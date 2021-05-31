package hr.fer.ooup.lab4;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Stack;

public class Oval extends AbstractGraphicalObject {
	
	private final static int NUMBER_OF_POINTS = 50;

	public Oval() {
		super(new Point[] {new Point(0, 10), new Point(10, 0)});
	}
	
	public Oval(Point lowerHotPoint, Point rightHotPoint) {
		super(new Point[] {lowerHotPoint, rightHotPoint});
	}

	@Override
	public boolean isHotPointSelected(int index, boolean selected) {
		return false;
	}

	@Override
	public Rectangle getBoundingBox() {
		Point lowerHotPoint = getHotPoint(0);
		Point rightHotPoint = getHotPoint(1);
		
		int x = lowerHotPoint.x - (rightHotPoint.x - lowerHotPoint.x);
		int y = rightHotPoint.y - (lowerHotPoint.y - rightHotPoint.y);
		
		int width = 2 * (rightHotPoint.x - lowerHotPoint.x);
		int height = 2 * (lowerHotPoint.y - rightHotPoint.y);
		
		return new Rectangle(new Point(x, y), new Dimension(width, height));
 	}

	@Override
	public double selectionDistance(Point mousePoint) {
		Point lowerHotPoint = getHotPoint(0);
		Point rightHotPoint = getHotPoint(1);
		
		int a = rightHotPoint.x - lowerHotPoint.x;
		int b = lowerHotPoint.y - rightHotPoint.y;
		int p = lowerHotPoint.x;
		int q = rightHotPoint.y;
		double elipse = Math.pow(mousePoint.x - p, 2)/Math.pow(a, 2) + Math.pow(mousePoint.y - q, 2)/Math.pow(b, 2);
		if (elipse <= 1) {
			return 0;
		}
		Point[] points = getPoints(50);
		double min = GeometryUtil.distanceFromPoint(points[0], mousePoint);
		for (int i = 1; i < points.length; i++) {
			double distance = GeometryUtil.distanceFromPoint(points[i], mousePoint);
			if (distance < min) {
				min = distance;
			}
		}
		return min;
	}

	@Override
	public void render(Renderer r) {
		r.fillPolygon(getPoints(NUMBER_OF_POINTS));
	}
	@Override
	public String getShapeName() {
		return "Oval";
	}
	@Override
	public GraphicalObject duplicate() {
		return new Oval(new Point(getHotPoint(0)), new Point(getHotPoint(1)));
	}
	@Override
	public String getShapeID() {
		return "@OVAL";
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {	
	}

	@Override
	public void save(List<String> rows) {
	}
	private Point[] getPoints(int numOfPoints) {
		Point lowerHotPoint = getHotPoint(0);
		Point rightHotPoint = getHotPoint(1);
		Point center = new Point(lowerHotPoint.x, rightHotPoint.y);
		int a = rightHotPoint.x - lowerHotPoint.x;
		int b = lowerHotPoint.y - rightHotPoint.y;
		
		Point[] points = new Point[numOfPoints];
		for (int i = 0; i < numOfPoints; i++) {
			double t = (2*Math.PI/numOfPoints) * i;
			int x = (int)(a * Math.cos(t)) + center.x;
			int y = (int)(b * Math.sin(t)) + center.y;
			points[i] = new Point(x, y);
		}
		return points;
	}

}
