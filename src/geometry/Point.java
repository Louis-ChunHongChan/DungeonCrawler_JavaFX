package geometry;

public class Point {

    private static final float EPSILON = 1e-5f;

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point p) {
        this(p.x, p.y);
    }
    public Point() {
        this(0, 0);
    }

    public float distanceTo(float x, float y) {
        return Point.distanceBetween(this.x, this.y, x, y);
    }
    public float distanceTo(Point p) {
        return Point.distanceBetween(this.x, this.y, p.x, p.y);
    }
    public static float distanceBetween(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    public static float distanceBetween(Point p1, Point p2) {
        return Point.distanceBetween(p1.x, p1.y, p2.x, p2.y);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void moveX(float delta) {
        x += delta;
    }
    public void moveY(float delta) {
        y += delta;
    }
    public void move(float deltaX, float deltaY) {
        x += deltaX;
        y += deltaY;
    }
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point)) {
            return false;
        }
        Point p = (Point) other;
        return Math.abs(this.x - p.x) <= EPSILON && Math.abs(this.y - p.y) <= EPSILON;
    }

}
