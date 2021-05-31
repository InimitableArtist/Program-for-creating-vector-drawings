package hr.fer.ooup.lab4;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SVGRendererImpl implements Renderer {
	
	private List<String> lines;
	private String fileName;
	
	public SVGRendererImpl(String fileName) {
		this.fileName = fileName;
		lines = new ArrayList<String>();
		lines.add("<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
	}

	@Override
	public void drawLine(Point s, Point e) {
		lines.add(String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"stroke:#0000ff;\"/>", s.x, s.y, e.x, e.y));
		
	}

	@Override
	public void fillPolygon(Point[] points) {
		StringBuilder sb = new StringBuilder();
		sb.append("<polygon points=\"");
		for (Point p : points) {
			sb.append(p.x).append(',').append(p.y).append(' ');
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\" style=\"stroke:#ff0000; fill:#0000ff;\"/>");
		lines.add(sb.toString());
	}
	
	public void close() throws IOException {
		lines.add("</svg>");
		FileWriter fw = new FileWriter(new File(this.fileName));
		for (String line : lines) {
			fw.write(line);
		}
		fw.flush();
		fw.close();
	}
	

}
