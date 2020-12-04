package iss.soa.lightService.controller;

import iss.soa.lightService.model.RoomLight;

import java.util.Arrays;
import org.json.*;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@RestController
public class LightResource {
	
	private RoomLight room = new RoomLight(0);
	
	/**
	 * Trying to get value from OM2M
	 */
	@GetMapping
	public String lightValue() {
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		// Set http request headers
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room1CT/room1SensorsCT/light/DATA/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String ret = null;
		String s = response.getBody();
		String[] tokens = s.split(" ");
		for (String t : tokens) {
			ret+=t+"\r\n";
		}
		return ret;
	}
	
	/**
	 * Return a RoomLight matching the id
	 * @param id
	 * @return a RoomLight
	 */
	@GetMapping(value = "/{id}")
	public RoomLight infosRoom(@PathVariable int id) {
		//RoomLight room = new RoomLight(id);
		return this.room;
	}
	
	/**
	 * Change the state of a window in a room
	 * @param id of the room
	 * @param iWindow id of the window to modify
	 */
	@PutMapping(value = "/{id}/{iLight}")
	public RoomLight lightStateChange(@PathVariable int id, @PathVariable int iLight) {
		//RoomLight room = new RoomLight(id);
		this.room.changeLightState(iLight);
		return room;
	}
	
}
