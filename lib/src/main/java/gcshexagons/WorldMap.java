package gcshexagons;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import gcshexagon.coordinates.HexCoordinates;

public class WorldMap<T> {

	private final Map<HexCoordinates, Set<T>> combinedMarkers = new HashMap<>();

	public WorldMap(Map<HexCoordinates, Collection<T>> markers) {
		for (var marker : markers.entrySet()) {
			for (var direction : HexGridToGcsGridMappings.DIRECTIONS) {
				HexCoordinates gridCoordinates = marker.getKey();
				HexCoordinates newLocation = HexCoordinates.of(gridCoordinates.column() + direction[0],
						gridCoordinates.row() + direction[1]);
				combinedMarkers.computeIfAbsent(newLocation, m -> new HashSet<T>()).addAll(marker.getValue());
			}
		}
	}

	public WorldMap(Collection<T> markers, Function<T, HexCoordinates> func) {
		for (var marker : markers) {
			for (var direction : HexGridToGcsGridMappings.DIRECTIONS) {
				HexCoordinates gridCoordinates = func.apply(marker);
				HexCoordinates newLocation = HexCoordinates.of(gridCoordinates.column() + direction[0],
						gridCoordinates.row() + direction[1]);
				combinedMarkers.computeIfAbsent(newLocation, m -> new HashSet<T>()).add(marker);
			}
		}
	}

	public Set<Entry<HexCoordinates, Set<T>>> entrySet() {
		return Collections.unmodifiableSet(combinedMarkers.entrySet());
	}

	public Set<T> get(HexCoordinates coordinates) {
		return Collections.unmodifiableSet(combinedMarkers.getOrDefault(coordinates, Collections.emptySet()));
	}

}
