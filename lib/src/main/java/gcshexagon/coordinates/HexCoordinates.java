package gcshexagon.coordinates;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class HexCoordinates {
	private static final int HASH_MAX_VALUE = 1_048_576;
	private static final int MAX_VALUE = 1024;

	public static HexCoordinates of(int col, int row) {
		return new HexCoordinates(col, row);
	}

	public static HexCoordinates of(long col, long row) {
		return of((int) col, (int) row);
	}

	private final int column, row;

	@Override
	public String toString() {
		return "(" + column + "  ," + row + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HexCoordinates other = (HexCoordinates) obj;
		return column == other.column && row == other.row;
	}

	@Override
	public int hashCode() {
		return (column + MAX_VALUE) * HASH_MAX_VALUE + (row + MAX_VALUE);
	}

	// TODO Review the condition check!
	public HexCoordinates(int col, int row) {
		super();

		if (Math.abs(col) > MAX_VALUE || Math.abs(row) > MAX_VALUE) {
			throw new IllegalArgumentException(col + "," + row);
		}

		this.column = col;
		this.row = row;
	}

}
