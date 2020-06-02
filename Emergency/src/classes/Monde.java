package classes;

import java.util.List;

import com.Client_caserne;
import com.Client_sonde;

import classes.depot.Caserne;
import classes.detecteur.Sonde;
import classes.detecteur.Type_detecteur;
import classes.humain.Personnel;
import classes.transport.Vehicule;

public class Monde {
	
	private List<Caserne> listcaserne;
	private List<Sonde> listsonde;
	
	Client_caserne com_caserne=new Client_caserne();
	Client_sonde com_sonde=new Client_sonde();

	public Monde() {
		super();
		listcaserne=com_caserne.getcaserne();
		if(listcaserne.isEmpty())
			Caserne.max_id=0;
		else 
			Caserne.max_id=this.listcaserne.get(listcaserne.size()-1).getId();
		for(Caserne c:listcaserne) {
			c.setListvehicule(com_caserne.getvehiculesofcaserne(c.getId()));
			c.setListpersonnel(com_caserne.getpersonnelsofcaserne(c.getId()));
			Vehicule.max_id+=c.getListvehicule().size();
			Personnel.max_id+=c.getListpersonnel().size();
			
		}
		listsonde=com_sonde.getsonde();
		if(listsonde.isEmpty())
			Sonde.max_id=0;
		else
			Sonde.max_id=this.listsonde.get(listsonde.size()-1).getId();
	}
	
	public void ajoutCaserne(double position_x,double position_y,int personnel_ini,int vehicule_ini) {
		Caserne c=new Caserne(position_x, position_y,personnel_ini,vehicule_ini);
		this.listcaserne.add(c);
		com_caserne.Ajout(c);
	}
	
	public void ajoutSonde(/*Type_detecteur type,*/int rate,double position_x,double position_y,double etat) {
		Sonde s=new Sonde(/*type,*/rate,position_x,position_y,etat);
		this.listsonde.add(s);
		com_sonde.Ajout(s);
	}
	
	public List<Caserne> getLisCaserne(){
		return this.listcaserne;
	}
	
	public List<Sonde> getLisSonde(){
		return this.listsonde;
	}
	
	
}
