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

		int lum, inside, outside;
		boolean presence, alarm, window, door, radiator, led;

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
				for (int room = 1; room <= 3; room++) {
					// for every device
					for (int id = 1; id <= room; id++) {

						// httpGET all OM2M values
						hmi.appendUi(hmi.getAllOM2M(rt, String.valueOf(room), String.valueOf(id)));

						alarm = hmi.devices.get("alarm").state;
						presence = hmi.devices.get("movement").state;
						led = hmi.devices.get("led").state;
						window = hmi.devices.get("window").state;
						door = hmi.devices.get("door").state;
						radiator = hmi.devices.get("radiator").state;
						lum = hmi.devices.get("light").value;
						inside = hmi.devices.get("temperatureInside").value;
						outside = hmi.devices.get("temperatureOutside").value;

						// time - alarm
						if (!alarm) {
							// outside operating time
							if (Hmi.time.isAfter(LocalTime.of(22, 0)) || Hmi.time.isBefore(LocalTime.of(7, 0))) {
								if (presence) {
									hmi.uiResp = rt.getForEntity(
											hmi.devices.get("alarm").url + hmi.set(String.valueOf(room), "", "true"),
											String.class);
									Hmi.log += "\n	Room(" + room + ") -	ALARM - ON";
								} else {
									// led
									if (led) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("led").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - OFF";
									}
									// window
									if (window) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE";
									}
									// door
									if (door) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("door").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Door(" + id + ") - CLOSE";
										;
									}
									// radiator
									if (radiator) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("radiator").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - OFF";
									}
								}
								// normal mode: light&temperature
							} else {
								// lighting
								if (presence && lum < 500) {
									hmi.uiResp = rt.getForEntity(
											hmi.devices.get("light").url
													+ hmi.set(String.valueOf(room), String.valueOf(id), "true"),
											String.class);
									Hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - ON";
								} else if (lum > 1500) {
									hmi.uiResp = rt.getForEntity(
											hmi.devices.get("led").url
													+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
											String.class);
									Hmi.log += "\n	Room(" + room + ") -	Led(" + id + ") - OFF";
								}
								// temperature
								if (!window) {
									if (inside > 24 && inside > outside) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "true"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - OPEN";
									} else if (inside < 19 && inside < outside) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "true"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - OPEN";
									}
								}
								if (window) {
									if (inside < 19 && outside < 19) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE";
										// radiator
										if (!radiator) {
											hmi.uiResp = rt.getForEntity(
													hmi.devices.get("radiator").url
															+ hmi.set(String.valueOf(room), String.valueOf(id), "true"),
													String.class);
											Hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - ON";
										}
									} else if (inside > 24 && outside > 24) {
										hmi.uiResp = rt.getForEntity(
												hmi.devices.get("window").url
														+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
												String.class);
										Hmi.log += "\n	Room(" + room + ") -	Window(" + id + ") - CLOSE";
										// radiator
										if (radiator) {
											hmi.uiResp = rt.getForEntity(hmi.devices.get("radiator").url
													+ hmi.set(String.valueOf(room), String.valueOf(id), "false"),
													String.class);
											Hmi.log += "\n	Room(" + room + ") -	Radiator(" + id + ") - OFF";
										}
									}
								}
							}
						} else {
							Hmi.log += "\n !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! !!!! ALARM !!!! "
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
