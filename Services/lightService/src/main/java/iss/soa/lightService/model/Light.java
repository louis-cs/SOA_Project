package iss.soa.lightService.model;

public class Light {
	
	public class LSensor {
		private int lux;
		
		public int getLum() {
			return this.lux;
		}
		
		public void set(int lum){
			this.lux = lum;
		}
	}
	
	public class Led {
		private boolean state;
		
		public boolean getState() {
			return this.state;
		}
		
		public void set(boolean state){
			this.state = state;
		}
	}
	
}
