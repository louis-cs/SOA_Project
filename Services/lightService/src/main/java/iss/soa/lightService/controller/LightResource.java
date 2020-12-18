package iss.soa.lightService.controller;

import iss.soa.lightService.model.RoomLight;

import java.util.Arrays;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.obix.*;
import org.eclipse.om2m.commons.obix.io.*;



@RestController
public class LightResource {
	
	private RoomLight room = new RoomLight(0);
	
	/**
	 * Trying to get value from OM2M
	 */
	@GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
	public int lightValue() {
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		// Set http request headers
		//headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_XML }));
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room1CT/room1SensorsCT/light/DATA/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		//System.out.println(resp);
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		//System.out.println("------------------------------------");
		//System.out.println(cin.getContent());
		//System.out.println("------------------------------------");
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		//System.out.println(obj.getObjGroup());
		Int ret = (Int) obj.getObjGroup().get(2);
		//System.out.println(ret.getVal());
		//System.out.println(ret.getVal().intValue());

		return ret.getVal().intValue();
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
