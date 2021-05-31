package hr.fer.ooup.lab4;

import java.awt.Point;
import java.awt.geom.Line2D;

public class GeometryUtil {
	
	public static double distanceFromPoint(Point point1, Point point2) {
		return Math.hypot(Math.abs(point2.getX() - point1.getX()), Math.abs(point2.getY() - point1.getY()));
	}
	
	public static double distanceFromLineSegment(Point start, Point end, Point point) {
		return Line2D.ptSegDist(start.getX(), start.getY(), end.getX(), end.getY(), point.getX(), point.getY());
	}
	
}
