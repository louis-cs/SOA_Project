package iss.soa.movementService.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MovementResource {
	@GetMapping("/movement")
	public boolean isMovement() {
		return true;
	}
}
