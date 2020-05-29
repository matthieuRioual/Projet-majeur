package classes.depot;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import classes.ideable;
import classes.humain.Personnel;
import classes.transport.Vehicule;


public class Caserne implements ideable{

	public static int max_id;
	private int id;
	
	@SerializedName("position_x")
	private double position_x;
	
	@SerializedName("position_y")
	private double position_y;
	
	private List<Personnel> listpersonnel;
	private List<Vehicule> listvehicule;
	
	public Caserne(int position_x,int position_y) {
		super();
		this.id=max_id+1;
		max_id++;
		this.position_x=position_x;
		this.position_y=position_y;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id=id;
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

	public List<Personnel> getListpersonnel() {
		return listpersonnel;
	}

	public void setListpersonnel(List<Personnel> listpersonnel) {
		this.listpersonnel = listpersonnel;
	}

	public List<Vehicule> getListvehicule() {
		return listvehicule;
	}

	public void setListvehicule(List<Vehicule> listvehicule) {
		this.listvehicule = listvehicule;
	}
	
}
