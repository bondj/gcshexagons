package gcshexagons;

public class ScaledHexagon {

	private final double[] xPoints = new double[6];
	private final double[] yPoints = new double[6];

	private final double dx, dy;

	private final double scale;

	public ScaledHexagon(UnitHexagon hexagon, double scale, double latitudeTransform, double longTransform) {
		double[] origXPoints = hexagon.xPoints();
		double[] origYPoints = hexagon.yPoints();

		for (int i = 0; i < origXPoints.length; i++) {
			xPoints[i] = origXPoints[i] * scale + longTransform;
			yPoints[i] = origYPoints[i] * scale + latitudeTransform;
		}

		this.dx = longTransform;
		this.dy = latitudeTransform;
		this.scale = scale;

	}

	public double[] xPoints() {
		return xPoints;
	}

	public double[] yPoints() {
		return yPoints;
	}

	public DoublePoint transform() {
		return DoublePoint.of(dx, dy);
	}

	double inside(double cy, double cx) {
		var area = calculateArea();
		double sum = 0;

		for (int i = 0; i < xPoints.length; i++) {
			double ax = xPoints[i], ay = yPoints[i];
			double bx = xPoints[(i + 1) % 6], by = yPoints[(i + 1) % 6];

			double a = Math.sqrt(Math.pow(bx - ax, 2) + Math.pow(by - ay, 2));
			double b = Math.sqrt(Math.pow(bx - cx, 2) + Math.pow(by - cy, 2));
			double c = Math.sqrt(Math.pow(ax - cx, 2) + Math.pow(ay - cy, 2));
			double s = (a + b + c) / 2;

			double newArea = Math.sqrt(s * (s - a) * (s - b) * (s - c));
			sum = sum + newArea;
		}

		var diff = Math.abs(sum - area);

		return diff;

	}

	public double calculateArea() {
		return Math.pow(scale, 2) * 3 * Math.sqrt(3) / 2;
	}

//	public static void draw(final double latitude, final double longitude, double innerRadius, double outerRadius) {
//
//		double minX = longitude, maxX = longitude;
//		double minY = latitude, maxY = latitude;
//
//		for (int i = 1; i < xPoints.length; i++) {
//			minX = Math.min(minX, xPoints[i]);
//			minY = Math.min(minY, yPoints[i]);
//			maxX = Math.max(maxX, xPoints[i]);
//			maxY = Math.max(maxY, yPoints[i]);
//		}
//
////		minX = minX - 2;
////		minY = minY - 2;
////		maxX = maxX + 2;
////		maxY = maxY + 2;
//
//		var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		final Frame f = new Frame();
//		f.setSize(screenSize.width, screenSize.height);
//		f.setVisible(true);
//		var g = (Graphics2D) f.getGraphics();
//
//		Rectangle bounds = f.getBounds();
//		// System.out.println(bounds);
//		double top = screenSize.height - bounds.getY();
//		double scale = top / (maxY - minY);
//		int[] scaledX = new int[xPoints.length], scaledY = new int[yPoints.length];
//		for (int i = 0; i < xPoints.length; i++) {
//			double x = xPoints[i], y = yPoints[i];
//			scaledX[i] = (int) scale(x, minX, scale);
//			scaledY[i] = (int) scale(y, minY, scale);
//
//			g.drawString(DecimalFormat.format(x) + "," + DecimalFormat.format(y), scaledX[i], scaledY[i]);
//
//		}
//
//		g.drawPolygon(scaledX, scaledY, scaledX.length);
//
//		double outerRadiusScaled = outerRadius * scale, innerRadiusScaled = innerRadius * scale;
//
//		var centerX = scale(dx, minX, scale);
//		var centerY = scale(dy, minY, scale);
//
//		int innerCircumferfence = (int) innerRadiusScaled * 2;
//		g.drawArc((int) (centerX - innerRadiusScaled), (int) (centerY - innerRadiusScaled), innerCircumferfence,
//				innerCircumferfence, 0, 360);
//		int outerCircumference = (int) outerRadiusScaled * 2;
//		g.drawArc((int) (centerX - outerRadiusScaled), (int) (centerY - outerRadiusScaled), outerCircumference,
//				outerCircumference, 0, 360);
//
//		var cy = scale(latitude, minY, scale);
//		var cx = scale(longitude, minX, scale);
//
//		g.drawArc((int) cx - 10, (int) cy - 10, 20, 20, 0, 360);
//		g.drawString(DecimalFormat.format(longitude) + "," + DecimalFormat.format(latitude), (int) cx, (int) cy);
//
//		System.out.println();
//
//	}
//
//	private double scale(final double latitude, double minY, double scale) {
//		return (latitude - minY) * scale;
//	}

}
