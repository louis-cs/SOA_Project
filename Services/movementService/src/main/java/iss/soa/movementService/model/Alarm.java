package iss.soa.movementService.model;

public class Alarm {
	private int id;
	private boolean state; // False = off | True = on
	
	public Alarm(int id) {
		this.id = id;
		this.state = false;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public void changeState() {
		if ( this.state == true ) {
			this.state = false;
		}
		else {
			this.state = true;
		}
	}
	
}
