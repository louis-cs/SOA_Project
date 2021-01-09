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

import iss.soa.hmi.model.Device;
import iss.soa.hmi.model.Hmi;

@RestController
public class HmiResource {

	Hmi hmi = new Hmi();

	/**
	 * Change loop state
	 */
	@GetMapping("/loop")
	public boolean loop() {
		hmi.loop();
		return hmi.getLoop();
	}

	/**
	 * Set&Get time
	 * 
	 * @param t
	 * @return
	 */
	@GetMapping("/time/{h}/{m}")
	public LocalTime time(@PathVariable int h, @PathVariable int m) {
		Hmi.time = LocalTime.of(h, m);
		return Hmi.time;
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	@GetMapping("/alarm")
	public String alarm() {
		RestTemplate rt = new RestTemplate();
		for (int room = 1; room <= 3; room++) {
			rt.getForEntity(hmi.devices.get("alarm").url + hmi.set(String.valueOf(room), "", "false"), String.class)
			.getBody();
		}
		return "false";
	}

	@GetMapping("/movement/{state}")
	public String movement(@PathVariable boolean state) {
		RestTemplate rt = new RestTemplate();
		for (int room = 1; room <= 3; room++) {
			rt.getForEntity(
					hmi.devices.get("movement").url
					+ hmi.set(String.valueOf(room), "", String.valueOf(state)),
					String.class).getBody();
		}
		return String.valueOf(state);
	}

	@GetMapping("/light/{val}")
	public String light(@PathVariable int val) {
		RestTemplate rt = new RestTemplate();
		for (int room = 1; room <= 3; room++) {
			rt.getForEntity(hmi.devices.get("light").url + hmi.set(String.valueOf(room), "", String.valueOf(val)),
					String.class).getBody();
		}
		return String.valueOf(val);
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	/**
	 * Returns graphic interface NOT ; values and logs
	 * 
	 * @return
	 */
	@GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public String ui() {
		String log = hmi.getUi() + "\n________________________________________________________\n"
				+ "\n		OM2M action log" + "\n________________________________________________________\n" + Hmi.log;
		// cleaning log
		Hmi.log = "";
		return log;
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	/**
	 * Retrieving URI from OM2M
	 * 
	 * @return urls
	 */
	@GetMapping(value = "/r/{var}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String retrieve(@PathVariable String var) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Accept", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		String url = "http://127.0.0.1:8080/~/in-cse?fu=1&" + var; // var =
		// "ty=x"
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String) response.getBody();
		Mapper mapper = new Mapper();
		URIList uri = (URIList) mapper.unmarshal(resp);
		List<String> list = uri.getListOfUri();
		resp = "Request: \"" + var + "\"\nFound ->\n";
		for (String s : list) {
			resp += s + "\n";
		}
		return resp;
	}

	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	@GetMapping(value = "/reset", produces = MediaType.TEXT_PLAIN_VALUE)
	public String reset() {
		String uri = "", gui = "";
		RestTemplate rt = new RestTemplate();
		for (String deviceKey : hmi.devices.keySet()) {
			Device device = hmi.devices.get(deviceKey);
			for (int room = 1; room <= 3; room++) {
				if (device.hasId) {
					for (int id = 1; id <= room; id++) {
						uri = device.url
								+ hmi.set(String.valueOf(room), String.valueOf(id), String.valueOf(device.state));
						gui += rt.getForEntity(uri, String.class).getBody();
					}
				} else {
					if (device.isBool) {
						uri = device.url + hmi.set(Integer.toString(room), "", String.valueOf(device.state));

					} else {
						uri = device.url + hmi.set(Integer.toString(room), "", Integer.toString(device.value));
					}
					gui += rt.getForEntity(uri, String.class).getBody();
				}
			}
		}
		return gui;
	}
}
