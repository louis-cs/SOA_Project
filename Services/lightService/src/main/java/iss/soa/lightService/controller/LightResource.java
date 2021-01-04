package iss.soa.lightService.controller;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.eclipse.om2m.commons.resource.*;

import java.util.List;

import org.eclipse.om2m.commons.obix.*;
import org.eclipse.om2m.commons.obix.io.*;


@RestController
@RequestMapping("/light")
public class LightResource {
	
	/**
	 * Trying to get value from OM2M
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public int lightValue() {
		RestTemplate rt = new RestTemplate();		
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-m2m-origin", "admin:admin");
		headers.set("Content-type", "application/xml");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room1CT/room1SensorsCT/light/DATA/la";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET, entity, String.class);
		String resp = (String)response.getBody();
		
		Mapper mapper = new Mapper();		
		ContentInstance cin = (ContentInstance)mapper.unmarshal(resp);
		
		Obj obj = ObixDecoder.fromString(cin.getContent());
		Int ret = (Int) obj.getObjGroup().get(2);

		return ret.getVal().intValue();
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Trying to set value from OM2M
	 */
	@GetMapping(value = "/set/{val}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String setValue(@PathVariable String val) {
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
		
		String url = "http://127.0.0.1:8080/~/in-cse/in-name/insaRoomsAE/room1CT/room1SensorsCT/light/DATA";
		
		ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, entity, String.class);
		String resp = (String)response.getBody();
		return resp;
	}
}
