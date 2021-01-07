package iss.soa.hmi.model;

public class Hmi {

	private boolean loop;
	
	public Hmi() {
		this.loop = false;
	}
	
	public boolean f() {
		return this.loop;
	}
	
	public void loop() {
		this.loop = !this.loop;
	}
	
	////////////////////////////////////////
	////////////////////////////////////////
	
	/*
	 * root URLs of resources  
	 */	
	public String light = "http://localhost:8082/light";
	public String led = "http://localhost:8082/led";
	public String mvt = "http://localhost:8080/mvt";
	public String temp = "http://localhost:8081/temp";
	
	/*
	 * URIs for each resource
	 */
	public String get(String room, String id) {
		return "?room=" + room + "&id=" + id;
	}	
	public String set(String room, String id, String val) {
		return "/set?room=" + room + "&id" + id + "&val=" + val;
	}
}
