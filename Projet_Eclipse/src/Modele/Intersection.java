package Modele;

public class Intersection {
	String id;
	float latitude;
	float longitude;
	
	public Intersection(String id, float latitude, float longitude) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public String getId() {
		return id;
	}
}
