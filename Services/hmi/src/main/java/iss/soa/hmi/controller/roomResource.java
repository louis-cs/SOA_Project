package iss.soa.hmi.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import iss.soa.hmi.model.Device;
import iss.soa.hmi.model.Hmi;

@RestController

/*
 * ### ROOM Service should be located in another Spring-Maven project ###
 */

public class roomResource {

	Hmi hmi = new Hmi();

	/**
	 * Create room with sensors and actuators
	 * 
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/room/create/{room}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String roomCreate(@PathVariable String room) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");

		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"room" + room + "CT\"></m2m:cnt>";
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE";
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String) response.getBody();

		url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room" + room + "CT";
		body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"room" + room + "SensorsCT\"></m2m:cnt>";
		entity = new HttpEntity<String>(body, headers);
		response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		resp += (String) response.getBody();
		body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"room" + room
				+ "ActuatorsCT\"></m2m:cnt>";
		entity = new HttpEntity<String>(body, headers);
		response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		resp += (String) response.getBody();

		return resp;
	}

	/**
	 * Delete room
	 * 
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/room/delete/{room}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String roomDelete(@PathVariable String room) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room" + room + "CT";

		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String) response.getBody();
		return resp;
	}

	/**
	 * Reset all rooms
	 * 
	 * @return
	 */
	@GetMapping(value = "/room/reset", produces = MediaType.APPLICATION_JSON_VALUE)
	public String roomReset() {
		String resp = "";
		for (int i = 1; i <= 3; i++) {
			String room = "";
			room += i;
			roomDelete(room);
			resp += roomCreate(room);
		}

		String uri = "";
		RestTemplate rt = new RestTemplate();
		for (String deviceKey : hmi.devices.keySet()) {
			Device device = hmi.devices.get(deviceKey);
			for (int room = 1; room <= 3; room++) {
				if (device.hasId) {
					for (int id = 1; id <= room; id++) {
						uri = device.url + "/create" + hmi.get(String.valueOf(room), String.valueOf(id));
						rt.getForEntity(uri, null);
					}
				} else {
					uri = device.url + "/create" + hmi.get(Integer.toString(room), "");
					rt.getForEntity(uri, null);
				}
			}
		}
		return resp;
	}
}
