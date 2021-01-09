package iss.soa.hmi.model;

public class Device {
	public String url;
	public boolean isBool;
	public int value;
	public boolean state;
	public boolean hasId;
	
	/**
	 * Device object
	 * @param url
	 * @param isBool
	 * @param value
	 * @param state
	 */
	public Device(String url, boolean isBool, int value, boolean state, boolean hasId){
		this.url = url;
		this.isBool = isBool;
		this.value = value;
		this.state = state;
		this.hasId = hasId;
	}
}
