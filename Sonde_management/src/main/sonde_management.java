package main;

import java.util.concurrent.TimeUnit;

import classes.Monde;
import classes.detecteur.Type_detecteur;

public class sonde_management {

	
	public static void main(String[] args) throws InterruptedException{


		Monde m=new Monde();
		while(true) {
			m.turn();
			TimeUnit.SECONDS.sleep(10);
		}	
	}
}
