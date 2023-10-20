package gcshexagons;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class IntegerPoint {

	private final int x, y;

	private IntegerPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static IntegerPoint of(int x, int y) {
		return new IntegerPoint(x, y);
	}
	
	public static IntegerPoint of(long x, long y) {
		return new IntegerPoint((int)x, (int)y);
	}

}
