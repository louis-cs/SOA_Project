package iss.soa.lightService.model;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomLight {
	
	private int id;
	private LightSensor sensor;
	private ArrayList<Light> lList = new ArrayList<Light>();
	
	
	// Empty constructor
	public RoomLight() {
		
	}
	
	public RoomLight(int id) {
		this.id = id;
		
		this.sensor = new LightSensor(0);
		
		// Let's add 3 lights
		lList.add(new Light(0));
		lList.add(new Light(1));
		lList.add(new Light(2));
	}
	
	/*-------------------Getters-------------------*/
	public int getId() {
		return this.id;
	}
	
	public int getLightValue() {
		return this.sensor.getLightValue();
	}
	
	// Return the list of state matching each window
	public HashMap<Integer, Boolean> getLightStateList() {
		HashMap<Integer, Boolean> list = new HashMap<Integer, Boolean>();
		for (int i = 0 ; i < lList.size() ; i++) {
			list.put(i, lList.get(i).getState());
		}
		return list;
	}
	
	/*-------------------Methods-------------------*/
	public void changeLightState(int id) {
		for (int i = 0 ; i < lList.size() ; i++) {
			if ( id == lList.get(i).getId() ) {
				lList.get(i).changeState();
				break;
			}
		}
	}
}
