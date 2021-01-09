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
		 * SOA Project Main: Automatic control system
		 */
		Hmi hmi = new Hmi();
		RestTemplate rt = new RestTemplate();

		// autom
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// activated?
			if (hmi.getLoop()) {
				hmi.clearUi();
				// for every room
				for (int i = 1; i <= 3; i++) {
					String room = "";
					room += i;
					// for every device
					for (int j = 1; j <= i; j++) {
						String id = "";
						id += j;

						// httpGET all OM2M values
						hmi.appendUi(hmi.getAllOM2M(rt, room, id));

						// time - alarm
						if (!hmi.devices.get("alarm").state) {
							// outside operating time
							if (Hmi.time.isAfter(LocalTime.of(22, 0)) || Hmi.time.isBefore(LocalTime.of(7, 0))) {
								if (hmi.devices.get("movement").state) {
									hmi.uiResp = rt.getForEntity(
											hmi.devices.get("alarm").url + hmi.set(room, "", "true"), String.class);
									hmi.log += "\n	Room(" + room + ") -	ALARM - ON	|	" + hmi.uiResp.getBody();
								} else {
									// window
									if (hmi.devices.get("window").state) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url + hmi.set(room, id, "false"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE	|	"
												+ hmi.uiResp.getBody();
									}
									// door
									if (hmi.devices.get("door").state) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("door").url + hmi.set(room, id, "false"), String.class);
										hmi.log += "\n	Room(" + room + ") -	Door(" + id + ") - CLOSE	|	"
												+ hmi.uiResp.getBody();
									}
									// temperature
									if (hmi.devices.get("radiator").state) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("radiator").url + hmi.set(room, id, "false"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - OFF	|	"
												+ hmi.uiResp.getBody();
									}
								}
								// normal mode: light&temperature
							} else {
								// lighting
								if (hmi.devices.get("movement").state && hmi.devices.get("light").value < 500) {
									hmi.uiResp = rt.getForEntity(
											hmi.devices.get("light").url + hmi.set(room, id, "true"), String.class);
									hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - ON	|	"
											+ hmi.uiResp.getBody();
								} else if (hmi.devices.get("light").value > 1500) {
									hmi.uiResp = rt.getForEntity(
											hmi.devices.get("light").url + hmi.set(room, id, "false"), String.class);
									hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - OFF	|	"
											+ hmi.uiResp.getBody();
								}
								// temperature
								if (!hmi.devices.get("window").state) {
									if (hmi.devices.get("temperatureInside").value > 24
											&& hmi.devices.get("temperatureInside").value > hmi.devices
													.get("temperatureOutside").value) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url + hmi.set(room, id, "true"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - OPEN	|	"
												+ hmi.uiResp.getBody();
									} else if (hmi.devices.get("temperatureInside").value < 19
											&& hmi.devices.get("temperatureInside").value < hmi.devices
													.get("temperatureOutside").value) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url + hmi.set(room, id, "true"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - OPEN	|	"
												+ hmi.uiResp.getBody();
									}
								}
								if (hmi.devices.get("window").state) {
									if (hmi.devices.get("temperatureInside").value < 19
											&& hmi.devices.get("temperatureOutside").value < 19) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url + hmi.set(room, id, "false"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE	|	"
												+ hmi.uiResp.getBody();
										// radiator
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("radiator").url + hmi.set(room, id, "true"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - ON	|	"
												+ hmi.uiResp.getBody();
									} else if (hmi.devices.get("temperatureInside").value > 24
											&& hmi.devices.get("temperatureOutside").value > 24) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url + hmi.set(room, id, "false"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE	|	"
												+ hmi.uiResp.getBody();
										// radiator
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("radiator").url + hmi.set(room, id, "false"),
												String.class);
										hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - OFF	|	"
												+ hmi.uiResp.getBody();
									}
								}
							}
						} else {
							hmi.log = "\n !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! "
									+ "\n !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! "
									+ "\n !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! "
									+ "\n !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! ";
						}
					}
				}
			}
		}
	}
}
