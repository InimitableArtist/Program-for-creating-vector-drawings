from point import Point
import math

class GeometryUtil:

    def distanceFromPoint(point1: Point, point2: Point):
        return math.hypot(abs(point2.getX() - point1.getX()), abs(point2.getY() - point1.getY()))

    def distanceFromLineSegment(start: Point, end: Point, point: Point):
        if point.getX() < start.getX():
            return self.distanceFromPoint(point, start)
        if (point.getX() > end.getX()):
            return self.distanceFromPoint(point, eend)
        a = start.getY() - end.getY()
        b = end.getX() - start.getX()
        c = start.getX() * end.getY() - end.getX() * start.getY()
        return abs((a * point.getX() + b * point.getY() + c)) / (math.sqrt(a * a + b * b))   