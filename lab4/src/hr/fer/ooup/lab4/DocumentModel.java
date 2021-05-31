package hr.fer.ooup.lab4;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DocumentModel {
	
	public final static double SELECTION_PROXIMITY = 10;
	//Kolekcija svih grafièkih objekata:
	private List<GraphicalObject> objects = new ArrayList<>();
	//Read-Only proxy oko kolekcije grafièkih objekata:
	private List<GraphicalObject> roObjects = Collections.unmodifiableList(objects);
	//Kolekcija prijavljenih promatraèa:
	private List<DocumentModelListener> listeners = new ArrayList<>();
	//Kolekcija selektiranih objekata:
	private List<GraphicalObject> selectedObjects = new ArrayList<>();
	//Read-Only proxy oko kolekcije selektiranih objekata::
	private List<GraphicalObject> roSelectedObjects = Collections.unmodifiableList(selectedObjects);
	
	//Konstruktor...
	public DocumentModel() {
		objects = new ArrayList<GraphicalObject>();
		roObjects = Collections.unmodifiableList(objects);
		selectedObjects = new ArrayList<GraphicalObject>();
		roSelectedObjects = Collections.unmodifiableList(selectedObjects);
		listeners = new ArrayList<DocumentModelListener>();
	}
	
	//Brisanje svih objekata iz modela i potom
	//obavijeste svi promatraèi modela
	public void clear() {
		for (GraphicalObject go: objects) {
			go.removeGraphicalObjectListener(goListener);
		}
		objects.clear();
		selectedObjects.clear();
		notifyListeners();
	}
	
	//Dodavanje objekta u domkument
	public void addGraphicalObject(GraphicalObject go) {
		if(go.isSelected()) {
			selectedObjects.add(go);
		}
		objects.add(go);
		go.addGraphicalObjectListener(goListener);
		notifyListeners();
		
	}
	
	//Uklanjanje objekta iz dokumenta
	public void removeGraphicalObject(GraphicalObject go) {
		go.setSelected(false);
		go.removeGraphicalObjectListener(goListener);
		objects.remove(go);
		notifyListeners();
	}
	
	//Vrati listu postojeæih objekata
	public List<GraphicalObject> list() {
		return roObjects;
	}
	
	
	//Prijava...
	public void addDocumentModelListener(DocumentModelListener l) {
		listeners.add(l);
	}
	
	//Odjava...
	public void removeDocumentModelListener(DocumentModelListener l) {
		listeners.remove(l);
	}
	
	//Obavještavanje...
	public void notifyListeners() {
		for (DocumentModelListener documentModelListener : listeners) {
			documentModelListener.documentChange();
		}
	}
	
	
	//Promatraè koji æe biti registriran nad svim objektima crteža...
	private final GraphicalObjectListener goListener = new GraphicalObjectListener() {
		
		@Override
		public void graphicalObjectSelectionChanged(GraphicalObject go) {
			int index = selectedObjects.indexOf(go);
			if(go.isSelected()) {
				if (index == -1) {
					selectedObjects.add(go);
					
				} else {
					return;
				}
			}
			else{
					if (index != -1) {
						selectedObjects.remove(index);
					} else {
						return;
					}
			}
			notifyListeners();
			
			
			
		}
		
		@Override
		public void graphicalObjectChanged(GraphicalObject go) {
			notifyListeners();
			
		}
	};
	
	//Vrati listu selektiranih objekata
	public List<GraphicalObject> getSelectedObjects() {
		return roSelectedObjects;
	}
	
	//Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
	public void increaseZ(GraphicalObject go) {
		int index = objects.indexOf(go);
		if (index != -1 && index > 0) {
			objects.set(index, objects.get(index - 1));
			objects.set(index - 1, go);
		}
		notifyListeners();
	}
	//Pomakni predani objekt u listi objekata ne jedno mjesto ranije...
	public void decreaseZ(GraphicalObject go) {
		int index = objects.indexOf(go);
		if (index != -1 && index > 0) {
			objects.set(index, objects.get(index - 1));
			objects.set(index - 1, go);
		}
		notifyListeners();
	}
 
	//Pronaði postoji li u modelu neki objekt koji klin na toèku koja je
	//predana kao argument selektira i vrati ga ili vrati null. 
	public GraphicalObject findSelectedGraphicalObject(Point mousePoint) {
		Map<Double, GraphicalObject> possibleSelections = new HashMap<Double, GraphicalObject>();
		for (GraphicalObject go : objects) {
			double distance = go.selectionDistance(mousePoint);
			if (distance <= SELECTION_PROXIMITY) {
				possibleSelections.put(distance, go);
			}
		}
		if (!possibleSelections.isEmpty()) {
			double min = SELECTION_PROXIMITY + 1;
			for (Entry<Double, GraphicalObject> entry : possibleSelections.entrySet()) {
				if (entry.getKey() < min) {
					min = entry.getKey();
				}
			}
			return possibleSelections.get(min);
		}
		
		return null;
	}
	
	//Pronaði da li u predanom objektu predana toèka miša selektira neki hot-point.
	//toèka miša selektira onaj hot-point objekata kojemu je najbliža uz uvjet da ta
	//udaljenost nije veæa od SELECTION_PROXIMITY. Vraæa se index hot-pointa kojeg bi
	//predana toèka selektirali ili -1 ako takve nema.
	public int findSelectedHotPoint(GraphicalObject go, Point mousePoint) {
		for (int i = 0; i < go.getNumberOfHotPoints(); i++) {
			if (go.getHotPointDistance(i, mousePoint) <= SELECTION_PROXIMITY) {
				return i;
			}
		}
		return -1;
		
	}
	
	public void deselectAll() {
		while(selectedObjects.size() > 0) {
			selectedObjects.get(0).setSelected(false);
		}
	}
	
	
	public List<GraphicalObject> findSelectedGraphicalObjects(Point mp) {
		List<GraphicalObject> Go = new ArrayList<GraphicalObject>();
		for (GraphicalObject go : objects) {
			if (go.selectionDistance(mp) <= SELECTION_PROXIMITY/2) {
				Go.add(go);
			}
		}
		return Go;
	}

	
}
