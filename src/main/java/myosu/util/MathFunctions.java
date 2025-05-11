package myosu.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class MathFunctions {
	public static double getAngleFromTo(double fromX, double fromY, double toX, double toY) {
		double deltaX = toX - fromX;
		double deltaY = toY - fromY;
		double angle;
		
		if (deltaX == 0 && deltaY == 0) {
			angle = 0;
		} else if (deltaX <= 0 && deltaY <= 0 || deltaX >= 0 && deltaY >= 0) {
			angle = Math.toDegrees(Math.atan(Math.abs(deltaY) / (double) Math.abs(deltaX)));
			if (deltaX >= 0 && deltaY >= 0) {
				angle += 90;
			} else if (deltaX <= 0 && deltaY <= 0) {
				angle += 270;
			}
		} else {
			angle = Math.toDegrees(Math.atan(Math.abs(deltaX) / (double) Math.abs(deltaY)));
			if (deltaX <= 0 && deltaY >= 0) {
				angle += 180;
			}
		}
		
		if (angle == 360) {
			angle = 0;
		}
		
		return angle;
	}
	
	public static Point2D getDeltaXAndYFromAngle(double angle, double length) {
		double localAngle = angle % 90;
		double opposite = length * Math.sin(Math.toRadians(localAngle));
		double next = length * Math.cos(Math.toRadians(localAngle));
		
		if (angle < 90) {
			return new Point2D.Double(opposite, -next);
		} else if (angle >= 90 && angle < 180) {
			return new Point2D.Double(next, opposite);
		} else if (angle >= 180 && angle < 270) {
			return new Point2D.Double(-opposite, next);
		} else if (angle >= 270) {
			return new Point2D.Double(-next, -opposite);
		}
		
		return null;
	}
	
	/*public static Point2D getMidPoint(ArrayList<Unit> list) {
		double minX = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		
		for (Unit unit : list) {
			if (unit.getX() < minX) {
				minX = unit.getX();
			}
			if (unit.getX() > maxX) {
				maxX = unit.getX();
			}
			if (unit.getY() < minY) {
				minY = unit.getY();
			}
			if (unit.getY() > maxY) {
				maxY = unit.getY();
			}
		}
		
		return new Point2D.Double((minX + maxX) / 2, (minY + maxY) / 2);
	}*/
	
	public static double correctAngle(double angle) {
		if (angle < 0) {
			angle += 360;
		}
		
		if (angle >= 360) {
			angle -= 360;
		}
		return angle;
	}
	
	public static double pythagoras(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public static double distance(double startX, double startY, double toX, double toY) {
		return pythagoras(Math.abs(startX - toX), Math.abs(startY - toY));
	}
	
	public static double slopeOfAnAngle(double angle) throws Exception {
		if (angle == 0 || angle == 180 || angle == 360) {
			throw new IllegalArgumentException("Slope is infinite");
		}
		
		if (angle == 90 || angle == 270) {
			return 0;
		}
		
		double modi = angle % 90;
		if (angle > 90 && angle < 180 || angle > 270 && angle < 360) {
			return -Math.tan(Math.toRadians(modi));
		} else {
			return Math.tan(Math.toRadians(90 - modi));
		}
	}
	
	public static double distanceLinePoint(double pointX, double pointY, double slope, double constant) {
		return (Math.abs(slope * pointX - pointY + constant)) / (Math.sqrt(Math.pow(slope, 2) + 1));
	}
	
	public static double getConstant(double linePointX, double linePointY, double slope) {
		return (-slope) * linePointX + linePointY;
	}
	
	/**
	 * Calculates distance from half-line to point.
	 * @param startLinePointX Start of half-line X
	 * @param startLinePointY Start of half-line Y
	 * @param lineAngle From straight up clockwise to line.
	 * @param pointX Point X
	 * @param pointY Point Y
	 * @param flippedCoordinates If true -> game coordinate system, false -> normal coordinate system.
	 * @return distance
	 */
	public static double distanceHalfLineToPoint(double startLinePointX, double startLinePointY, double lineAngle, double pointX, double pointY, boolean flippedCoordinates) {
		if (flippedCoordinates) {
			startLinePointY *= -1;
			pointY *= -1;
		}
		
		double startToPointAngle = getAngleFromTo(startLinePointX, -startLinePointY, pointX, -pointY);
		
		if (isBetweenAngles(startToPointAngle, correctAngle(lineAngle - 90), correctAngle(lineAngle + 90))) {
			try {
				double slope = MathFunctions.slopeOfAnAngle(lineAngle);
				
				return MathFunctions.distanceLinePoint(pointX, pointY, slope, MathFunctions.getConstant(startLinePointX, startLinePointY, slope));
			} catch (Exception e) {
				//Angle is 0 || 180
				return Math.abs(startLinePointX - pointX);
			}
		} else {
			return distance(startLinePointX, startLinePointY, pointX, pointY);
		}
	}
	
	public static boolean isBetweenAngles(double test, double low, double high) {
		if (high < low) {
			return test > low || test < high;
		} else {
			return test > low && test < high;
		}
	}
	
	/**
	 * Returns a point on a line segment that connects 2 points.
	 * Percent tells how far through the line the point is.
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @param percent
	 * @return 
	 */
	public static Point2D pointBetweenPointsPercent(double startX, double startY, double endX, double endY, int percent) {
		double multiplier = percent / 100.0;
		double deltaX = Math.abs(endX - startX);
		double deltaY = Math.abs(endY - startY);
		
		return new Point2D.Double(startX + multiplier * deltaX, startY + multiplier * deltaY);
	}
	
	/**
	 * Returns a Boolean based on a given chance of something happening.
	 * @param chance How likely something is to happen. From 0 to 1.
	 * @return Boolean, whether it happened or not.
	 */
	public static boolean randomFromChance(double chance) {
		return Math.random() <= chance;
	}
	
	public static int permutations(int n, int k, boolean repetition) {
		if (repetition) {
			return (int) Math.pow(n, k);
		} else {
			return factorial(n) / (factorial(n - k));
		}
	}
	
	public static int combinations(int n, int k, boolean repetition) {
		if (repetition) {
			return combinations(n + k - 1, k, false);
		} else {
			return factorial(n) / (factorial(k) * factorial(n - k));
		}
	}
	
	public static int factorial(int n) {
		if (n < 0) {
			return 0;
		} else if (n == 0) {
			return 1;
		}
		int total = 1;
		for (int i = n; i > 0; i--) {
			total *= i;
		}
		return total;
	}
	
	/**
	 * Moves the value between the low and high if outside.
	 * @param value
	 * @param low
	 * @param high
	 * @return 
	 */
	public static int maxMin(int value, int low, int high) {
		return Math.min(high, Math.max(value, low));
	}
	
	public static boolean isBetween(int value, int low, int high) {
		return low <= value && value <= high;
	}
}
