package iss.soa.temperatureService.model;

public class Radiator {
	private int id;
	private boolean state;
	
	public Radiator(int id) {
		this.id = id;
		this.state = false; // Radiator is turned off by default
	}
	
	public void changeState() {
		if ( this.state == true ) {
			this.state = false;
		}
		else {
			this.state = true;
		}
	}

	public int getId() {
		return this.id;
	}
	
	public boolean getState() {
		return this.state;
	}
}
