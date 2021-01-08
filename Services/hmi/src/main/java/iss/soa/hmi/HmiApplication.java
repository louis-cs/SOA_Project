package iss.soa.hmi;

import java.time.LocalTime;

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
					String room = ""; room += i;
					//for every device
					for(int j=1; j<=i; j++){
						String id = ""; id += j;
					
						//httpGET all OM2M values
						hmi.appendUi(hmi.getAllOM2M(rt, room, id));
					
						//time - alarm
						if(!hmi.alarm){
							//outside operating time
							if(Hmi.time.isAfter(LocalTime.of(22, 0)) || Hmi.time.isBefore(LocalTime.of(7, 0))){
								if (hmi.presence) {
									hmi.uiResp = rt.getForEntity(hmi.alarms + hmi.set(room, "", "true"), String.class);
									hmi.log += "\n	Room(" + room + ") -	ALARM - ON	|	" + hmi.uiResp.getBody();
								}else{
									//window
									if(hmi.window){
										hmi.uiResp = rt.getForEntity(hmi.windows + hmi.set(room, id, "false"), String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE	|	" + hmi.uiResp.getBody();
									}
									//door
									if(hmi.door){
										hmi.uiResp = rt.getForEntity(hmi.doors + hmi.set(room, id, "false"), String.class);
										hmi.log += "\n	Room(" + room + ") -	Door(" + id + ") - CLOSE	|	" + hmi.uiResp.getBody();
									}
									//temperature
									if(hmi.radiator){
										hmi.uiResp = rt.getForEntity(hmi.radiators + hmi.set(room, id, "false"), String.class);
										hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - OFF	|	" + hmi.uiResp.getBody();
									}
								}
								//normal mode: light&temperature
							}else{
								//lighting
								if(hmi.presence && hmi.lum<500){
									hmi.uiResp = rt.getForEntity(hmi.lights + hmi.set(room, id, "true"), String.class);
									hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - ON	|	" + hmi.uiResp.getBody();
								}else if(hmi.lum>1500){
									hmi.uiResp = rt.getForEntity(hmi.lights + hmi.set(room, id, "false"), String.class);
									hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - OFF	|	" + hmi.uiResp.getBody();
								}
								//temperature
								if(!hmi.window){
									if(hmi.inside>24 && hmi.inside>hmi.outside){
										hmi.uiResp = rt.getForEntity(hmi.windows + hmi.set(room, id, "true"), String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - OPEN	|	" + hmi.uiResp.getBody();
									}else if(hmi.inside<19 && hmi.inside<hmi.outside){
										hmi.uiResp = rt.getForEntity(hmi.windows + hmi.set(room, id, "true"), String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - OPEN	|	" + hmi.uiResp.getBody();
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
