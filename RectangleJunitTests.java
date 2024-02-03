import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class RectangleJunitTests {

    private Rectangle rect1;
    private Rectangle rect2;
    private Rectangle rect3;

    @Before
    public void setUp() {
        rect1 = new Rectangle(0, 0, 10, 10);
        rect2 = new Rectangle(5, 5, 5, 5);
        rect3 = new Rectangle(10, 10, 20, 20);
    }

    @Test
    public void testRectangleCreation() {
        assertEquals("Checking x coordinate", 0, rect1.getxCoordinate());
        assertEquals("Checking y coordinate", 0, rect1.getyCoordinate());
        assertEquals("Checking width", 10, rect1.getWidth());
        assertEquals("Checking height", 10, rect1.getHeight());
    }
    
    //////////////////
    
    
    
    
    //////////////////
    @Test
    public void testIntersectWhenSecondRectangleIsImmediatelyToTheRight() {
        // r2 is immediately to the right of this rectangle
        Rectangle rect1 = new Rectangle(0, 0, 5, 5);
        Rectangle r2 = new Rectangle(5, 0, 5, 5);
        assertFalse(rect1.intersect(r2));
    }

    @Test
    public void testIntersectWhenSecondRectangleIsImmediatelyToLeft() {
        // r2 is immediately to the left of this rectangle
        Rectangle rect1 = new Rectangle(5, 0, 5, 5);
        Rectangle r2 = new Rectangle(0, 0, 5, 5);
        assertFalse(rect1.intersect(r2));
    }

    @Test
    public void testIntersectWhenSecondRectangleIsImmediatelyAbove() {
        // r2 is immediately above this rectangle
        Rectangle rect1 = new Rectangle(0, 0, 5, 5);
        Rectangle r2 = new Rectangle(0, 5, 5, 5);
        assertFalse(rect1.intersect(r2));
    }

    @Test
    public void testIntersectWhenSecondRectangleIsImmediatelyBelow() {
        // r2 is immediately below this rectangle
        Rectangle rect1 = new Rectangle(0, 5, 5, 5);
        Rectangle r2 = new Rectangle(0, 0, 5, 5);
        assertFalse(rect1.intersect(r2));
    }

    @Test
    public void testIntersectWhenRectanglesTouchAtCorners() {
        // r2 touches the bottom right corner of this rectangle
        Rectangle rect1 = new Rectangle(0, 0, 5, 5);
        Rectangle r2 = new Rectangle(5, 5, 5, 5);
        assertFalse(rect1.intersect(r2));
    }

    
    ////////////
    
    @Test
    public void testIntersectShouldReturnFalseWhenOneRectangleIsToTheLeftOfTheOther() {
        Rectangle toTheLeftRect = new Rectangle(-20, 0, 10, 10);
        assertFalse(rect1.intersect(toTheLeftRect));
    }

    @Test
    public void testIntersectShouldReturnFalseWhenOneRectangleIsToTheRightOfTheOther() {
        Rectangle toTheRightRect = new Rectangle(20, 0, 10, 10);
        assertFalse(rect1.intersect(toTheRightRect));
    }

    @Test
    public void testIntersectShouldReturnFalseWhenOneRectangleIsAboveTheOther() {
        Rectangle aboveRect = new Rectangle(0, 20, 10, 10);
        assertFalse(rect1.intersect(aboveRect));
    }

    @Test
    public void testIntersectShouldReturnFalseWhenOneRectangleIsBelowTheOther() {
        Rectangle belowRect = new Rectangle(0, -20, 10, 10);
        assertFalse(rect1.intersect(belowRect));
    }

    @Test
    public void testIntersectShouldReturnTrueWhenRectanglesOverlap() {
        Rectangle overlapRect = new Rectangle(5, 5, 10, 10);
        assertTrue(rect1.intersect(overlapRect));
    }
    
    
   ////////////////////

    @Test
    public void testIntersectTrue() {
        assertTrue("rect1 should intersect with rect2", rect1.intersect(rect2));
    }

    @Test
    public void testIntersectFalse() {
        assertFalse("rect1 should not intersect with rect3", rect1.intersect(rect3));
    }

    @Test
    public void testIntersectEdgeCase() {
        Rectangle edgeCaseRect = new Rectangle(10, 0, 10, 10);
        assertFalse("Edge touching should not be considered intersecting", rect1.intersect(edgeCaseRect));
    }
    
    @Test
    public void testIntersectWithOneInsideAnotherShouldBeTrue() {
        Rectangle insideRect = new Rectangle(5, 5, 2, 2);
        assertTrue("Rectangle inside another should intersect", rect1.intersect(insideRect));
    }

    @Test
    public void testIntersectWithNoOverlapShouldBeFalse() {
        Rectangle noOverlapRect = new Rectangle(20, 20, 5, 5);
        assertFalse("Non-overlapping rectangles should not intersect", rect1.intersect(noOverlapRect));
    }

    @Test
    public void testIntersectWithPartialOverlapShouldBeTrue() {
        Rectangle partialOverlapRect = new Rectangle(3, 3, 10, 10);
        assertTrue("Partially overlapping rectangles should intersect", rect1.intersect(partialOverlapRect));
    }

    @Test
    public void testIntersectWithCommonLineShouldBeFalse() {
        Rectangle commonLineRect = new Rectangle(0, 10, 10, 10);
        assertFalse("Rectangles with a common line should not intersect", rect1.intersect(commonLineRect));
    }

    @Test
    public void testIntersectWithTouchingAtPointShouldBeFalse() {
        Rectangle touchAtPointRect = new Rectangle(10, 10, 5, 5);
        assertFalse("Rectangles touching at a point should not intersect", rect1.intersect(touchAtPointRect));
    }

    @Test
    public void testIntersectWithSameRectangleShouldBeTrue() {
        assertTrue("Rectangle should intersect with itself", rect1.intersect(rect1));
    }

    @Test
    public void testEqualsTrue() {
        Rectangle rect4 = new Rectangle(0, 0, 10, 10);
        assertTrue("rect1 should be equal to rect4", rect1.equals(rect4));
    }

    @Test
    public void testEqualsFalse() {
        assertFalse("rect1 should not be equal to rect2", rect1.equals(rect2));
    }

    @Test
    public void testEqualsDifferentObject() {
        assertFalse("Rectangle should not be equal to a string", rect1.equals("test"));
    }

    @Test
    public void testToString() {
        assertEquals("Checking toString format", "(0, 0, 10, 10)", rect1.toString());
    }

    @Test
    public void testIsInvalidNegativeDimensions() {
        Rectangle invalidRect = new Rectangle(0, 0, -10, -10);
        assertTrue("Rectangle with negative dimensions should be invalid", invalidRect.isInvalid());
    }

    @Test
    public void testIsInvalidExceedsBoundary() {
        Rectangle boundaryExceedingRect = new Rectangle(1020, 1020, 10, 10);
        assertTrue("Rectangle exceeding boundary should be invalid", boundaryExceedingRect.isInvalid());
    }

    @Test
    public void testIsInvalidValidCase() {
        assertFalse("rect1 should be valid", rect1.isInvalid());
    }
    
    
    
    @Test
    public void testEqualsWithItselfShouldBeTrue() {
        // A rectangle should be equal to itself
        assertTrue("Rectangle should be equal to itself", rect1.equals(rect1));
    }

    @Test
    public void testEqualsWithNullShouldBeFalse() {
        // A rectangle should not be equal to null
        assertFalse("Rectangle should not be equal to null", rect1.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClassShouldBeFalse() {
        // A rectangle should not be equal to an object of a different class
        Object differentClassObject = new Object();
        assertFalse("Rectangle should not be equal to an object of a different class", rect1.equals(differentClassObject));
    }

    @Test
    public void testIsInvalidWithZeroWidthShouldBeTrue() {
        // A rectangle with a width of 0 should be invalid
        Rectangle zeroWidthRect = new Rectangle(10, 10, 0, 20);
        assertTrue("Rectangle with zero width should be invalid", zeroWidthRect.isInvalid());
    }

    @Test
    public void testIsInvalidWithZeroHeightShouldBeTrue() {
        // A rectangle with a height of 0 should be invalid
        Rectangle zeroHeightRect = new Rectangle(10, 10, 20, 0);
        assertTrue("Rectangle with zero height should be invalid", zeroHeightRect.isInvalid());
    }

    @Test
    public void testIsInvalidWithLargeCoordinatesShouldBeTrue() {
        // A rectangle that extends beyond the boundary should be invalid
        Rectangle largeRect = new Rectangle(1000, 1000, 30, 30);
        assertTrue("Rectangle extending beyond boundary should be invalid", largeRect.isInvalid());
    }

    @Test
    public void testIsInvalidWithNegativeCoordinatesShouldBeTrue() {
        // A rectangle with negative coordinates should be valid as long as it doesn't extend beyond the boundary
        Rectangle negativeCoordinatesRect = new Rectangle(-10, -10, 20, 20);
        assertFalse("Rectangle with negative coordinates should be valid", negativeCoordinatesRect.isInvalid());
    }
}
