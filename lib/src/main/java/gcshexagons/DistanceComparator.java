package gcshexagons;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import gcshexagon.coordinates.GcsCoordinates;
import gcshexagon.coordinates.Haversine;

public class DistanceComparator implements Comparator<GcsCoordinates> {

	private final GcsCoordinates reference;
	private final Map<GcsCoordinates, Double> distance = new HashMap<>();

	public DistanceComparator(GcsCoordinates reference) {
		super();
		this.reference = reference;
	}

	public double distance(final GcsCoordinates point) {
		return distance.computeIfAbsent(point, a -> Haversine.haversineMiles(a, reference));
	}

	@Override
	public int compare(GcsCoordinates o1, GcsCoordinates o2) {
		return Double.compare(distance(o1), distance(o2));
	}

	@Override
	public String toString() {
		return "DistanceComparator [reference=" + reference + "]";
	}

	public static void main(String[] args) {
		var home = GcsCoordinates.of(37.4917419, -122.2299191);
		var amy = GcsCoordinates.of(37.8219085, -121.9974116);
		var sedgemoor = GcsCoordinates.of(51.3237343, -0.9986642);

		var locations = new GcsCoordinates[] { amy, home, sedgemoor };

		Arrays.sort(locations, new DistanceComparator(home));
		System.out.println(Arrays.toString(locations));

	}

}
