package iss.soa.movementService.model;

public class MovementSensor {
	private int id;
	private boolean state;
	
	public MovementSensor(int id) {
		this.id = id;
		this.state = false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean getState() {
		return this.state;
	}
}
