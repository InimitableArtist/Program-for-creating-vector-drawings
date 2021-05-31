package hr.fer.ooup.lab4;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CompositeShape implements GraphicalObject {
	
	private GraphicalObject[] objects;
	private boolean selected;
	private List<GraphicalObjectListener> listeners;
	
	public CompositeShape() {
		this.selected = selected;
		listeners = new ArrayList<GraphicalObjectListener>();
	}
	
	public CompositeShape(GraphicalObject[] compositeObjects, boolean b) {
		this.objects = compositeObjects;
		this.selected = b;
		listeners = new ArrayList<GraphicalObjectListener>();
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		notifySelectionListeners();
		
	}

	public void notifySelectionListeners() {
		
		for (GraphicalObjectListener listener : listeners) {
			listener.graphicalObjectSelectionChanged(this);
		}
	}
	@Override
	public int getNumberOfHotPoints() {
		return 0;
	}

	@Override
	public Point getHotPoint(int index) {
		return null;
	}
	@Override
	public void setHotPoint(int index, Point point) {}
	@Override
	public boolean isHotPointSelected(int index, boolean selected) {
		return false;
	}
	@Override
	public void setHotPointSelected(int index, boolean selected) {}

	@Override
	public double getHotPointDistance(int index, Point mousePoint) {
		return Double.MAX_VALUE;
	}
	@Override
	public void translate(Point delta) {
		for (GraphicalObject obj : objects) {
			obj.translate(delta);
		}
		notifyListeners();	
	}
	private void notifyListeners() {
		for (GraphicalObjectListener listener : listeners) {
			listener.graphicalObjectChanged(this);
		}
		
	}
	@Override
	public Rectangle getBoundingBox() {
		Rectangle rect = objects[0].getBoundingBox();
		int minX = (int)rect.getMinX();
		int maxX = (int)rect.getMaxX();
		int minY = (int)rect.getMinY();
		int maxY = (int)rect.getMaxY();
		
		for (int i = 1; i < objects.length; i++) {
			rect = objects[i].getBoundingBox();
			minX = rect.getMinX() < minX ? (int)rect.getMinX() : minX;
			maxX = rect.getMaxX() > maxX ? (int)rect.getMaxX() : maxX;
			minY = rect.getMinY() < minY ? (int)rect.getMinY() : minY;
			maxY = rect.getMaxY() > maxY ? (int)rect.getMaxY() : maxY;
		}
		
		return new Rectangle(new Point(minX, minY), new Dimension(maxX - minX, maxY - minY));
	}

	@Override
	public double selectionDistance(Point mousePoint) {
		if (objects.length == 0) {
			return Double.MAX_VALUE;
		}
		double[] distances = new double[objects.length];
		for (int i = 0; i < objects.length; i++) {
			distances[i] = objects[i].selectionDistance(mousePoint);
		}
		double min = distances[0];
		for (int i = 1; i < distances.length; i++) {
			if (distances[i] < min) {
				min = distances[i];
			}
		}
		return min;
	}
	@Override
	public void render(Renderer r) {
		for (GraphicalObject object : objects) {
			object.render(r);
		}
	}
	@Override
	public void addGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.add(l);
	}
	@Override
	public void removeGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.remove(l);	
	}
	@Override
	public String getShapeName() {
		return "Composite";
	}
	@Override
	public GraphicalObject duplicate() {
		return null;
	}
	@Override
	public String getShapeID() {
		return "@COMP";
	}
	@Override
	public void load(Stack<GraphicalObject> stack, String data) {	
	}
	@Override
	public void save(List<String> rows) {	
	}
	@Override
	public boolean isHotPointSelected(int index) {
		return false;
	}
	public GraphicalObject[] getObject() {
		return objects;
	}
}
