package classes;

import java.util.ArrayList;
import java.util.List;

import com.Client_caserne;
import com.Client_feu;
import com.Client_personnel;
import com.Client_sonde;
import com.Client_vehicule;

import classes.depot.Caserne;
import classes.detecteur.Sonde;
import classes.detecteur.Type_detecteur;
import classes.humain.Personnel;
import classes.incendie.Feu;
import classes.transport.Vehicule;

public class Monde {
	
	private List<Caserne> listcaserne;
	private List<Sonde> listsonde;
	private List<Feu> listfeu;
	private List<Vehicule> listvehicule;
	private List<Personnel> listpersonnel;
	
	Client_caserne com_caserne=new Client_caserne();
	Client_sonde com_sonde=new Client_sonde();
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
		 /*
		// Recupere les vehicules 
		listvehicule=com_vehicule.getvehicules();
		if(listvehicule.isEmpty())
			Vehicule.max_id=0;
		else 
			Vehicule.max_id=this.listvehicule.get(listvehicule.size()-1).getId();
		// recupere les casernes	 
		listcaserne=com_caserne.getcaserne();
		if(listcaserne.isEmpty())
			Caserne.max_id=0;
		else 
			Caserne.max_id=this.listcaserne.get(listcaserne.size()-1).getId();
		//Recupere les sondes
		listsonde=com_sonde.getsonde();
		if(listsonde.isEmpty())
			Sonde.max_id=0;
		else
			Sonde.max_id=this.listsonde.get(listsonde.size()-1).getId();
	
		//Recupere le personnel
		listpersonnel=com_personnel.getpersonnel();
		if(listpersonnel.isEmpty())
			Personnel.max_id=0;
		else
			Personnel.max_id=this.listsonde.get(listpersonnel.size()-1).getId();*/
}


	public void setlistfeu(List<Feu> getincendies) {
		this.listfeu=getincendies;
		
	}
	
	public List<Feu> getListFeu() {
		return this.listfeu;
	}

	public void ajoutCaserne(double position_x,double position_y,int personnel_ini,int vehicule_ini) {
		Caserne c=new Caserne(position_x, position_y,personnel_ini,vehicule_ini);
		com_caserne.Ajout(c);
	}
	
	public void ajoutSonde(Type_detecteur type,int rate,double position_x,double position_y,double etat) {
		Sonde s=new Sonde(type,rate,position_x,position_y,etat);
		this.listsonde.add(s);
		com_sonde.Ajout(s);
	}
	
	public List<Caserne> getListCaserne(){
		return this.listcaserne;
	}
	
	public List<Sonde> getListSonde(){
		return this.listsonde;
	}

	/*public void turn() {
		setlistfeu(this.com_feu.getincendies());
		System.out.println(this.listcaserne);
		if(!listfeu.isEmpty()) {
			double distance=10;
			for(Feu f:listfeu) {
				if(f.getPrisenecharge()==0) {
					List<Integer> casernes_pointeur=new ArrayList<Integer>();
					if(!listcaserne.isEmpty()) {
						int boucle_pointeur=0;
						for(Caserne c:this.listcaserne) {
							int position_pointeur=0;
							for(int k=0;k<casernes_pointeur.size();k++) {
									if(get_distance(f.getPosx(),f.getPosy(),c.getPosition_x(),c.getPosition_y())<get_distance(
											f.getPosx(),f.getPosy(),listcaserne.get(casernes_pointeur.get(k)).getPosition_x(),listcaserne.get(casernes_pointeur.get(k)).getPosition_y()));	
										position_pointeur++;
							}
							casernes_pointeur.add(position_pointeur,boucle_pointeur);
							boucle_pointeur++;
						}
						System.out.println("Liste des casernes par ordre de distance "+casernes_pointeur+" qui vont s'occuper du feu "+f.getId());
						
					}
				}
				
			}
		}
	}*/
	
	public void turn() {
		List<Vehicule> listvehiculedisponible=com_vehicule.getvehiculesdisponibles();
		List<Vehicule> listvehiculenondisponible=com_vehicule.getvehiculesnondisponibles();
		List<Feu> listfeu=com_feu.getincendiesdetecte();
		for(Feu f:listfeu) {
			double distance=10;
			int vehicule_pointeur=0;
			int boucle_pointeur=0;
			if(!f.isprisenecharge()) {
				for(Vehicule v_d:listvehiculedisponible) {
					vehicule_pointeur=(get_distance(f.getPosx(),f.getPosy(),v_d.getPosition_x(),v_d.getPosition_y())<distance)?boucle_pointeur:vehicule_pointeur;
					boucle_pointeur++;
				}
			}
		System.out.println("C'est le camion "+listvehiculedisponible.get(vehicule_pointeur).getId()+" qui va s'occuper du feu "+f.getId());
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
