class Point:

    _x = 0
    _y = 0

    def __init__(x, y):
        self._x = x
        self._y = y

    def getX(self):
        return self._x

    def getY(self):
        return self._y
    
    def translate(self, dp: Point):
        return Point(self.getX()  + dp.getX(), self.getY() + dp.getY())

    def difference(self, p: Point):
        return Point(self.getX() - p.getX(), self.getY() - p.getY())
