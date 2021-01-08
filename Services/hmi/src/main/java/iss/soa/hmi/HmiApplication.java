package iss.soa.hmi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import iss.soa.hmi.model.Hmi;

@SpringBootApplication
public class HmiApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmiApplication.class, args);

		/*
		 * SOA Project Main:
		 * Automatic control system
		 */
		Hmi hmi = new Hmi();
		RestTemplate rt = new RestTemplate();
		
		//autom
		while(true){
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//activated?
			System.out.println(hmi.getLoop());
			if (hmi.getLoop()) {
				hmi.clearUi();
				//for every room
				for(int i=1; i<=3; i++){
					//for every number of device per room
					for(int j=i; j<=3; j++){
						String room="", id=""; room += i; id += j;
						//httpGET all OM2M values
						hmi.appendUi(hmi.getAllOM2M(rt, room, id));
						
						//time - alarm
						if (true){
							
						}
						//temperature
							
						//window
							
						//door
						
					}
				}
			}
		}
	}
}
