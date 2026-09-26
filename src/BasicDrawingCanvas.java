abstract class CanvasShape {
    private static int shapeCounter = 1000;
    private final String shapeId;
    CanvasShape() {
        shapeId = "SHAPE-" + (++shapeCounter);
    }
    public String getShapeId() {
        return shapeId;
    }
    public abstract double calculateArea();
    // One-argument overload
    public void scale(double factor) {
        // Actual dimension scaling is done by subclasses.
    }
    // Two-argument overload
    public void scale(double xFactor, double yFactor) {
        scale((xFactor + yFactor) / 2);
    }
}
class CanvasCircleShape extends CanvasShape {
    private double radius;
    public CanvasCircleShape(double radius) {
        this.radius = radius;
    }
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    @Override
    public void scale(double factor) {
        radius = radius * factor;
    }
    @Override
    public void scale(double xFactor, double yFactor) {
        radius = radius * ((xFactor + yFactor) / 2);
    }
}
class CanvasSquareShape extends CanvasShape {
    private double side;
    public CanvasSquareShape(double side) {
        this.side = side;
    }
    @Override
    public double calculateArea() {
        return side * side;
    }
    @Override
    public void scale(double factor) {
        side = side * factor;
    }
    @Override
    public void scale(double xFactor, double yFactor) {
        side = side * ((xFactor + yFactor) / 2);
    }
}
public class BasicDrawingCanvas {
    static void printArea(CanvasShape s) {
        System.out.println(s.calculateArea());
    }
    public static void main(String[] args) {
        CanvasCircleShape c = new CanvasCircleShape(5.0);
        System.out.println(c.calculateArea());
        CanvasSquareShape sq = new CanvasSquareShape(4.0);
        System.out.println(sq.calculateArea());
        sq.scale(2.0);
        System.out.println(sq.calculateArea());
        printArea(c);
        System.out.println(c.getShapeId());
        System.out.println(sq.getShapeId());
        // Shape cannot be directly instantiated:
        // CanvasShape s = new CanvasShape();   // Compile-time error
    }
}


