from rectangle import Rectangle
from graphicalobject import AbstractGraphicalObject
from point import Point
from geometryUtil import GeometryUtil
class LineSegment(AbstractGraphicalObject):
    
    def init(self,linp1=Point(0,0),linp2=Point(10,0)):
        #vidi kaj ces  s pozivanjem super()
        self.linp1 = linp1
        self.linp2 = linp2
        return
    
    def selectionDistance(self,mousePoint):
        return  GeometryUtil.distanceFromLineSegment(self.linp1,self.linp2,mousePoint)
    
    def getBoundingBoxy(self):
        
        width = abs(self.linp1.getX() - self.linp2.getX())
        height = abs(self.linp1.getY() - self.linp2.getY())
        centerX = (self.linp1.getX() + self.linp2.getX())/2
        centerY = (self.linp1.getY() + self.linp2.getY())/2
        return Rectangle(width=width,height=height,x=centerX,y=centerY)

    def duplicate(self):
        newObj = LineSegment(linp1=self.linp1,linp2=self.linp2)
        newObj.selected = self.selected.copy()
        return
    
    def getShapeName():
        return "Linija"

    def render(self, r):
        return