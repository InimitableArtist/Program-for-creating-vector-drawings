package hr.fer.ooup.lab4;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractGraphicalObject implements GraphicalObject {

	private Point[] hotPoints;
	private boolean[] hotPointSelected;
	private boolean selected;
	private List<GraphicalObjectListener> listeners;
	
	public AbstractGraphicalObject(Point[] hotPoints) {
		this.hotPoints = hotPoints;
		hotPointSelected = new boolean[hotPoints.length];
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

	@Override
	public int getNumberOfHotPoints() {
		return hotPoints.length;
	}

	@Override
	public Point getHotPoint(int index) {
		if(index < hotPoints.length) {
			return hotPoints[index];
		}
		return null;
	}

	@Override
	public void setHotPoint(int index, Point point) {
		if(index < hotPoints.length) {
			hotPoints[index] = point;
		}
		notifyListeners();
	}

	@Override
	public boolean isHotPointSelected(int index) {
		if(index < hotPointSelected.length) {
			return hotPointSelected[index];
		}
		return false;
	}

	@Override
	public void setHotPointSelected(int index, boolean selected) {
		if(index < hotPointSelected.length) {
			hotPointSelected[index] = selected;
		}
	}

	@Override
	public double getHotPointDistance(int index, Point mousePoint) throws IndexOutOfBoundsException {
		if(index >= hotPoints.length) throw new IndexOutOfBoundsException(index + " is greater than " + hotPoints.length);
		return GeometryUtil.distanceFromPoint(hotPoints[index], mousePoint);
	}

	@Override
	public void translate(Point d) {
		for (Point point : hotPoints) {
			point.x += d.x;
			point.y += d.y;
		}
		notifyListeners();
	}

	@Override
	public void addGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.add(l);
	}

	@Override
	public void removeGraphicalObjectListener(GraphicalObjectListener l) {
		listeners.remove(l);
	}
	
	public void notifyListeners() {
		for (GraphicalObjectListener listener : listeners) {
			listener.graphicalObjectChanged(this);
		}
	}
	
	public void notifySelectionListeners() {
		for (GraphicalObjectListener listener : listeners) {
			listener.graphicalObjectSelectionChanged(this);
		}
	}
}