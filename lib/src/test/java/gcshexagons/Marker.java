package gcshexagons;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class Marker {

	@JsonProperty("MarkerID")
	public String markerID;
	
	@JsonProperty("Marker No.")
	public String markerNumber;

	@JsonProperty("Title")
	public String title;
	
	@JsonProperty("Subtitle")
	public String subtitle;
	
	@JsonProperty("Latitude (minus=S)")
	public Double latitude;
	
	@JsonProperty("Longitude (minus=W)")
	public Double longitude;
	
	@JsonProperty("Link")
	public String link;
	
	@JsonAnySetter
    public void addOtherInfo(String propertyKey, Object value) {
       
    } 

}
