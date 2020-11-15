package iss.soa.movementService.controller;

import org.springframework.web.bind.annotation.*;

import iss.soa.movementService.model.RoomMovement;


@RestController
public class MovementResource {

	/**
	 * Return a RoomMovement matching the id
	 * @param id
	 * @return a RoomMovement
	 */
	@GetMapping(value = "/{id}")
	public RoomMovement infosRoom(@PathVariable int id) {
		RoomMovement room = new RoomMovement(id);
		return room;
	}
	
	/**
	 * Change the state of a door in a room
	 * @param id of the room
	 * @param iDoor id of the door to modify
	 */
	@PutMapping(value = "/{id}/{iDoor}")
	public RoomMovement windowStateChange(@PathVariable int id, @PathVariable int iDoor) {
		RoomMovement room = new RoomMovement(id);
		room.changeDoorPosition(iDoor);
		return room;
	}
	
}
