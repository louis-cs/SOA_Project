package iss.soa.hmi.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.LocalTime;

public class Hmi {

	private static boolean loop;
	private static String ui;
	public static LocalTime time;
	
	public Hmi() {
		loop = false;
		ui = "Basic User Interface...";
		time = LocalTime.now();
	}
	
	public boolean getLoop() {
		return loop;
	}	
	public void loop() {
		loop = !loop;
	}
	
	public String getUi() {
		return ui;
	}
	public void appendUi(String s) {
		ui += "\n" + s;
	}
	public void clearUi() {
		ui = "";
	}
	////////////////////////////////////////
	////////////////////////////////////////
	
	/*
	 * UI additional log information
	 */
	public ResponseEntity<String> uiResp;
	public String log;
	
	/*
	 * OM2M values
	 */	
	public int lum;
	public boolean presence;
	public int inside;
	public int outside;
	public boolean led;
	public boolean door;
	public boolean alarm;
	public boolean radiator;
	public boolean window;
	
	////////////////////////////////////////
	////////////////////////////////////////
	
	/**
	 * root URLs of resources  
	 */	
	public String lights = "http://localhost:8082/light";
	public String movements = "http://localhost:8083/movement";
	public String temperatureInside = "http://localhost:8081/temperature/inside";
	public String temperatureOutside = "http://localhost:8081/temperature/outside";
	public String leds = "http://localhost:8082/led";
	public String doors = "http://localhost:8083/door";
	public String alarms = "http://localhost:8083/alarm";
	public String radiators = "http://localhost:8081/radiator";
	public String windows = "http://localhost:8081/window";
	
	/*
	 * URIs for each resource
	 */
	public String get(String room, String id) {
		return "?room=" + room + "&id=" + id;
	}	
	public String set(String room, String id, String val) {
		return "/set?room=" + room + "&id" + id + "&val=" + val;
	}
	
	/**
	 * All OM2M values
	 * @param rt
	 * @param room
	 * @param id
	 * @return
	 */
	public String getAllOM2M(RestTemplate rt, String room, String id) {
		String uri, gui;
		gui = "	OM2M - Room: " + room + "Device: " + id + "Time:" + time
				+ "\n * * * * * * * * * * * *"
				+ "\nSensors ->";
		uri = this.lights + this.get(room, "");		
		ResponseEntity<Integer> intResponse = rt.getForEntity(uri, int.class);
		this.lum = (int)intResponse.getBody();
		gui += "Luminosity: " + this.lum;
		uri = this.movements + this.get(room, "");
		ResponseEntity<Boolean> boolResponse = rt.getForEntity(uri, boolean.class);
		this.presence = (boolean)boolResponse.getBody();
		gui += " | Presence: " + this.presence;
		uri = this.temperatureInside + this.get(room, "");
		intResponse = rt.getForEntity(uri, int.class);
		this.inside = (int)intResponse.getBody();	
		gui += " | Temperature Inside: " + this.inside;
		uri = this.temperatureOutside + this.get(room, "");
		intResponse = rt.getForEntity(uri, int.class);
		this.outside = (int)intResponse.getBody();	
		gui += " | Temperature Outside: " + this.outside;
		uri = this.leds + this.get(room, id);
		boolResponse = rt.getForEntity(uri, boolean.class);
		this.led = (boolean)boolResponse.getBody();
		gui += "\nActuators - > Led: " + this.led;	
		uri = this.alarms + this.get(room, "");
		boolResponse = rt.getForEntity(uri, boolean.class);
		this.alarm = (boolean)boolResponse.getBody();
		gui += " | Alarm: " + this.alarm;	
		uri = this.doors + this.get(room, id);
		boolResponse = rt.getForEntity(uri, boolean.class);
		this.door = (boolean)boolResponse.getBody();
		gui += " | Door: " + this.door;		
		uri = this.windows + this.get(room, id);
		boolResponse = rt.getForEntity(uri, boolean.class);
		this.window = (boolean)boolResponse.getBody();
		gui += " | Window: " + this.window;
		uri = this.radiators + this.get(room, id);
		boolResponse = rt.getForEntity(uri, boolean.class);
		this.radiator = (boolean)boolResponse.getBody();
		gui += " | Radiator: " + this.radiator
				+ "\n * * * * * * * * * * * *";
		return gui;
	}
}
