package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.Client_feu;


public class Monde {
	
	Client_feu com_feu=new Client_feu();

	
	public List<Fire> listfeu;
	
	public Monde() {
		super();
		listfeu=new ArrayList<Fire>();
				
	}

	public void evoluate() {
		Random generator = new Random();
		int number = generator.nextInt(3);
		switch(number)
	     {
		case 0:
	    case 1:
	    	number=generator.nextInt(6);
	    	double[] max=new double [] {45.77,4.91};
	    	double[] min= new double[] {45.73,4.79};
	    	double x=(generator.nextInt((int)((max[0]-min[0])*10000+1))+min[0]*10000) / 10000.0;
	    	double y=(generator.nextInt((int)((max[1]-min[1])*10000+1))+min[1]*10000) / 10000.0;
			Fire f=new Fire(Fire_type.values()[number],12,x,y);
			Fire.max_id++;
			com_feu.Ajout(f);
			//listfeu.add(f);
			break;	    	
		default:
			break;
		  }
			
		for(Fire feu:this.listfeu) {
				String requestBody = "{ \"intensite\": \"" + feu.getIntensity() + "\"}";
				//feu.setIntensity(feu.getIntensity()+1);
				com_feu.Misajour(feu,requestBody);
		}
	}
	
	public void getincendies() {
		listfeu=com_feu.getincendies();
		if(listfeu.isEmpty())
			Fire.max_id=0;
		Fire.max_id=this.listfeu.get(listfeu.size()-1).getId();
	}

	public void deleteall() {
		com_feu.deleteall();
	}
}
