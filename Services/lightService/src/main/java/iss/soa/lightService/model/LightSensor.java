package iss.soa.lightService.model;

public class LightSensor {
	
	private int id;
	private int lightValue;
	
	public LightSensor(int id) {
		this.id = id;
		this.lightValue = 300;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getLightValue() {
		return this.lightValue;
	}
	
}
