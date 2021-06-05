package geometry;

public class Rectangle {

    private Point location;
    private float width;
    private float height;

    public Rectangle(float x, float y, float width, float height) {
        this.location = new Point(x, y);
        this.width = width;
        this.height = height;
    }
    public Rectangle(Point location, float width, float height) {
        this.location = new Point(location);
        this.width = width;
        this.height = height;
    }
    public Rectangle() {
        this(0, 0, 0, 0);
    }
    public Rectangle(Rectangle r) {
        this(r.getLocation(), r.getWidth(), r.getHeight());
    }

    public boolean contains(Rectangle r) {
        return this.getX() <= r.getX()
                && this.getX() + this.getWidth() >= r.getX() + r.getWidth()
                && this.getY() <= r.getY()
                && this.getY() + this.getHeight() >= r.getY() + r.getHeight();
    }
    // from java.awt.Rectangle.intersects(Rectangle r)
    public boolean intersects(Rectangle r) {
        float tw = this.width;
        float th = this.height;
        float rw = r.width;
        float rh = r.height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        float tx = this.getX();
        float ty = this.getY();
        float rx = r.getX();
        float ry = r.getY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;

        return ((rw < rx || rw > tx)
                && (rh < ry || rh > ty)
                && (tw < tx || tw > rx)
                && (th < ty || th > ry));
    }
    // From https://stackoverflow.com/a/26178015
    public float minDistanceTo(Rectangle r) {
        boolean left = r.getX() + r.getWidth() < this.getX();
        boolean right = this.getX() + this.getWidth() < r.getX();
        boolean bottom = r.getY() + r.getHeight() < this.getY();
        boolean top = this.getY() + this.getHeight() < r.getY();
        if (top && left) {
            return Point.distanceBetween(
                    this.getX(), this.getY() + this.getHeight(),
                    r.getX() + r.getWidth(), r.getY()
            );
        } else if (left && bottom) {
            return Point.distanceBetween(
                    this.getX(), this.getY(),
                    r.getX() + r.getWidth(), r.getY() + r.getHeight()
            );
        } else if (bottom && right) {
            return Point.distanceBetween(
                    this.getX() + this.getWidth(), this.getY(),
                    r.getX(), r.getY() + r.getHeight()
            );
        } else if (right && top) {
            return Point.distanceBetween(
                    this.getX() + this.getWidth(), this.getY() + this.getHeight(),
                    r.getX(), r.getY()
            );
        } else if (left) {
            return this.getX() - (r.getX() + r.getWidth());
        } else if (right) {
            return r.getX() - (this.getX() + this.getWidth());
        } else if (bottom) {
            return this.getY() - (r.getY() + r.getHeight());
        } else if (top) {
            return r.getY() - (this.getY() + this.getHeight());
        } else {
            return 0;
        }
    }
    public Point getCenter() {
        return new Point(this.getX() + this.getWidth() / 2f, this.getY() + this.getHeight() / 2f);
    }

    public float getX() {
        return location.getX();
    }
    public float getY() {
        return location.getY();
    }
    public Point getLocation() {
        return new Point(location);
    }
    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
    public void moveX(float delta) {
        location.moveX(delta);
    }
    public void moveY(float delta) {
        location.moveY(delta);
    }
    public void move(float deltaX, float deltaY) {
        location.move(deltaX, deltaY);
    }
    public void moveTo(float x, float y) {
        location.moveTo(x, y);
    }
    public void moveTo(Point p) {
        location.moveTo(p.getX(), p.getY());
    }
    public void changeWidth(float delta) {
        width += delta;
    }
    public void changeHeight(float delta) {
        height += delta;
    }
    public void setWidth(float width) {
        this.width = width;
    }
    public void setHeight(float height) {
        this.height = height;
    }

}
