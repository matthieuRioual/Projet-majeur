package classes.depot;


import java.util.Random;

import com.Client_personnel;
import com.Client_vehicule;
import com.google.gson.annotations.SerializedName;

import classes.ideable;
import classes.Fire_type;
import classes.humain.Personnel;
import classes.transport.Vehicule;


public class Caserne implements ideable{

	public static int max_id;
	private int id;
	private int personnel_ini;
	private int vehicule_ini;
	
	@SerializedName("position_x")
	private double position_x;
	
	@SerializedName("position_y")
	private double position_y;
	
	private Client_vehicule com_vehicule=new Client_vehicule();
	private Client_personnel com_personnel=new Client_personnel();

	
	public Caserne(double position_x,double position_y,int personnel_ini,int vehicule_ini) {
		super();
		max_id++;
		this.id=Caserne.max_id;
		this.position_x=position_x;
		this.position_y=position_y;
		this.personnel_ini=personnel_ini;
		this.vehicule_ini=vehicule_ini;
		caserne_init();
	}

	private void caserne_init() {
		//for(int k = 0;k<this.personnel_ini;k++) {
			//Random generator = new Random();
	    	//int number=generator.nextInt(6);
			Vehicule vehicule=new Vehicule(Fire_type.A,this.getPosition_x(),this.getPosition_y(),this.getId());
			Vehicule vehicule2=new Vehicule(Fire_type.B,this.getPosition_x(),this.getPosition_y(),this.getId());
			Vehicule vehicule3=new Vehicule(Fire_type.C,this.getPosition_x(),this.getPosition_y(),this.getId());
			this.com_vehicule.Ajout(vehicule);
			this.com_vehicule.Ajout(vehicule2);
			this.com_vehicule.Ajout(vehicule3);

			
		//}

		for(int k = 0;k<this.vehicule_ini;k++) {
			Random generator = new Random();
	    	int number=generator.nextInt(6);
			Personnel personnel=new Personnel(Fire_type.values()[number],0,this.getId());
			this.com_personnel.Ajout(personnel);
		}	
	}

	@Override
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id=id;
	}
	
	public double getPosition_x() {
		return position_x;
	}

	public void setPosition_x(double position_x) {
		this.position_x = position_x;
	}

	public double getPosition_y() {
		return position_y;
	}

	public void setPosition_y(double position_y) {
		this.position_y = position_y;
	}

	
}
