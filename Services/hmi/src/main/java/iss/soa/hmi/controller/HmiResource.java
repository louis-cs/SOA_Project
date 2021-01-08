package iss.soa.hmi.controller;

import java.time.LocalTime;
import java.util.List;

import org.eclipse.om2m.commons.obix.Mapper;
import org.eclipse.om2m.commons.resource.URIList;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import iss.soa.hmi.model.Hmi;

@RestController
public class HmiResource {
	
	Hmi hmi = new Hmi();
	
	/**
	 * Change loop state
	 */
	@GetMapping("/loop")
	public boolean loop(){
		hmi.loop();
		return hmi.getLoop();
	}
	
	@GetMapping("/alarm")
	public String alarm(@RequestParam String room, @RequestParam String id){
		RestTemplate rt = new RestTemplate();
		return (String)rt.getForEntity(hmi.alarms+hmi.set(room, id, "false"), String.class).getBody();
	}

	/**
	 * Set&Get time
	 * @param t
	 * @return
	 */
	@GetMapping("/time")
	public LocalTime time(@RequestParam int h, @RequestParam int m){
		Hmi.time = LocalTime.of(h, m);
		return Hmi.time;
	}
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns graphic interface NOT ; values and logs
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public String ui(){
		String log = "\n________________________________________________________\n"
					+ "\n			OM2M action log"
					+ "\n________________________________________________________\n";
		hmi.appendUi(log);
		hmi.appendUi(hmi.log);
		hmi.log = "";
		return hmi.getUi();
	}
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
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
		String url = "http://127.0.0.1:8080/~/in-cse?fu=1&" + var; //var = "ty=x"
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

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	@GetMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
	public String reset() {
		String uri, gui = "", room = "", id = "";
		ResponseEntity<String> Response;
		RestTemplate rt = new RestTemplate();
		for(int i=1; i<4; i++){
			room += i;
			uri = hmi.lights + hmi.set(room, "", "300");		
			Response = rt.getForEntity(uri, String.class);
			gui += Response.getBody();
			uri = hmi.movements + hmi.set(room, "", "true");
			Response = rt.getForEntity(uri, String.class);
			gui += Response.getBody();
			uri = hmi.temperatureInside + hmi.set(room, "", "21");
			Response = rt.getForEntity(uri, String.class);
			gui += Response.getBody();
			uri = hmi.temperatureOutside + hmi.set(room, "", "19");
			Response = rt.getForEntity(uri, String.class);
			gui += Response.getBody();
			uri = hmi.alarms + hmi.set(room, "", "false");
			Response = rt.getForEntity(uri, String.class);
			gui += Response.getBody();
			for(int j=1; j<=i; j++){
				id += j;
				uri = hmi.leds + hmi.set(room, id, "false");
				Response = rt.getForEntity(uri, String.class);
				gui += Response.getBody();
				uri = hmi.doors + hmi.set(room, id, "false");
				Response = rt.getForEntity(uri, String.class);
				gui += Response.getBody();
				uri = hmi.windows + hmi.set(room, id, "false");
				Response = rt.getForEntity(uri, String.class);
				gui += Response.getBody();
				uri = hmi.radiators + hmi.set(room, id, "false");
				Response = rt.getForEntity(uri, String.class);
				gui += Response.getBody();
			}
		}
		return gui;
	}
}
