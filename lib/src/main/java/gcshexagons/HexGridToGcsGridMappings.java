package gcshexagons;



import gcshexagon.coordinates.GcsCoordinates;
import gcshexagon.coordinates.HexCoordinates;;

public class HexGridToGcsGridMappings {

	public static final int[][] DIRECTIONS = { { 0, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { -1, 0 },
			/* { 0, 0 }, */ { 1, 0 }, { -1, 1 }, { 0, 1 }, { 1, 1 } };

	private final double hexwidth; // degrees

	private final double innerRadius, outerRadius; // degrees
	private final double innerRadiusSquared, outerRadiusSquared;
	private final double longitudeSpacing, latitudeSpacing;

	private final double referenceLatitude, referenceLongitude;

	private final static UnitHexagon UNIT_HEXAGON = new UnitHexagon();

	public HexGridToGcsGridMappings(int hexwidthMiles) {
		this(hexwidthMiles, 0d, 0d);
	}

	public HexGridToGcsGridMappings(int hexwidthMiles, double latitude, double longitude) {
		this.referenceLatitude = latitude;
		this.referenceLongitude = longitude;
		this.hexwidth = (hexwidthMiles / Constants.EARTH_CIRCUMFERENCE_MILES) * 360d;

		innerRadius = hexwidth / 2;
		outerRadius = hexwidth / Math.sqrt(3);

		innerRadiusSquared = Math.pow(innerRadius, 2);
		outerRadiusSquared = Math.pow(outerRadius, 2);

		longitudeSpacing = Math.sqrt(3) * outerRadius;
		latitudeSpacing = 3.0 / 2.0 * outerRadius;
	}

	public GcsCoordinates gridToGcs(HexCoordinates in) {
		return gridToGcs(in.column(), in.row());
	}
	
	public GcsCoordinates gridToGcs(int col, int row) {

		double latitude = latitudeSpacing * row;
		double longitude;
		if (row % 2 == 0) {
			longitude = longitudeSpacing * col;
		} else {
			longitude = longitudeSpacing * col + longitudeSpacing / 2.0;
		}
		return GcsCoordinates.of(referenceLatitude + latitude, referenceLongitude + longitude);
	}
	
	public HexCoordinates gcsToGrid(GcsCoordinates in) {
		return gcsToGrid(in.latitude(), in.longitude());
	}

	public HexCoordinates gcsToGrid(double latitude, double longitude) {
		double realLatitude = latitude - referenceLatitude;
		double realLongitude = longitude - referenceLongitude;

		int rowRounded = (int) Math.round(realLatitude / latitudeSpacing);

		double colDouble;
		if (rowRounded % 2 == 0) {
			colDouble = realLongitude / longitudeSpacing;
		} else {
			colDouble = (realLongitude - (longitudeSpacing / 2.0)) / longitudeSpacing;
		}

		int colRounded = (int) Math.round(colDouble);

		HexCoordinates results = null;
		double bestSoFar = Double.MAX_VALUE;

		for (var i : DIRECTIONS) {
			var mapped = gridToGcs(colRounded + i[0], rowRounded + i[1]);

			double valid = validate(latitude, longitude, mapped);

			if (valid < bestSoFar) {
				results = (HexCoordinates.of(colRounded + i[0], rowRounded + i[0]));
			}
		}

		return results;


	}

	double validate(double latitude, double longitude, GcsCoordinates gcsCoordinates) {

		double distance = Math.pow(latitude - gcsCoordinates.latitude(), 2.0)
				+ Math.pow(longitude - gcsCoordinates.longitude(), 2.0);

		if (compare(distance, innerRadiusSquared) <= 0)
			return 0;

		if (compare(distance, outerRadiusSquared) > 0)
			return Double.MAX_VALUE;

		var scaledHexagon = UNIT_HEXAGON.scaleAndTransform(outerRadius, gcsCoordinates.latitude(),
				gcsCoordinates.longitude());
		
		var result = scaledHexagon.inside(latitude, longitude);

		return result;
	}

	static double compare(double a, double b) {
		// This might be max....
		var maxUlp = Math.min(Math.ulp(a), Math.ulp(b));
		if (Math.abs(a - b) < maxUlp)
			return 0;
		else
			return a - b;
	}

}
