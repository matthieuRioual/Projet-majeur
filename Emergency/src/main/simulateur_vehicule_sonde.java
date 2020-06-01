package main;

import classes.Monde;
import classes.depot.Caserne;
import classes.humain.Personnel;
import classes.transport.Vehicule;

public class simulateur_vehicule_sonde {

	
	public static void main(String[] args) throws InterruptedException{
		
		Monde m=new Monde();
		System.out.println(Vehicule.max_id);
		System.out.println(Personnel.max_id);

		//System.out.println(m.getLisCaserne().get(28).getListpersonnel());
		//System.out.println(m.getLisSonde());

	}
	
}
