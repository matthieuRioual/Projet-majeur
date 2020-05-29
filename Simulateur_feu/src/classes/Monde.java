package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.Client_feu;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Monde {
	
	Client_feu com=new Client_feu();
	
	HttpRequestFactory requestFactory
	  = new NetHttpTransport().createRequestFactory();
	
	HttpRequest request;
	
	public List<Fire> listfeu;
	
	public Monde() {
		super();
		listfeu=new ArrayList<Fire>();
				
	}

	public void evoluate() {
		int number_fire=listfeu.size();
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
			com.Ajout(f);
			listfeu.add(f);
			break;
		case 2:
			if(number_fire!=0) {
		    	number=generator.nextInt(number_fire);
		    	com.Supression(listfeu.get(number).getId());
		    	listfeu.remove(number);
		    	break;
			}
	    	
		default:
			break;
		  }
			
		for(Fire feu:this.listfeu) {
				String requestBody = "{ \"intensite\": \"" + ((int) feu.getIntensity()/12) + "\"}";
				feu.setIntensity(feu.getIntensity()+1);
				com.Misajour(feu,requestBody);
		}
	}
	
	public void getincendies() {
		listfeu=com.getincendies();
		if(listfeu.isEmpty())
			Fire.max_id=0;
		Fire.max_id=this.listfeu.get(listfeu.size()-1).getId();
	}

	public void deleteall() {
		com.deleteall();
	}
}
