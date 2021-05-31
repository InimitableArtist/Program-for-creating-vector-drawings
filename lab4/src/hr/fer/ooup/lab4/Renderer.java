package hr.fer.ooup.lab4;

import java.awt.Point;

public interface Renderer {

	void drawLine(Point s, Point e);
	void fillPolygon(Point[] points);
	
}
