package iss.soa.lightService.model;

public class Light {
	
	private int id;
	private boolean state;
	
	public Light(int id) {
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
