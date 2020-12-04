package iss.soa.temperatureService.model;

public class Window {
	private int id;
	private boolean state;
	
	public Window(int id) {
		this.id = id;
		this.state = false; // Window is closed by default
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
