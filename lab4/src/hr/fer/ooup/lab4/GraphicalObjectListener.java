package hr.fer.ooup.lab4;

public interface GraphicalObjectListener {

	//Poziva se kad se nad objektom promjeni bilo što...
	public void graphicalObjectChanged(GraphicalObject go);
	public void graphicalObjectSelectionChanged(GraphicalObject go);
	
}
