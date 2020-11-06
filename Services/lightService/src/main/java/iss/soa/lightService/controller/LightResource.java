package iss.soa.lightService.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class LightResource {
	
	@GetMapping("/light")
	public boolean lightState() {
		return true;
	}
	
}
