package iss.soa.movementService.model;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomMovement {
	
	private int id;
	private MovementSensor sensor;
	private Alarm alarm;
	private ArrayList<Door> dList = new ArrayList<Door>();
	
	// Empty constructor
	public RoomMovement() {
		
	}
	
	public RoomMovement(int id) {
		this.id = id;
		this.sensor = new MovementSensor(0);
		this.alarm = new Alarm(0);
		
		// Let's add 3 doors
		this.dList.add(new Door(0));
		this.dList.add(new Door(1));
		this.dList.add(new Door(2));
	}
	
	/*-------------------Getters-------------------*/
	public int getId() {
		return this.id;
	}
	
	public boolean getMovementSensorState() {
		return this.sensor.getState();
	}
	
	public boolean getAlarmState() {
		return this.alarm.getState();
	}
	
	public int getDoorsNumber() {
		return this.dList.size();
	}
	
	// Return the list of state matching each door
	public HashMap<Integer, Boolean> getDoorStateList() {
		HashMap<Integer, Boolean> list = new HashMap<Integer, Boolean>();
		for (int i = 0 ; i < this.dList.size() ; i++) {
			list.put(i, this.dList.get(i).getPosition());
		}
		return list;
	}
	
	/*-------------------Methods-------------------*/
	public void changeDoorPosition(int id) {
		for (int i = 0 ; i < this.dList.size() ; i++) {
			if ( id == this.dList.get(i).getId() ) {
				this.dList.get(i).changePosition();
				break;
			}
		}
	}
	
}
