package iss.soa.movementService.model;

public class RoomMovement {
	
	public class MovementSensor {
		private int id;
		private boolean state;
		
		public int getId() {
			return this.id;
		}
		
		public boolean getState() {
			return this.state;
		}
	}
	
	public class Door {
		private int id;
		private boolean state;
		
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
	
	public class Alarm {
		private int id;
		private boolean state;
		
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
}
