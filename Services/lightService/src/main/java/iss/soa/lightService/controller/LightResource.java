package iss.soa.lightService.controller;

import iss.soa.lightService.model.RoomLight;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class LightResource {
	
	/**
	 * Return the state of a light in a room
	 * @return an boolean 
	 */
	@GetMapping("/light")
	public boolean lightState() {
		return true;
	}
	
	/**
	 * Return a RoomLight matching the id
	 * @param id
	 * @return a RoomLight
	 */
	@GetMapping(value = "/{id}")
	public RoomLight infosRoom(@PathVariable int id) {
		RoomLight room = new RoomLight(id);
		return room;
	}
	
	/**
	 * Change the state of a window in a room
	 * @param id of the room
	 * @param iWindow id of the window to modify
	 */
	@PutMapping(value = "/{id}/{iLight}")
	public RoomLight lightStateChange(@PathVariable int id, @PathVariable int iLight) {
		RoomLight room = new RoomLight(id);
		room.changeLightState(iLight);
		return room;
	}
	
}
