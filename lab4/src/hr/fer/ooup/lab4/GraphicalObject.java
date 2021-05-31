package hr.fer.ooup.lab4;

import java.awt.Point;
import java.util.List;
import java.util.Stack;
import java.awt.Rectangle;


public interface GraphicalObject {

	public boolean isSelected();
	public void setSelected(boolean selected);
	public int getNumberOfHotPoints();
	public Point getHotPoint(int index);
	public void setHotPoint(int index, Point point);
	public boolean isHotPointSelected(int index, boolean selected);
	public void setHotPointSelected(int index, boolean selected);
	public double getHotPointDistance(int index, Point mousePoint);
	
	//Geometrijske operacije
	public void translate(Point delta);
	public Rectangle getBoundingBox();
	double selectionDistance(Point mousePoint);
	
	//Podr�ka za crtanje
	public void render(Renderer r);
	
	//Observer za dojavu promjena modelu
	public void addGraphicalObjectListener(GraphicalObjectListener l);
	public void removeGraphicalObjectListener(GraphicalObjectListener l);
	
	//Podr�ka za prototip (alatna traka, stvaranje objekata u crte�u, ...)
	public String getShapeName();
	public GraphicalObject duplicate();
	
	//Podr�ka za snimanje i u�itavanje
	public String getShapeID();
	public void load(Stack<GraphicalObject> stack, String data);
	public void save(List<String> rows);
	boolean isHotPointSelected(int index);
	
	
}
