package classes.transport;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public abstract class Vehicule {

	@SerializedName("id")
	private int id_vehicule;
	@SerializedName("position_x")
	private int position_x;
	@SerializedName("position_y")
	private int position_y;
	@SerializedName("type_vehicule")
	private String type_vehicule;
	@SerializedName("type_produit")
	private String type_produit;
	@SerializedName("produit")
	private double produit;
	@SerializedName("carburant")
	private int carburant;
	@SerializedName("personnel_embarque")
	private List<Integer> personnel_embarque;
	@SerializedName("caserne")
	private int caserne;
	
	public Vehicule() {
		super();
		this.personnel_embarque=new ArrayList<Integer>();
	}
	
	public Vehicule(int id_vehicule, int position_x, int position_y,int caserne) {
		super();
		this.id_vehicule = id_vehicule;
		this.position_x = position_x;
		this.position_y = position_y;
		this.carburant = 100;
		this.type_vehicule=this.getClass().getSimpleName();
		this.type_produit="undefined";
		this.produit=0.0;	
		this.caserne=caserne;
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
