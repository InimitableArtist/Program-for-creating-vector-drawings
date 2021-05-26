from abc import ABC, abstractmethod
from point import Point

class Renderer(ABC):

    @abstractmethod
    def drawLine(s: Point, e: Point):
        pass
    @abstractmethod
    def fillPolygon(points):
        pass
