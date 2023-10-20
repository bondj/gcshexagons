package gcshexagons;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class DoublePoint {

	private final double x, y;

	private DoublePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static DoublePoint of(double x, double y) {
		return new DoublePoint(x, y);
	}
	
	public IntegerPoint round() {
		return IntegerPoint.of(Math.round(x), Math.round(y));
	}
	
	public DoublePoint ceil() {
		return DoublePoint.of(Math.ceil(x), Math.ceil(y));
	}
	
	public DoublePoint floor() {
		return DoublePoint.of(Math.floor(x), Math.floor(y));
	}

}
