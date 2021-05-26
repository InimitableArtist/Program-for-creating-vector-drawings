from graphicalobject import GraphicalObjectListener, GraphicalObject
from documentModelListener import DocumentModelListener
from point import Point


class ConcreteGraphicalObjectListener(GraphicalObjectListener, DocumentModel):

    def graphicalObjectSelectionChanged(go: GraphicalObject):
        index = DocumentModel._selectedObjects.index(go)
        if go.isSelected():
            if index == -1:
                DocumentModel._selectedObjects.append(go)
            else:
                return
        else:
            if index != -1:
                DocumentModel._selectedObjects.remove(go)
            else:
                return
        self.notifyListeners()

    def graphicalObjectChanged(go: GraphicalObject):
        self.notifyListeners()

class DocumentModel:

    SELECTION_PROXIMITY = 10

    #Kolekcija svih grafičkih objekata.
    _objects = None
    #Proxy oko kolekcije grafičkih objekata.
    _roObjects = None
    #Kolekcija prijavljenih promatrača
    _listeners = None
    #Kolekcija selektiranih objekata.
    _selectedObjects = None
    #Proxy oko kolekcije selektiranih objekata.
    _roSelectedObjects = None
    
    #Promatrač koji će biti registriran nad svim objektima crteža.
    _goListener = ConcreteGraphicalObjectListener()

    def __init__(self):
        self._objects = []
        self._roObjects = [self._objects]
        self._selectedObjects = []
        self._roSelectedObjects = [self._selectedObjects]
        self._listeners = []

    #Brisanje svih objekata iz modela.
    def clear(self):
        for go in self._objects:
            go.removeGraphicalObjectListener(self._goListener)
        
        self._objects.clear()
        self._selectedObjects.clear()
        self.notifyListeners()

    #Dodavanje objekta u dokument.
    def addGraphicalObject(self, go: GraphicalObject):
        if go.isSelected():
            self._selectedObjects.append(go)
        
        self._objects.append(go)
        go.addGraphicalObjectListener(self._goListener)
        self.notifyListeners()

    def removeGraphicalObject(self, go: GraphicalObject):
        go.setSelected(False)
        go.removeGraphicalObjectListener(self._goListener)
        self._objects.remove(go)
        self.notifyListeners()

    #Vrati listu postojecih objekata.
    def getList(self):
        return self._roObjects

    #Prijava.
    def addDocumentModelListener(listener: DocumentModelListener):
        self._listeners.append(listener)

    #Odjava.
    def removeDocumentModelListener(listener: DocumentModelListener):
        self._listeners.remove(listener)

    #Obavještavanje.
    def notifyListeners(self):
        for documentModelListener in self._listeners:
            documentModelListener.documentChanged()
    
    #Vrati listu selektiranih objekata.
    def getSelectedObjects(self):
        return self._roSelectedObjects

    #Pomakni predani objekt u listi objekata na jedno mjesto kasnije...
    #Time će se on iscrtati kasniej (pa će time možda veći dio biti vidljiv)
    def increaseZ(self, go: GraphicalObject):
        index = self._objects.index(go)
        if index != -1 and index < len(self._objects) - 1:
            self._objects.insert(index, self._objects[index + 1])
            self._objects.insert(index + 1, go)

        self.notifyListeners()

    def decreaseZ(self, go: GraphicalObject):
        index = self._objects.index(go)
        if index != -1 and index > 0:
            self._objects.insert(index, self._objects[index + 1])
            self._objects.insert(index - 1, go)

        self.notifyListeners()

    #Pronađi postoji li u modelu neki objekt koji klik na točku koja je
    #predana kao argument selektira i vrati ga ili vrati null.
    #Točka selektira objekt kojemu je najbliža uz uvjet da ta udaljenost
    #nije veća od SELECTION_PROXIMITY. Status selektiranosti objekta ova
    #metoda NE dira.
    def findSelectedGraphicalObject(self, mousePoint: Point):
        possibleSelections = {}
        for go in self._objects:
            distance = go.selectionDistance(mousePoint)
            if distance <= SELECTION_PROXIMITY:
                possibleSelections[distance] = go
        
        if bool(possibleSelections):
            _min = SELECTION_PROXIMITY + 1
            for item in possibleSelections.items():
                if item[0] < _min:
                    _min = item[0]
            
            return possibleSelections[_min]
        
        return None

    #Pronađe li da u predanom objektu predana točka miša selektira neki hot-point.
    #Točka miša selektira onaj hot-point objekta kojemu je najbliža uz uvjet da ta
    #udaljenost nije veća od SELECTION_PROXIMITY. Vraća se index hot-pointa
    #kojeg bi predana točka selektirala ili -1 ako takve nema. Status selekcije
    #se pri tome NE dira.
    def findSelectedHotPoint(self, go: GraphicalObject, mousePoint: Point):
            for i in range(go.getNumberOfHotPoints()):
                if go.getHotPointDistance(i, mousePoint) <= SELECTION_PROXIMITY:
                    return i
            return -1
    

