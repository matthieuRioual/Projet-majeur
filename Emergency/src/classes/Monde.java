package classes;

import java.util.List;

import com.Client_caserne;
import com.Client_feu;
import com.Client_personnel;
import com.Client_vehicule;

import classes.depot.Caserne;
import classes.humain.Personnel;
import classes.incendie.Feu;
import classes.transport.Vehicule;

public class Monde {
	
	private List<Caserne> listcaserne;
	
	Client_caserne com_caserne=new Client_caserne();
	Client_feu com_feu = new Client_feu();
	Client_personnel com_personnel = new Client_personnel();
	Client_vehicule com_vehicule = new Client_vehicule();

	public Monde() {
		super();
		
		listcaserne=com_caserne.getcaserne();
		if(listcaserne.isEmpty())
			Caserne.max_id=0;
		else 
			Caserne.max_id=this.listcaserne.get(listcaserne.size()-1).getId(); 
	}

	public void ajoutCaserne(double position_x,double position_y,int personnel_ini,int vehicule_ini) {
		Caserne c=new Caserne(position_x, position_y,personnel_ini,vehicule_ini);
		com_caserne.Ajout(c);
	}
	
	public void turn() {
		List<Vehicule> listvehiculedisponible=com_vehicule.getvehiculesdisponibles();
		//List<Vehicule> listvehiculenondisponible=com_vehicule.getvehiculesnondisponibles();
		List<Feu> listfeu=com_feu.getincendiesdetecte();
		double pas=1*Math.pow(10, -3);
		for(Feu f:listfeu) {
			if(f.getprisenecharge()==0) {
				double distance=10;
				int vehicule_pointeur=0;
				int boucle_pointeur=1;
				for(Vehicule v_d:listvehiculedisponible) {
					if(v_d.getType_produit().equals(f.getType())) {
						vehicule_pointeur=(get_distance(f.getPosx(),f.getPosy(),v_d.getPosition_x(),v_d.getPosition_y())<distance)? boucle_pointeur:vehicule_pointeur;
						distance=(get_distance(f.getPosx(),f.getPosy(),v_d.getPosition_x(),v_d.getPosition_y())<distance)? get_distance(f.getPosx(),f.getPosy(),v_d.getPosition_x(),v_d.getPosition_y()):distance;
					}
					boucle_pointeur++;
				}
				if(vehicule_pointeur!=0) {
					com_feu.pris_en_charge(f.getId(),listvehiculedisponible.get(vehicule_pointeur-1).getId());
					com_vehicule.setdisponibilite(f.getId(), listvehiculedisponible.get(vehicule_pointeur-1).getId());
					System.out.println("C'est le camion "+listvehiculedisponible.get(vehicule_pointeur-1).getId()+" qui va s'occuper du feu "+f.getId());
					listvehiculedisponible.remove(vehicule_pointeur-1);
				}
				else
					System.out.println("Aucun camion n'est disponible pour intervenir sur le feu numéro "+f.getId());
			}
			else {
				Vehicule vehicule_intervention=com_vehicule.getvehiculebyID(f.getprisenecharge()).get(0);
				if(vehicule_intervention.getDisponibilite()!=f.getprisenecharge()) {
					com_vehicule.setdisponibilite(f.getId(), vehicule_intervention.getId());
					vehicule_intervention.setDisponibilite(f.getId());
				}

				if(Math.abs(vehicule_intervention.getPosition_x()-f.getPosx())<pas && Math.abs(vehicule_intervention.getPosition_y()-f.getPosy())<pas) {
					if(f.getIntensity()-1>0) {
						String requestBody = "{ \"intensite\": \"" + (f.getIntensity()-1) + "\"}";
						com_feu.Misajour(f,requestBody);
						System.out.println("le feu "+f.getId()+"diminue d\'intensité");
					}
					else
						com_vehicule.setdisponibilite(0, vehicule_intervention.getId());
						vehicule_intervention.setDisponibilite(0);
						listvehiculedisponible.add(vehicule_intervention);
						com_feu.Supression(f.getId());
						
				}
				else
					com_vehicule.move(pas,vehicule_intervention,f);
			}
			
		}
		for(Vehicule v_nd:listvehiculedisponible) {
				Caserne caserne_retour=com_caserne.getcasernebyID(v_nd.getCaserne()).get(0);
				if(caserne_retour.getPosx()!=v_nd.getPosition_x() || caserne_retour.getPosx()!=v_nd.getPosition_x()) {
					com_vehicule.move(pas, v_nd, caserne_retour);
				}
			}
	}

	public double get_distance(double x1,double y1,double x2,double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}
	
	
	public void tout_supprimer() {
		List<Vehicule> listvehicule=com_vehicule.getvehicules();
		for(Vehicule v:listvehicule)
			com_vehicule.Supression(v.getId());
		List<Caserne> listcaserne=com_caserne.getcaserne();
		for(Caserne c:listcaserne)
			com_caserne.Supression(c.getId());
		List<Personnel> listpersonnel=com_personnel.getpersonnel();
		for(Personnel p:listpersonnel)
			com_personnel.Supression(p.getId());
	}
}
