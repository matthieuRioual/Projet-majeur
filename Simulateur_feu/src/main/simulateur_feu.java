package main;


import java.util.concurrent.TimeUnit;
import classes.*;

public class simulateur_feu {

	public static void main(String[] args) throws InterruptedException{
		

		Monde monde=new Monde();
		monde.deleteall();
	while(true) {
			TimeUnit.SECONDS.sleep(1);
			monde.evoluate();
		}
		

	}
	
}
