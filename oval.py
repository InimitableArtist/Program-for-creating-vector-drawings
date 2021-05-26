from tkinter.constants import CENTER
from Rectangle import Rectangle
from GraphObject import AbstractGraphicalObject
from Point import Point
from geometryUtil import GeometryUtil

class Oval(AbstractGraphicalObject):
    
    def init(self,rightP=Point(10,0),bottP=Point(0,10)):
        super().init([rightP,bottP])
        self.center = Point(bottP.getX(),rightP.getY())
        return
    
    def selectionDistance(self,mousePoint):
        return  GeometryUtil.distanceFromPoint(mousePoint,self.center)
    
    def getBoundingBoxy(self):
        height = abs(self.center.getY() - self.bottP.getY())
        width = abs(self.center.getX() - self.rightP.getX())
        return Rectangle(self.center.getX(),self.center.getY(),width,height)
    
    def duplicate(self):
        newObj = Oval(rightP=self.rightP,bottP=self.bottP)
        newObj.selected = self.selected.copy()
        return newObj
    
    def getShapeName():
        return "Oval"