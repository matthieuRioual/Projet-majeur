package main;

import classes.Monde;
import classes.transport.Vehicule;


public class simulateur_vehicule_sonde {


	public static void main(String[] args) throws InterruptedException{

		
		Monde m=new Monde();
		System.out.println(Vehicule.max_id);
		//m.ajoutSonde(Type_detecteur.CO2, 10000, 15, 100, 12.0);
		System.out.println(m.getLisCaserne().get(63).getListvehicule());
	}

	
}
