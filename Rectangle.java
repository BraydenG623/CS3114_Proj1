/**
 * This class holds the coordinates and dimensions of a rectangle and methods to
 * check if it intersects or has the same coordinates as an other rectangle.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Rectangle {
    // the x coordinate of the rectangle
    private int xCoordinate;
    // the y coordinate of the rectangle
    private int yCoordinate;
    // the distance from the x coordinate the rectangle spans
    private int width;
    // the distance from the y coordinate the rectangle spans
    private int height;

    /**
     * Creates an object with the values to the parameters given in the
     * xCoordinate, yCoordinate, width, height
     * 
     * @param x
     *            x-coordinate of the rectangle
     * @param y
     *            y-coordinate of the rectangle
     * @param w
     *            width of the rectangle
     * @param h
     *            height of the rectangle
     */
    public Rectangle(int x, int y, int w, int h) {
        xCoordinate = x;
        yCoordinate = y;
        width = w;
        height = h;
    }


    /**
     * Getter for the x coordinate
     *
     * @return the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }


    /**
     * Getter for the y coordinate
     *
     * @return the y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }


    /**
     * Getter for the width
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }


    /**
     * Getter for the height
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Checks if this rectangle intersects with another rectangle.
     * @param r2 The other rectangle to check intersection with.
     * @return true if the rectangles intersect, false otherwise.
     */
    public boolean intersect(Rectangle r2) {
        // Check if one rectangle is to the left of the other
        boolean toTheLeft = this.xCoordinate + this.width <= r2.xCoordinate 
                            || r2.xCoordinate + r2.width <= this.xCoordinate;

        // Check if one rectangle is above the other
        boolean above = this.yCoordinate + this.height <= r2.yCoordinate 
                        || r2.yCoordinate + r2.height <= this.yCoordinate;

        // If one rectangle is to the left of the 
        // other or above the other, they do not intersect
        return !(toTheLeft || above);
    }


    /**
     * Checks, if the invoking rectangle has the same coordinates as rec.
     * 
     * @param rec
     *            the rectangle parameter
     * @return true if the rectangle has the same coordinates as rec, false if
     *         not
     */
    public boolean equals(Object rec) {
       
        //Check for self-assignent on whether 
        //the rectangle in question is equal 
        //to "this"
        if (this == rec) return true;

        
        //Check for null and make sure the objects are in the same class
        if (rec == null || 
            getClass() != rec.getClass()) return false;

           
        //Typecast the object so that it can be compared properly
        Rectangle rectangle = (Rectangle) rec; 
        //Now we can start comparing the 
        //rectangles/objects and see 
        //if they are equals
        return this.xCoordinate == rectangle.xCoordinate && 
            this.yCoordinate == rectangle.yCoordinate && 
            this.width == rectangle.width && 
            this.height == rectangle.height; 
    }


    /**
     * Returns a string representation of 
     * this rectangle.
     * @return A string that represents the 
     * rectangle's coordinates and dimensions.
     */
    public String toString() {
        //Concat the strings
        return "(" + this.xCoordinate + ", " 
            + this.yCoordinate + 
            ", " + this.width + ", " + 
            this.height + ")";
    }


    /**
     * Checks if the rectangle has invalid parameters.
     * @return true if the rectangle's parameters are invalid, false otherwise.
     */
    public boolean isInvalid() {
        // Check if the rectangle's width or height is not positive
        boolean hasInvalidDimensions = 
            this.width <= 0 || this.height <= 0;
        
        // Check if the rectangle's starting coordinates are negative
        boolean hasNegativeCoordinates = 
            this.xCoordinate < 0 || this.yCoordinate < 0;
        
        // Check if the rectangle extends beyond the 1024x1024 world box
        boolean isOutsideWorldBox = 
            this.xCoordinate + this.width > 1024 || 
            this.yCoordinate + this.height > 1024;
        
        // A rectangle is invalid if any of the above conditions are true
        return hasInvalidDimensions || 
            hasNegativeCoordinates || 
            isOutsideWorldBox;
    }

}