package classes;

import com.google.gson.annotations.SerializedName;

public class Fire implements ideable {
	
	static int max_id;
	private int id;
	@SerializedName("position_x")
	private double position_x;
	@SerializedName("position_y")
	private double position_y;
	@SerializedName("categorie")
	private Fire_type categorie;
	@SerializedName("intensite")
	private int intensite;
	
	public Fire(Fire_type T,int I,double x,double y) {
		this.id=max_id+1;
		this.categorie=T;
		this.intensite=I;
		this.position_x=x;
		this.position_y=y;
	}
	
	
	@Override
	public String toString() {
		String phrase = "je suis un feu de classe " + this.categorie + " et je suis un feu d'intensité " +this.intensite+ " sur 5 ayant pour position[x:"+this.position_x+", y:"+this.position_y+"]";
		return(phrase);
	}
	
	public int getIntensity() {
		return this.intensite;
	}
	
	public Fire_type getType() {
		return this.categorie;
	}

	public double getPosx() {
		return this.position_x;
	}
	
	public double getPosy() {
		return this.position_y;
	}

	public int getId() {
		return this.id;
	}

	public void setPosx(double new_x) {
		this.position_x=new_x;
	}
	
	public void setPosy(double new_y) {
		this.position_y=new_y;
	}
	
	public void setIntensity(int i) {
		if(i<61) {
			this.intensite=i;
			}
	}

	
	
}
