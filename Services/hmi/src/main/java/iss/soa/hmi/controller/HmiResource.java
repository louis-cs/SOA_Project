package iss.soa.hmi.controller;

import java.util.List;

import org.eclipse.om2m.commons.obix.Mapper;
import org.eclipse.om2m.commons.resource.URIList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import iss.soa.hmi.model.Hmi;

@RestController
public class HmiResource {
	
	private Hmi hmi = new Hmi();
	
	/**
	 * Change loop state
	 */
	@GetMapping("/loop")
	public void loop(){
		hmi.loop();
	}
	
	/**
	 * Retrieving URI from OM2M
	 * @return urls
	 */
	@GetMapping(value = "/r/{var}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String retrieve(@PathVariable String var) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Accept", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse?fu=1&" + var; //var = "ty=x" //var = "rn=DATA"
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		URIList uri = (URIList)mapper.unmarshal(resp);
		
		List<String> list = uri.getListOfUri();
		resp = "Request: \"" + var + "\"\nFound ->\n";
		
		for(String s : list){
			resp += s + "\n";
		}
		return resp;
	}

	/*
	 * 	!! ROOM Service should be located in another Spring-Maven project !!
	 */
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	/**
	 * Create room with sensors and actuators
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/room/create/{room}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String roomCreate(@PathVariable String room){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"room"+room+"CT\"></m2m:cnt>";		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE";		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();

		url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"+room+"CT";
		body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"room"+room+"SensorsCT\"></m2m:cnt>";
		entity = new HttpEntity<String>(body, headers);
		response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		resp += (String)response.getBody();		
		body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"room"+room+"ActuatorsCT\"></m2m:cnt>";
		entity = new HttpEntity<String>(body, headers);	
		response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		resp += (String)response.getBody();
		
		return resp;
	}
	/**
	 * Delete room
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/room/delete/{room}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String roomDelete(@PathVariable String room){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");	
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room" + room + "CT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	/**
	 * SOA Project Main:
	 * Automatic control system
	 */
	@GetMapping
	public String main() {
		RestTemplate rt = new RestTemplate();
		
		String uri;
		uri = hmi.light + hmi.get("1", "");
		System.out.println(uri);
		
		ResponseEntity<String> response = rt.getForEntity(uri, String.class);
		String resp = (String)response.getBody();
		
		return resp;
	}
}
