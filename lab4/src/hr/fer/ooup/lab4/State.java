package hr.fer.ooup.lab4;

import java.awt.Point;

public interface State {
	
	//Poziva se kad program registrira da je pritisnuta lijeva tipka mi�a
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown);
	//Poziva se kad program registrira da je otpu�tena lijeva tipka mi�a
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown);
	public void mouseDragged(Point mousePoint);
	public void keyPressed(int keyCode);
	//Poziva se nakon �to je platno nacrtalo grafi�ki objekt predan kao argument
	public void afterDraw(Renderer r, GraphicalObject  go);
	//Poziva se nakon pto je platno nacrtalo �itav crte�
	public void afterDraw(Renderer r);
	//Poziva se kad program napu�ta ovo stanje kako bi pre�lo u neko drugo
	public void onLeaving();

}
