package gcshexagons;

public class UnitHexagon {

	private final static double ARC = Math.PI * 2 / 6;
	private final static double TWEAK = Math.PI / 6;

	private final double[] xPoints = new double[6];
	private final double[] yPoints = new double[6];

	public UnitHexagon() {
		for (double i = 0; i < 6; i++) {
			xPoints[(int) i] = StrictMath.cos(i * ARC + TWEAK);
			yPoints[(int) i] = StrictMath.sin(i * ARC + TWEAK);
		}
	}

	public double[] xPoints() {
		return xPoints;
	}

	public double[] yPoints() {
		return yPoints;
	}

	public ScaledHexagon scaleAndTransform(double scale, double dx, double dy) {
		return new ScaledHexagon(this, scale, dx, dy);
	}

}
