package iss.soa.temperatureService.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.eclipse.om2m.commons.obix.Bool;
import org.eclipse.om2m.commons.obix.Int;
import org.eclipse.om2m.commons.obix.Mapper;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.io.ObixDecoder;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class TemperatureResource {
	
	/**
	 * Get sensor value from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/temperature/inside", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getTemperatureInside(@RequestParam String room, @RequestParam String id) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/temperatureInside" + id + "/la";
		
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
	@GetMapping(value = "/temperature/inside/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setTemperatureInside(@RequestParam String room, @RequestParam String id, @RequestParam String val) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;temperature&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;inside temperature&quot;/&gt;"
				+ "        &lt;int name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;°C&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/temperatureInside" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;		
	}
	
	/**
	 * Create temperature
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/temperature/inside/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String temperatureInsideCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"temperatureInside"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	/**
	 * Delete temperature
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/temperature/inside/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String temperatureInsideDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");	
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/temperatureInside" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////	

	/**
	 * Get sensor value from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/temperature/outside", produces = MediaType.APPLICATION_JSON_VALUE)
	public int getTemperatureOutside(@RequestParam String room, @RequestParam String id) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/temperatureOutside" + id + "/la";
		
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
	@GetMapping(value = "/temperature/outside/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setTemperatureOutside(@RequestParam String room, @RequestParam String id, @RequestParam String val) {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;temperature&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;outside temperature&quot;/&gt;"
				+ "        &lt;int name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;°C&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/temperatureOutside" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;		
	}
	
	/**
	 * Create temperature
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/temperature/outside/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String temperatureOutsideCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"temperatureOutside"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	/**
	 * Delete temperature
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/temperature/outside/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String temperatureOutsideDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");	
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "SensorsCT/temperatureOutside" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get window state from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/window", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getWindow(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/window" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal().booleanValue();		
	}

	/**
	 * Set window state to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/window/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setWindow(@RequestParam String room, @RequestParam String id, @RequestParam String val){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;temperature&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;window&quot;/&gt;"
				+ "        &lt;bool name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/window" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Create window
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/window/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String windowCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"window"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Delete window
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/window/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String windowDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/window" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Get window state from OM2M
	 * @param room
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/radiator", produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean getRadiator(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/radiator" + id + "/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Bool ret = (Bool) obj.getObjGroup().get(2);

		return ret.getVal().booleanValue();		
	}

	/**
	 * Set window state to OM2M
	 * @param room
	 * @param id
	 * @param val
	 * @return
	 */
	@GetMapping(value = "/radiator/set", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setRadiator(@RequestParam String room, @RequestParam String id, @RequestParam String val){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=4");
		
		String body = "<m2m:cin xmlns:m2m=\"http://www.onem2m.org/xml/protocols\">"
				+ "    <cnf>message</cnf>"
				+ "    <con>"
				+ "      &lt;obj&gt;"
				+ "        &lt;str name=&quot;appId&quot; val=&quot;temperature&quot;/&gt;"
				+ "        &lt;str name=&quot;category&quot; val=&quot;radiator&quot;/&gt;"
				+ "        &lt;bool name=&quot;data&quot; val=&quot;" + val + "&quot;/&gt;"
				+ "        &lt;str name=&quot;unit&quot; val=&quot;boolean&quot;/&gt;"
				+ "      &lt;/obj&gt;"
				+ "    </con>"
				+ "</m2m:cin>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/window" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Create window
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/radiator/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public String radiatorCreate(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		String body = "<m2m:cnt xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" rn=\"radiator"+id+"\"></m2m:cnt>";		
		
		HttpEntity<String> entity = new HttpEntity<String>(body, headers);		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
	
	/**
	 * Delete window
	 * @param room
	 * @param id
	 * @return
	 */	
	@GetMapping(value = "/radiator/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public String radiatorDelete(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml;ty=3");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room"
				+ room + "CT/room" + room + "ActuatorsCT/radiator" + id;
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.DELETE, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
}
