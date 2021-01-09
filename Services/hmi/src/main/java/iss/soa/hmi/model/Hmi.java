package iss.soa.hmi.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Hmi {

	private static boolean loop;
	private static String ui;
	public static LocalTime time;

	/*
	 * UI additional log information
	 */
	public ResponseEntity<String> uiResp;
	public String log;

	/**
	 * MAP: OM2M devices
	 */
	public Map<String, Device> devices = new HashMap<String, Device>();

	////////////////////////////////////////
	////////////////////////////////////////

	public Hmi() {
		loop = false;
		ui = "Basic User Interface...";
		time = LocalTime.now();

		devices.put("light", new Device("http://localhost:8082/light", false, 300, false, false));
		devices.put("temperatureInside", new Device("http://localhost:8081/temperature/inside", false, 21, false, false));
		devices.put("temperatureOutside", new Device("http://localhost:8081/temperature/outside", false, 19, false, false));
		devices.put("movement", new Device("http://localhost:8083/movement", true, 0, false, false));
		devices.put("led", new Device("http://localhost:8082/led", true, 0, true, true));
		devices.put("door", new Device("http://localhost:8083/door", true, 0, false, true));
		devices.put("alarm", new Device("http://localhost:8083/alarm", true, 0, false, false));
		devices.put("radiator", new Device("http://localhost:8081/radiator", true, 0, false, true));
		devices.put("window", new Device("http://localhost:8081/window", true, 0, false, true));
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

	/*
	 * URIs for each resource
	 */
	public String get(String room, String id) {
		return String.format("?room=%s&id=%s", room, id);
	}

	public String set(String room, String id, String val) {
		return String.format("/set?room=%s&id=%s&val=%s", room, id, val);
	}

	////////////////////////////////////////
	////////////////////////////////////////

	/**
	 * All OM2M values
	 * 
	 * @param rt
	 * @param room
	 * @param id
	 * @return
	 */
	public String getAllOM2M(RestTemplate rt, String room, String id) {
		String uri, gui;
		gui = "	OM2M - Room:" + room + " Device:" + id + " Time:" + time + "\nDevices -> ";
		for (String deviceKey : devices.keySet()) {
			Device device = devices.get(deviceKey);
			uri = device.url;
			if (device.hasId) {
				uri += this.get(room, id);
			} else {
				uri += this.get(room, "");
			}
			if (device.isBool) {
				device.state = (boolean) rt.getForEntity(uri, boolean.class).getBody();
				gui += deviceKey + ": " + device.state + " | ";
			} else {
				device.value = (int) rt.getForEntity(uri, int.class).getBody();
				gui += deviceKey + ": " + device.value + " | ";
			}
		}
		return gui;
	}
}
