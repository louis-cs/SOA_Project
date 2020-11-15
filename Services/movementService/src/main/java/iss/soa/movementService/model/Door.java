package iss.soa.movementService.model;

public class Door {
	
	// State sensor
	public class Sensor {
		private boolean position; // False = closed | True = opened
		
		public Sensor() {
			this.position = false;
		}
		
		public boolean getPosition() {
			return this.position;
		}
	}
	
	public class Actuator {
		private boolean position; // False = closed | True = opened
		
		public Actuator() {
			this.position = false;
		}
		
		public void changePosition() {
			if ( this.position == true ) {
				this.position = false;
			}
			else {
				this.position = true;
			}
		}
	}
	
	private int id;
	private Sensor sensor;
	private Actuator actuator;
	
	public Door(int id) {
		this.id = id;
		this.sensor = new Sensor();
		this.actuator = new Actuator();
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean getPosition() {
		return this.sensor.getPosition();
	}
	
	public void changePosition() {
		this.actuator.changePosition();
	}
}
