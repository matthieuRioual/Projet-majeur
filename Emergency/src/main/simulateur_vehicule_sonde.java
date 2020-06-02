package main;

import classes.Monde;
import classes.detecteur.Type_detecteur;
import classes.transport.Vehicule;


public class simulateur_vehicule_sonde {


	public static void main(String[] args) throws InterruptedException{

		
		Monde m=new Monde();
		System.out.println(Vehicule.max_id);
		m.ajoutCaserne(45.754, 4.857, 2, 10);
		//m.ajoutSonde(5000, 45.746, 4.807, 4.0);
		
	}

	
}
