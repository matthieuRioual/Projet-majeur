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
					com_vehicule.pris_en_charge(f.getId(), listvehiculedisponible.get(vehicule_pointeur-1).getId());
					System.out.println("C'est le camion "+listvehiculedisponible.get(vehicule_pointeur-1).getId()+" qui va s'occuper du feu "+f.getId());
					listvehiculedisponible.remove(vehicule_pointeur-1);
				}
				else
					System.out.println("Aucun camion n'est disponible pour intervenir sur le feu numéro "+f.getId());
			}
			else {
				List<Vehicule> vehicule_intervention=com_vehicule.getvehiculebyID(f.getprisenecharge());
				com_vehicule.move(vehicule_intervention.get(0),f);
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
