package classes.transport;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import classes.ideable;

public class Vehicule implements ideable{

	public static int max_id=0;
	private int id;
	
	@SerializedName("position_x")
	private double position_x;
	
	@SerializedName("position_y")
	private double position_y;
	
	@SerializedName("type_vehicule")
	private String type_vehicule;
	
	@SerializedName("type_produit")
	private String type_produit;
	
	@SerializedName("produit")
	private double produit;
	
	@SerializedName("carburant")
	private int carburant;
	
	@SerializedName("caserne")
	private int caserne;
	
	private List<Integer> personnel_embarque;
	
	public Vehicule(double position_x, double position_y,int id_caserne) {
		super();
		this.id = this.max_id;
		this.max_id++;
		this.position_x = position_x;
		this.position_y = position_y;
		this.type_vehicule=new String("temporaire");
		this.type_produit=new String ("temporaire2");
		this.produit=0;	
		this.carburant = 100;
		this.caserne=id_caserne;
		this.personnel_embarque=new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCarburant() {
		return this.carburant;
	}
	
	public void setCarburant(int carb) {
		this.carburant=carb;
	}
	
	public List<Integer> personnel_embarque() {
		return this.personnel_embarque;
	}
	
	public void setPersonel_embarque(int[] id_pers) {
		for(int i:id_pers) {
			if(!personnel_embarque().contains(i))
				personnel_embarque.add(i);				
		}
	}
	
	public double getPosition_x() {
		return position_x;
	}

	public void setPosition_x(double position_x) {
		this.position_x = position_x;
	}

	public double getPosition_y() {
		return position_y;
	}

	public void setPosition_y(double position_y) {
		this.position_y = position_y;
	}

	
	
}
