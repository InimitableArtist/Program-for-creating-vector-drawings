package hr.fer.ooup.lab4;

import java.awt.Point;

public interface State {
	
	//Poziva se kad program registrira da je pritisnuta lijeva tipka miša
	public void mouseDown(Point mousePoint, boolean shiftDown, boolean ctrlDown);
	//Poziva se kad program registrira da je otpuštena lijeva tipka miša
	public void mouseUp(Point mousePoint, boolean shiftDown, boolean ctrlDown);
	public void mouseDragged(Point mousePoint);
	public void keyPressed(int keyCode);
	//Poziva se nakon što je platno nacrtalo grafièki objekt predan kao argument
	public void afterDraw(Renderer r, GraphicalObject  go);
	//Poziva se nakon pto je platno nacrtalo èitav crtež
	public void afterDraw(Renderer r);
	//Poziva se kad program napušta ovo stanje kako bi prešlo u neko drugo
	public void onLeaving();

}
