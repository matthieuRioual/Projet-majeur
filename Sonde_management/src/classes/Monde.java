package classes;

import java.util.List;

import classes.com.Client_sonde;
import classes.detecteur.*;


public class Monde {
	
	Client_sonde com_sonde=new Client_sonde();


	public Monde() {
		super();
    	}
    		

	public void ajoutSonde(Type_detecteur type,int rate,double position_x,double position_y,double etat) {
		Sonde s=new Sonde(type,rate,position_x,position_y,etat);
		com_sonde.Ajout(s);
	}

	
	public void tout_supprimer() {
		List<Sonde> listsonde=com_sonde.getsonde();
		for(Sonde s:listsonde)
			com_sonde.Supression(s.getId());

	}

	public void turn() {
		List<Sonde> listsonde=com_sonde.getsonde();
		for(Sonde s:listsonde) {
			com_sonde.detection(s.getId());
		}
	}
	
	public void init_sonde() {
		double pas=0.01;
		double[] max=new double [] {45.77,4.91};
    	double[] min= new double[] {45.73,4.79};
    	for(int i=0;i<(max[0]-min[0])/pas;i++) {
    		for(int j=0;j<(max[1]-min[1])/pas;j++) {
    			double x=min[0]+i*pas ;
    			double y=min[1]+j*pas;
    			this.ajoutSonde(Type_detecteur.CO2, 5000, x, y, 5);
    		}
		
    	}
	}
	
	
}
