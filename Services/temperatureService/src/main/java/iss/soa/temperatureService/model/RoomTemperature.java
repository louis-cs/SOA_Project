package iss.soa.temperatureService.model;

import java.util.ArrayList;

public class RoomTemperature {
	
	private int id;
	private int temperature;
	private ArrayList<Window> wList;
	private ArrayList<Radiator> rList;
	
	public RoomTemperature(int id) {
		this.id = id;
		
		this.temperature = 25;
		
		// Let's add 3 windows
		wList.add(new Window(0));
		wList.add(new Window(1));
		wList.add(new Window(2));
		
		// Let's add 3 radiators
		rList.add(new Radiator(0));
		rList.add(new Radiator(1));
		rList.add(new Radiator(2));
	}
	
	/*-------------------Getters-------------------*/
	public int getId() {
		return this.id;
	}
	
	public int getTemperature() {
		return this.temperature;
	}
	
	public int getWindowsNumber() {
		return this.wList.size();
	}
	
	public int getRadiatorsNumber() {
		return this.rList.size();
	}
	
	/*-------------------Setters-------------------*/
	public void setWindowState(int id) {
		for (int i = 0 ; i < wList.size() ; i++) {
			if ( id == wList.get(i).getId() ) {
				wList.get(i).changeState();
				break;
			}
		}
	}
	
	public void setRadiatorState(int id) {
		for (int i = 0 ; i < rList.size() ; i++) {
			if ( id == rList.get(i).getId() ) {
				rList.get(i).changeState();
				break;
			}
		}
	}
	
}
