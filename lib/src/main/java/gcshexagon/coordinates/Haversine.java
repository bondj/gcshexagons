package gcshexagon.coordinates;

import gcshexagons.Constants;

public class Haversine {

	public static double haversineMiles(GcsCoordinates start, GcsCoordinates end) {
		return haversine(start.latitude(), start.longitude(), end.latitude(), end.longitude(),
				Constants.EARTH_RADIUS_MILES);
	}

	public static double haversineMiles(double lat1, double lon1, double lat2, double lon2) {
		return haversine(lat1, lon1, lat2, lon2, Constants.EARTH_RADIUS_MILES);
	}

	static double haversine(double lat1, double lon1, double lat2, double lon2, double radius) {
		// distance between latitudes and longitudes
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		// convert to radians
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// apply formulae
		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);

		double c = 2 * Math.asin(Math.sqrt(a));
		return radius * c;
	}

	static double bearing(double lat1, double lon1, double lat2, double lon2) {
		double longitude1 = lon1;
		double longitude2 = lon2;
		double latitude1 = Math.toRadians(lat1);
		double latitude2 = Math.toRadians(lat2);
		double longDiff = Math.toRadians(longitude2 - longitude1);
		double y = Math.sin(longDiff) * Math.cos(latitude2);
		double x = Math.cos(latitude1) * Math.sin(latitude2)
				- Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

		return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
	}

}
