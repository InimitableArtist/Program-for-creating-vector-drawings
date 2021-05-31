package hr.fer.ooup.lab4;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SelectShapeState extends IdleState {

	private final static int HOT_POINT_BOUNDING_BOX_WIDTH = 3;
	
	private DocumentModel model;
	
	private GraphicalObject selectedGo;
	private int indexOfSelectedHotPoint;
	
	public SelectShapeState(DocumentModel model) {
		this.model = model;
	}
	
	@Override
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		GraphicalObject go = model.findSelectedGraphicalObject(mousePoint);
		if (go == null) {
			selectedGo = null;
			model.deselectAll();
			return;
		}
		
		if (!ctrlDown) {
			model.deselectAll();
			go.setSelected(true);
			selectedGo = go;
		} else {
			selectedGo = null;
			if (!go.isSelected()) {
				go.setSelected(true);
			} else {
				go.setSelected(false);
			}
		}
	}
	
	@Override
	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_RIGHT) {
			for (GraphicalObject go : model.getSelectedObjects()) {
				go.translate(new Point(1, 0));
			}
		}
		else if (keyCode == KeyEvent.VK_LEFT) {
			for (GraphicalObject go : model.getSelectedObjects()) {
				go.translate(new Point(-1, 0));
			}
		}
		else if (keyCode == KeyEvent.VK_UP) {
			for (GraphicalObject go : model.getSelectedObjects()) {
				go.translate(new Point(0, -1));
			}
		}
		else if (keyCode == KeyEvent.VK_DOWN) {
			for (GraphicalObject go : model.getSelectedObjects()) {
				go.translate(new Point(0, 1));
			}
		}
		else if (keyCode == KeyEvent.VK_ADD || keyCode == KeyEvent.VK_PLUS) {
			List<GraphicalObject> selectedObjectsPlus = model.getSelectedObjects();
			if (!selectedObjectsPlus.isEmpty() && selectedObjectsPlus.size() == 1) {
				model.increaseZ(selectedObjectsPlus.get(0));
			}
		}
		else if (keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_MINUS) {
			List<GraphicalObject> selectedObjectsMinus= model.getSelectedObjects();
			if (!selectedObjectsMinus.isEmpty() && selectedObjectsMinus.size() == 1) {
				model.decreaseZ(selectedObjectsMinus.get(0));
			}
		}
		else if (keyCode == KeyEvent.VK_G) {
			List<GraphicalObject> selectedObjects = model.getSelectedObjects();
			if (!selectedObjects.isEmpty() && selectedObjects.size() > 1) {
				GraphicalObject[] compositeObjects = new GraphicalObject[selectedObjects.size()];
				for (int i = 0; i < compositeObjects.length; i++) {
					compositeObjects[i] = selectedObjects.get(0);
					model.removeGraphicalObject(selectedObjects.get(0));
				}
				GraphicalObject go = new CompositeShape(compositeObjects, false);
				model.addGraphicalObject(go);
				go.setSelected(true);
			}
		}
		else if (keyCode == KeyEvent.VK_U) {
			List<GraphicalObject> ungroup = model.getSelectedObjects();
			if (!ungroup.isEmpty() && ungroup.size() == 1 && ungroup.get(0).getShapeName().equals("Composite")) {
				CompositeShape composite = (CompositeShape)ungroup.get(0);
				GraphicalObject[] objects = composite.getObject();
				model.removeGraphicalObject(composite);
				for (GraphicalObject go : objects) {
					go.setSelected(true);
					model.addGraphicalObject(go);
				}
			}
		}
	}
	
	@Override
	public void mouseDragged(Point mousePoint) {
		if (selectedGo != null && indexOfSelectedHotPoint >= 0) {
			if (selectedGo.isHotPointSelected(indexOfSelectedHotPoint)) {
				selectedGo.setHotPoint(indexOfSelectedHotPoint, mousePoint);
			}
		} else {
			if (selectedGo != null) {
				int index = model.findSelectedHotPoint(selectedGo, mousePoint);
				if (index != -1) {
					selectedGo.setHotPointSelected(index, true);
					indexOfSelectedHotPoint = index;
					selectedGo.setHotPoint(index, mousePoint);
				}
			}
		}
	}
	
	@Override
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown) {
		if(selectedGo != null && indexOfSelectedHotPoint >= 0 && indexOfSelectedHotPoint < selectedGo.getNumberOfHotPoints()) {
			selectedGo.setHotPointSelected(indexOfSelectedHotPoint, false);
			indexOfSelectedHotPoint = -1;
		}
	}
	
	@Override
	public void afterDraw(Renderer r, GraphicalObject go) {
		if (!go.isSelected()) {return;}
		Rectangle rectangle = go.getBoundingBox();
		r.drawLine(new Point(rectangle.x, rectangle.y), new Point((int)rectangle.getMaxX(), rectangle.y));
		r.drawLine(new Point(rectangle.x, rectangle.y), new Point(rectangle.x, (int)rectangle.getMaxY()));
		r.drawLine(new Point(rectangle.x, (int)rectangle.getMaxY()), new Point((int)rectangle.getMaxX(), (int)rectangle.getMaxY()));
		r.drawLine(new Point((int)rectangle.getMaxX(), rectangle.y), new Point((int)rectangle.getMaxX(), (int)rectangle.getMaxY()));
		
		if (selectedGo != null && selectedGo.equals(go)) {
			List<Point> hotPoints = new ArrayList<Point>();
			for (int i = 0; i < go.getNumberOfHotPoints(); i++) {
				hotPoints.add(go.getHotPoint(i));
			}
			for (Point point : hotPoints) {
				r.drawLine(new Point(point.x - HOT_POINT_BOUNDING_BOX_WIDTH, point.y - HOT_POINT_BOUNDING_BOX_WIDTH), 
						new Point(point.x + HOT_POINT_BOUNDING_BOX_WIDTH, point.y - HOT_POINT_BOUNDING_BOX_WIDTH));
				r.drawLine(new Point(point.x - HOT_POINT_BOUNDING_BOX_WIDTH, point.y - HOT_POINT_BOUNDING_BOX_WIDTH),
						new Point(point.x - HOT_POINT_BOUNDING_BOX_WIDTH, point.y + HOT_POINT_BOUNDING_BOX_WIDTH));
				r.drawLine(new Point(point.x - HOT_POINT_BOUNDING_BOX_WIDTH, point.y + HOT_POINT_BOUNDING_BOX_WIDTH),
						new Point(point.x + HOT_POINT_BOUNDING_BOX_WIDTH, point.y + HOT_POINT_BOUNDING_BOX_WIDTH));
				r.drawLine(new Point(point.x + HOT_POINT_BOUNDING_BOX_WIDTH, point.y - HOT_POINT_BOUNDING_BOX_WIDTH),
						new Point(point.x + HOT_POINT_BOUNDING_BOX_WIDTH, point.y + HOT_POINT_BOUNDING_BOX_WIDTH));
			}
		}
	}
	
	
}
