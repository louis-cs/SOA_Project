package iss.soa.lightService.controller;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.eclipse.om2m.commons.resource.*;

import java.util.List;

import org.eclipse.om2m.commons.obix.*;
import org.eclipse.om2m.commons.obix.io.*;


@RestController
public class LightResource {
	
	/**
	 * Get sensor value from OM2M
	 */
	@GetMapping(value = "/light/{room}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getLightValue(@PathVariable int room, @PathVariable int id) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room
				+ "SensorsCT/light" + id + "/DATA/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Int ret = (Int) obj.getObjGroup().get(2);

		return ret.getVal().intValue();
	}
	
	/**
	 * Set sensor value from OM2M
	 */
	@GetMapping(value = "/light/set/{room}/{id}/{val}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setValue(@PathVariable int room, @PathVariable int id, @PathVariable int val) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;light&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;luminosity &quot;/&gt;"
				+ "        &lt;int name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;lux&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room
				+ "SensorsCT/light" + id + "/DATA";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;		
	}
	

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get led state from OM2M
	 */
	@GetMapping(value = "/led/{room}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean getLedState(@PathVariable int room, @PathVariable int id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room
				+ "ActuatorsCT/led" + id + "/DATA/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal();		
	}
	
	/**
	 * Get led state from OM2M
	 */
	@GetMapping(value = "/led/set/{room}/{id}/{bool}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setLedState(@PathVariable int room, @PathVariable int id, @PathVariable boolean bool){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;light&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;luminosity &quot;/&gt;"
				+ "        &lt;boolean name=&quot;data&quot; val=&quot;" + bool + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room
				+ "ActuatorsCT/led" + id + "/DATA/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
}
