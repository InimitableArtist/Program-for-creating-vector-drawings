package hr.fer.ooup.lab4;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class G2DRendererImpl implements Renderer {
	
	private Graphics2D g2d;
	
	public G2DRendererImpl(Graphics2D g) {
		System.out.println("sad sam tu...");
		this.g2d = g;
	}

	@Override
	public void drawLine(Point s, Point e) {
		System.out.println("tu sam");
		g2d.setColor(Color.BLUE);
		g2d.drawLine(s.x, s.y, e.x, e.y);
		
	}

	@Override
	public void fillPolygon(Point[] points) {
		int[] xpoints = new int[points.length];
		int[] ypoints = new int[points.length];
		
		for (int i = 0; i < points.length; i++) {
			xpoints[i] = points[i].x;
			ypoints[i] = points[i].y;
		}
		g2d.setColor(Color.BLUE);
		g2d.fillPolygon(xpoints, ypoints, points.length);
		g2d.setColor(Color.RED);
		g2d.drawPolygon(xpoints, ypoints, points.length);
		
	}

}
