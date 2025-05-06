package Data;

public class boundingBox {
    private int x1, y1, x2, y2;

    public boundingBox(int x1, int x2, int y1, int y2) {
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
    }

    public int getX1() {
        return x1;
    }
    
    public int getX2() {
        return x2;
    }
    
    public int getY1() {
        return y1;
    }
    
    public int getY2() {
        return y2;
    }
    
    public void setX1(int x1) {
        this.x1 = x1;
    }
    
    public void setX2(int x2) {
        this.x2 = x2;
    }
    
    public void setY1(int y1) {
        this.y1 = y1;
    }
    
    public void setY2(int y2) {
        this.y2 = y2;
    }

    public boolean isColliding(boundingBox other) {
        return (this.x1 < other.x2 &&
                this.x2 > other.x1 &&
                this.y1 < other.y2 &&
                this.y2 > other.y1);
    }

    public String toString() {
        return "[" + x1 + "," + y1 + "," + x2 + "," + y2 + "]";
    }
}
