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
}
