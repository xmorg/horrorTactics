class button: #clickable buttons
    def __init__(self, x, y, w, h):
        self.x =x
        self.y =y
        self.w =w
        self.h =h
    def getclick(self, x, y):
        if( x > self.x and x < self.x+self.w):
            if (y > self.y and  y < self.y + self.w ):
                return True
            return False
