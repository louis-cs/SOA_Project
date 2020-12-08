package iss.soa.hmi.controller;

import org.springframework.web.bind.annotation.*;

import iss.soa.hmi.model.Hmi;

public class HmiResource {
	
	/**
	 * Return the Hmi
	 * @return Hmi
	 */
	@GetMapping
	public Hmi disp() {
		Hmi ihm = new Hmi();
		return ihm;
	}
}
