from tkinter import *
import abc 
from point import Point
from GeometryUtil import GeometryUtil

class GraphicalObject(metaclass=abc.ABCMeta):
    
    @abc.abstractmethod
    def isSelected():
        pass
    
    @abc.abstractmethod
    def setSelected(selected):
        pass
    
    @abc.abstractmethod
    def getNumberOfHotPoints():
        pass
    
    @abc.abstractmethod
    def getHotPoint(index):
        pass
    
    
    @abc.abstractmethod
    def isHotPointSelected(index):
        pass
    
    @abc.abstractmethod
    def setHotPointSelected(index,selected):
        pass
    
    @abc.abstractmethod
    def getHotPointDistance(index,mousePoint):
        pass
    
    #Geometrijska operacija nad oblikom
    @abc.abstractmethod
    def translate(delta):
        pass
    
    @abc.abstractmethod
    def getBoundingBoxy():
        pass
    
    @abc.abstractmethod
    def selectionDistance(mousePoint):
        pass
    
    #observer za dojavu promjena modelu
    @abc.abstractmethod
    def addGraphicalObjectListener(l):
        pass
    
    @abc.abstractmethod
    def removeGraphicalObjectListener(l):
        pass
    
    #podrška za prototip(alatna traka ,stvarane objekata u crtežu)
    @abc.abstractmethod
    def getShapeName():
        pass
    
    @abc.abstractmethod
    def duplicate():
        pass
    
    @abc.abstractmethod
    def render(self,r):
        pass
class GraphicalObjectListener(metaclass=abc.ABCMeta):
    
    @abc.abstractmethod
    def graphicalObjectChanged(go):
        pass
    
    @abc.abstractmethod
    def graphicalObjectSelectionChanged(go):
        pass

class AbstractGraphicalObject(GraphicalObject):
    def __init__(self,points):
        self.hotPoints = points
        self.hotPointsSelected = [False for x in points]
        self.selected = False
        self.listeners=list()
        return
    
    def getHotPoint(self,idx):
        return self.hotPoints[idx]
    
    def setHotPoint(self,idx,hp):
        self.hotPoints[idx]=hp
        self.notifyListeners()
        return
    
    def getNumberOfHotPoints(self):
        return len(self.hotPoints)
    
    def getHotPointDistance(self,idx, p):
        return GeometryUtil.distanceFromPoint(self.hotPoints[idx],p)
    
    def isHotPointSelected(self,index):
        return self.hotPointsSelected[index]
    
    def setHotPointSelected(self,idx,state):
        self.hotPointsSelected[idx]=state
        self.notifySelectionListeners()
        return
    
    def isSelected(self):
        return self.selected
    
    def setSelected(self,state):
        self.selected = state
        self.notifySelectionListeners()
        return 
    
    def translate(self,delta):
        for i in range(len(self.hotPoints)):
            self.hotPoints[i]=self.hotPoints[i].translate(delta)
        self.notifyListeners()
        return
    
    def addGraphicalObjectListener(self,l):
        self.listeners.append(l)
        return
    
    def removeGraphicalObjectListener(self,l):
        self.listeners.remove(l)
        return
    
    def notifyListeners(self):
        for l in self.listeners:
            l.graphicalObjectChanged(self)
        return
    
    def notifySelectionListeners(self):
        for l in self.listeners:
            l.graphicalObjectSelectionChanged(self)
        return


