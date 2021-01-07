package iss.soa.movementService.controller;

import org.eclipse.om2m.commons.obix.Bool;
import org.eclipse.om2m.commons.obix.Mapper;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.io.ObixDecoder;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class MovementResource {
	/**
	 * Get sensor value from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/movement", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getMovement(@RequestParam String room, @RequestParam String id) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/movement" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal().booleanValue();
	}
	
	/**
	 * Set sensor value to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/movement/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setMovement(@RequestParam String room, @RequestParam String id, @RequestParam String val) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;movement&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;presence&quot;/&gt;"
				+ "        &lt;bool name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/movement" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;		
	}
	
	/**
	 * Create movement
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/movement/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String movementCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"movement"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	/**
	 * Delete movement
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/movement/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String movementDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");	
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/movement" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get door state from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/door", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getDoor(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/door" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal().booleanValue();		
	}

	/**
	 * Set door state to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/door/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setDoor(@RequestParam String room, @RequestParam String id, @RequestParam String val){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;movement&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;door&quot;/&gt;"
				+ "        &lt;bool name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/door" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Create door
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/door/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String doorCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"door"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Delete door
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/door/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String doorDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/door" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get alarm state from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/alarm", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getAlarm(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/alarm" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal().booleanValue();		
	}

	/**
	 * Set door state to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/alarm/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setAlarm(@RequestParam String room, @RequestParam String id, @RequestParam String val){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;movement&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;alarm&quot;/&gt;"
				+ "        &lt;bool name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/alarm" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Create door
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/alarm/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String alarmCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"alarm"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Delete door
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/alarm/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String alarmDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/alarm" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
}
