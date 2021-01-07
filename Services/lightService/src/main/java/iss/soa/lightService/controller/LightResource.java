package iss.soa.lightService.controller;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.eclipse.om2m.commons.resource.*;

import org.eclipse.om2m.commons.obix.*;
import org.eclipse.om2m.commons.obix.io.*;


@RestController
public class LightResource {
	
	/**
	 * Get sensor value from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/light", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getLight(@RequestParam String room, @RequestParam String id) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/light" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Int ret = (Int) obj.getObjGroup().get(2);

		return ret.getVal().intValue();
	}
	
	/**
	 * Set sensor value to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/light/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setLight(@RequestParam String room, @RequestParam String id, @RequestParam String val) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;light&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;luminosity&quot;/&gt;"
				+ "        &lt;int name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;lux&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/light" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;		
	}
	
	/**
	 * Create light
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/light/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String lightCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"light"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	/**
	 * Delete light
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/light/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String delLight(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");	
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/light" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get led state from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/led", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getLedState(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/led" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal().booleanValue();		
	}

	/**
	 * Set led state to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/led/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setLedState(@RequestParam String room, @RequestParam String id, @RequestParam String val){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;light&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;led&quot;/&gt;"
				+ "        &lt;bool name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/led" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Create led
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/led/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String ledCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"led"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Delete led
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/led/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String delLed(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/led" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
}
