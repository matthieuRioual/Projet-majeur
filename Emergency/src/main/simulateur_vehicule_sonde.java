package main;

import java.util.concurrent.TimeUnit;

import classes.Monde;



public class simulateur_vehicule_sonde {


	public static void main(String[] args) throws InterruptedException{

		
		Monde m=new Monde();
		m.tout_supprimer();
		Monde m2=new Monde();
		m2.ajoutCaserne(45.75, 4.85, 3, 2);
		while(true) {
			m2.turn();
			TimeUnit.SECONDS.sleep(5);

		}	
	}

	
}
