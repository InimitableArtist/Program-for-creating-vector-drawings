package hr.fer.ooup.lab4;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.Stack;

public class LineSegment extends AbstractGraphicalObject {

	public LineSegment() {
		super(new Point[]{new Point(0, 0), new Point(10, 0)});
	}
	
	public LineSegment(Point hotPoint1, Point hotPoint2) {
		super(new Point[]{hotPoint1, hotPoint2});
	}
	@Override
	public boolean isHotPointSelected(int index, boolean selected) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Rectangle getBoundingBox() {
		Point point1 = getHotPoint(0);
		Point point2 = getHotPoint(1);
		int x = (int) (point1.getX() < point2.getX() ? point1.getX() : point2.getX());
		int y = (int) (point1.getY() < point2.getY() ? point1.getY() : point2.getY());
		return new Rectangle(new Point(x, y), new Dimension(Math.abs(point1.x - point2.x), Math.abs(point1.y - point2.y)));
		
	}
	@Override
	public double selectionDistance(Point mousePoint) {
		return GeometryUtil.distanceFromLineSegment(getHotPoint(0), getHotPoint(1), mousePoint);
	}
	@Override
	public void render(Renderer r) {
		r.drawLine(getHotPoint(0), getHotPoint(1));	
	}
	@Override
	public String getShapeName() {
		return "Linija";	
	}
	@Override
	public GraphicalObject duplicate() {
		return new LineSegment(new Point(getHotPoint(0)), new Point(getHotPoint(1)));
	}
	@Override
	public String getShapeID() {
		return "@LINE";
	}
	@Override
	public void load(Stack<GraphicalObject> stack, String data) {	
	}
	@Override
	public void save(List<String> rows) {	
	}
}
