package lib;

public class ClampedInt {

    private int min;
    private int max;
    private int val;

    public ClampedInt(int min, int max, int val) {
        if (val > max || val < min) {
            throw new IllegalArgumentException("Starting value must be "
                    + "between min and max, inclusive");
        }
        this.min = min;
        this.max = max;
        this.val = val;
    }
    public ClampedInt(int min, int max) {
        this(min, max, min);
    }

    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        if (min > this.max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }
        this.min = min;
    }
    public void changeMin(int delta) {
        int newMin = this.min + delta;
        if (newMin > this.max) {
            throw new IllegalArgumentException("New min must be less than or equal to max");
        }
        this.min = newMin;
    }

    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        if (max < this.min) {
            throw new IllegalArgumentException("Max must be greater than or equal to min");
        }
        this.max = max;
    }
    public void changeMax(int delta) {
        int newMax = this.max + delta;
        if (newMax < this.min) {
            throw new IllegalArgumentException("New max must be greater than or equal to min");
        }
        this.max = newMax;
    }

    public int getValue() {
        return val;
    }

    public void setValue(int val) {
        if (val < this.min || val > this.max) {
            throw new IllegalArgumentException("Value must be between min and max, inclusive");
        }
        this.val = val;
    }
    public void changeValue(int delta) {
        this.val = Math.max(this.min, Math.min(this.max, this.val + delta));
    }
}
