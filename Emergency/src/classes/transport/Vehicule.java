package classes.transport;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Vehicule {

	public static int max_id=0;
	private int id_vehicule;
	
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

	
	public Vehicule() {
		super();
		this.personnel_embarque=new ArrayList<Integer>();
	}
	
	public Vehicule(double position_x, double position_y,int caserne) {
		super();
		this.id_vehicule = max_id;
		max_id++;
		this.position_x = position_x;
		this.position_y = position_y;
		this.carburant = 100;
		this.type_vehicule="temporaire";
		this.type_produit="undefined";
		this.produit=0.0;	
		this.caserne=caserne;
	}

	public int getId_vehicule() {
		return id_vehicule;
	}

	public void setId_vehicule(int id_vehicule) {
		this.id_vehicule = id_vehicule;
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
	
	
}
