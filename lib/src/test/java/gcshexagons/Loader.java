package gcshexagons;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import gcshexagon.coordinates.HexCoordinates;
import lombok.SneakyThrows;

public class Loader {

	@SneakyThrows
	public Loader() {
		load();
	}
	
	List<String> resources = List.of("HMdb-Entries-in-California-20230818.csv");


	public void load() throws IOException {
		final CsvMapper mapper = new CsvMapper().enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);
		CsvSchema headerSchema = CsvSchema.emptySchema().withHeader();

		for(var i: resources) {
		MappingIterator<Marker> it = mapper.readerFor(Marker.class).with(headerSchema)
				.readValues(Loader.class.getClassLoader().getResource(i));

		loadIterator(it);
		}
		
//		var markers = hexagonMarkerMap.get(new HexGridCoordinates(-12, 4));
		//System.out.println(markers);
	}

	private void loadIterator(MappingIterator<Marker> it) throws IOException {
		
		var read = new ArrayList<Marker>();
		
		var list = it.readAll();
		long start = System.nanoTime();
		ListIterator<Marker> listIterator = list.listIterator();
		
		while (listIterator.hasNext()) {
			var i = listIterator.next();
			if (i.latitude == null || i.longitude == null) {
				listIterator.remove();
				continue;
			}
			read.add(i);
		}
		
		HexGridToGcsGridMappings mapping = new HexGridToGcsGridMappings(60);
		
		var map = new WorldMap<>(read,a->mapping.gcsToGrid(a.getLatitude(), a.getLongitude()));
		
		System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()-start)+"ms");
	}



	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Loader loader = new Loader();



	}

}
