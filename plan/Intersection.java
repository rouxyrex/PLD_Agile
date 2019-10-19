package plan;


public class Intersection {
	String id;
	float lattitude;
	float longitude;
	
	public Intersection(String id, float lattitude, float longitude) {
		this.id = id;
		this.lattitude = lattitude;
		this.longitude = longitude;
	}
	
	public float getLattitude() {
		return lattitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public String getId() {
		return id;
	}
}
