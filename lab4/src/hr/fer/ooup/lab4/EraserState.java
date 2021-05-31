package hr.fer.ooup.lab4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EraserState extends IdleState {
	
	private DocumentModel model;
	private List<Point> points;
	
	public EraserState(DocumentModel model) {
		this.model = model;
		points = new ArrayList<Point>();
	}
	
	@Override
	public void mouseDragged(Point mousePoint) {
		points.add(mousePoint);
		model.notifyListeners();
	}
	
	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		points.add(mousePoint);
		Set<GraphicalObject> deleteObjects = new HashSet<GraphicalObject>();
		for (Point p : points) {
			deleteObjects.addAll(model.findSelectedGraphicalObjects(p));
		}
		for (GraphicalObject go : deleteObjects) {
			model.removeGraphicalObject(go);
		}
		points.clear();
		model.notifyListeners();
	}
	
	@Override
	public void afterDraw(Renderer r) {
		for (int i = 0; i < points.size() - 1; i++) {
			r.drawLine(points.get(i), points.get(i + 1));
		}
	}

}
