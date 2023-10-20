package gcshexagon.coordinates;

import gcshexagons.Constants;
import gcshexagons.DoublePoint;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class GcsCoordinates {
	
	public static GcsCoordinates of(double latitude, double longitude) {
		return new GcsCoordinates(latitude, longitude);
	}

	private final double latitude, longitude;

	/**
	 * Converts to double point, mapping longitude to x, and latitude to y
	 * 
	 * @return
	 */
	public DoublePoint toDoublePoint() {
		return DoublePoint.of(longitude, latitude);
	}

	public String toString() {
		return "(" + Constants.DECIMAL_FORMAT.format(latitude) + "," + Constants.DECIMAL_FORMAT.format(longitude) + ")";
	}
}
