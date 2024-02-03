import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains JUnit tests for the Rectangle class.
 * It tests various scenarios including rectangle creation,
 * intersection, equality, and boundary conditions.
 * 
 * @author Brayden Gardner
 * @version 1.0
 * @since 2024-02-03
 */

public class RectangleJunitTests {

    private Rectangle rect1;
    private Rectangle rect2;
    private Rectangle rect3;
    private Rectangle rectBase;
    private Rectangle rectAtOrigin;
    private Rectangle rectPositive;
    private Rectangle rectNegative;

    /**
     * Prepares the test environment before each test case.
     */
    @Before
    public void setUp() {
        rect1 = new Rectangle(0, 0, 10, 10);
        rect2 = new Rectangle(5, 5, 5, 5);
        rect3 = new Rectangle(10, 10, 20, 20);
        rectBase = new Rectangle(10, 10, 5, 5);

        rectAtOrigin = new Rectangle(0, 0, 10, 10);
        rectPositive = new Rectangle(10, 20, 30, 40);
        rectNegative = new Rectangle(-10, -20, 30, 40);

    }


    /**
     * Tests that rectangles touching at an edge are not considered to
     * intersect.
     */
    @Test
    public void testRectanglesTouchingAtEdgeDoNotIntersect() {
        Rectangle touchingAtRightEdge = new Rectangle(15, 10, 5, 5);
        assertFalse(
            "Rectangles touching at the right edge should not intersect",
            rectBase.intersect(touchingAtRightEdge));
    }


    /**
     * Tests that rectangles overlapping by just one unit are considered to
     * intersect.
     */
    @Test
    public void testRectanglesOverlapByOneUnitDoIntersect() {
        Rectangle overlappingByOneUnit = new Rectangle(14, 10, 5, 5);
        assertTrue("Rectangles overlapping by one unit should intersect",
            rectBase.intersect(overlappingByOneUnit));
    }


    /**
     * Tests that rectangles touching at a corner are not considered to
     * intersect.
     */
    @Test
    public void testRectanglesTouchingAtCornerDoNotIntersect() {
        Rectangle touchingAtTopRightCorner = new Rectangle(15, 15, 5, 5);
        assertFalse(
            "Rectangles touching at the top right corner should not intersect",
            rectBase.intersect(touchingAtTopRightCorner));
    }


    /**
     * Tests that rectangles overlapping inside each other are considered to
     * intersect.
     */
    @Test
    public void testRectanglesInsideOverlapDoIntersect() {
        Rectangle insideOverlap = new Rectangle(11, 11, 1, 1);
        assertTrue("Rectangles inside should intersect", rectBase.intersect(
            insideOverlap));
    }


    /**
     * Tests that rectangles with no shared area do not intersect.
     */
    @Test
    public void testRectanglesWithNoSharedAreaDoNotIntersect() {
        Rectangle noSharedAreaToLeft = new Rectangle(6, 10, 4, 5);
        assertFalse(
            "Rectangles with no shared area to the left should not intersect",
            rectBase.intersect(noSharedAreaToLeft));

        Rectangle noSharedAreaAbove = new Rectangle(10, 6, 5, 4);
        assertFalse("Rectangles with no shared area above should not intersect",
            rectBase.intersect(noSharedAreaAbove));
    }


    /**
     * Tests that rectangles that completely overlap each other are considered
     * to intersect.
     */
    @Test
    public void testRectanglesCompletelyOverlapDoIntersect() {
        Rectangle completelyOverlapping = new Rectangle(10, 10, 5, 5);
        assertTrue("Rectangles completely overlapping should intersect",
            rectBase.intersect(completelyOverlapping));
    }


    /**
     * Tests that rectangles partially overlapping each other are considered to
     * intersect.
     */
    @Test
    public void testRectanglePartiallyOverlappingDoIntersect() {
        Rectangle partiallyOverlapping = new Rectangle(12, 12, 10, 10);
        assertTrue("Rectangles partially overlapping should intersect", rectBase
            .intersect(partiallyOverlapping));
    }


    /**
     * Tests the initialization and
     * property values of a rectangle.
     */
    @Test
    public void testRectangleCreation() {
        assertEquals("Checking x coordinate", 0, rect1.getxCoordinate());
        assertEquals("Checking y coordinate", 0, rect1.getyCoordinate());
        assertEquals("Checking width", 10, rect1.getWidth());
        assertEquals("Checking height", 10, rect1.getHeight());
    }


    /**
     * Tests the initialization and
     * property values of a rectangle.
     */
    @Test
    public void testRectAtOriginCoordinates() {
        assertEquals("X coordinate at origin", 0, rectAtOrigin
            .getxCoordinate());
        assertEquals("Y coordinate at origin", 0, rectAtOrigin
            .getyCoordinate());
    }


    /**
     * Tests the initialization and
     * property values of a rectangle.
     */
    @Test
    public void testRectPositiveCoordinates() {
        assertEquals("Positive X coordinate", 10, rectPositive
            .getxCoordinate());
        assertEquals("Positive Y coordinate", 20, rectPositive
            .getyCoordinate());
    }


    /**
     * Tests the initialization and
     * property values of a rectangle.
     */
    @Test
    public void testRectNegativeCoordinates() {
        assertEquals("Negative X coordinate", -10, rectNegative
            .getxCoordinate());
        assertEquals("Negative Y coordinate", -20, rectNegative
            .getyCoordinate());
    }

    //////////////////


    /**
     * Tests rectangle intersection when the second rectangle starts exactly
     * where the first rectangle ends on the X-axis.
     */
    @Test
    public void testIntersectWhenSecondRectangleEndsWhereFirstBeginsOnXAxis() {
        Rectangle rectEx = new Rectangle(11, 0, 5, 5);
        Rectangle r2 = new Rectangle(5, 0, 5, 5); // starts exactly where rectEx
                                                  // ends
        assertFalse(
            "r2 shouldn't meet when starts where rectEx ends X",
            rectEx.intersect(r2));

        r2 = new Rectangle(4, 0, 5, 5); // overlaps rectEx by 1 on X-axis
        assertFalse("r2 should not intersect when it overlaps rectEx on X-axis",
            rectEx.intersect(r2));
    }


    /**
     * Tests rectangle intersection when the second rectangle starts exactly
     * where the first rectangle ends on the Y-axis.
     */
    @Test
    public void testIntersectWhenSecondRectangleEndsWhereFirstBeginsOnYAxis() {
        Rectangle rectEx = new Rectangle(0, 11, 5, 5);
        Rectangle r2 = new Rectangle(0, 5, 5, 5); // starts exactly where rectEx
                                                  // ends
        assertFalse(
            "r2 shouldn't meet when starts where rectEx ends Y",
            rectEx.intersect(r2));

        r2 = new Rectangle(0, 4, 5, 5); // overlaps rectEx by 1 on Y-axis
        assertFalse("r2 shouldn't intersect upon overlaps rectEx on Y",
            rectEx.intersect(r2));
    }

    //////////////////
// @Test
// public void testIntersectWhenSecondRectangleIsJustToTheRight() {
// Rectangle rectEx = new Rectangle(0, 0, 5, 5);
// Rectangle r2 = new Rectangle(6, 0, 5, 5); // Just beyond the edge of rectEx
// assertFalse(rectEx.intersect(r2));
//
// r2 = new Rectangle(5, 0, 5, 5); // Touching the edge should still not
// intersect
// assertFalse(rectEx.intersect(r2));
//
// r2 = new Rectangle(4, 0, 5, 5); // Now they should intersect
// assertTrue(rectEx.intersect(r2));
// }
//
// @Test
// public void testIntersectWhenSecondRectangleIsJustAbove() {
// Rectangle rectEx = new Rectangle(0, 0, 5, 5);
// Rectangle r2 = new Rectangle(0, 6, 5, 5); // Just above rectEx
// assertFalse(rectEx.intersect(r2));
//
// r2 = new Rectangle(0, 5, 5, 5); // Touching the edge should still not
// intersect
// assertFalse(rectEx.intersect(r2));
//
// r2 = new Rectangle(0, 4, 5, 5); // Overlapping rectEx
// assertTrue(rectEx.intersect(r2));
// }

// @Test
// public void testIntersectWhenSecondRectangleIsImmediatelyToTheRight() {
// // r2 is immediately to the right of this rectangle
// Rectangle rectEx = new Rectangle(0, 0, 5, 5);
// Rectangle r2 = new Rectangle(5, 0, 5, 5);
// assertFalse(rectEx.intersect(r2));
// }
//
// @Test
// public void testIntersectWhenSecondRectangleIsImmediatelyToLeft() {
// // r2 is immediately to the left of this rectangle
// Rectangle rectEx = new Rectangle(5, 0, 5, 5);
// Rectangle r2 = new Rectangle(0, 0, 5, 5);
// assertFalse(rectEx.intersect(r2));
// }
//
// @Test
// public void testIntersectWhenSecondRectangleIsImmediatelyAbove() {
// // r2 is immediately above this rectangle
// Rectangle rectEx = new Rectangle(0, 0, 5, 5);
// Rectangle r2 = new Rectangle(0, 5, 5, 5);
// assertFalse(rectEx.intersect(r2));
// }
//
// @Test
// public void testIntersectWhenSecondRectangleIsImmediatelyBelow() {
// // r2 is immediately below this rectangle
// Rectangle rectEx = new Rectangle(0, 5, 5, 5);
// Rectangle r2 = new Rectangle(0, 0, 5, 5);
// assertFalse(rectEx.intersect(r2));
// }
//
// @Test
// public void testIntersectWhenRectanglesTouchAtCorners() {
// // r2 touches the bottom right corner of this rectangle
// Rectangle rectEx = new Rectangle(0, 0, 5, 5);
// Rectangle r2 = new Rectangle(5, 5, 5, 5);
// assertFalse(rectEx.intersect(r2));
// }

    ////////////


    /**
     * Verifies that rectangles do not intersect when one is completely to the
     * left of the other.
     */
    @Test
    public void testIntersectFalseWhenRectLeft() {
        Rectangle toTheLeftRect = new Rectangle(-20, 0, 10, 10);
        assertFalse(rect1.intersect(toTheLeftRect));
    }


    /**
     * Verifies that rectangles do not intersect when one is completely to the
     * right of the other.
     */
    @Test
    public void testIntersectFalseWhenRectRight() {
        Rectangle toTheRightRect = new Rectangle(20, 0, 10, 10);
        assertFalse(rect1.intersect(toTheRightRect));
    }


    /**
     * Verifies that rectangles do not intersect when one is completely above
     * the other.
     */
    @Test
    public void testIntersectFalseWhenRectAbove() {
        Rectangle aboveRect = new Rectangle(0, 20, 10, 10);
        assertFalse(rect1.intersect(aboveRect));
    }


    /**
     * Verifies that rectangles do not intersect when one is completely below
     * the other.
     */
    @Test
    public void testIntersectFalseWhenRectBelow() {
        Rectangle belowRect = new Rectangle(0, -20, 10, 10);
        assertFalse(rect1.intersect(belowRect));
    }


    /**
     * Verifies that rectangles do intersect when they overlap.
     */
    @Test
    public void testIntersectTrueWhenOverlap() {
        Rectangle overlapRect = new Rectangle(5, 5, 10, 10);
        assertTrue(rect1.intersect(overlapRect));
    }

    ////////////////////


    /**
     * Tests that two intersecting rectangles are correctly identified as
     * intersecting.
     */
    @Test
    public void testIntersectTrue() {
        assertTrue("rect1 should intersect with rect2", rect1.intersect(rect2));
    }


    /**
     * Tests that two non-intersecting rectangles are correctly identified as
     * not intersecting.
     */
    @Test
    public void testIntersectFalse() {
        assertFalse("rect1 should not intersect with rect3", rect1.intersect(
            rect3));
    }


    /**
     * Tests that rectangles touching at an edge are correctly identified as not
     * intersecting.
     */
    @Test
    public void testIntersectEdgeCase() {
        Rectangle edgeCaseRect = new Rectangle(10, 0, 10, 10);
        assertFalse("Edge touching should not be considered intersecting", rect1
            .intersect(edgeCaseRect));
    }


    /**
     * Tests that a rectangle completely inside another rectangle is correctly
     * identified as intersecting.
     */
    @Test
    public void testIntersectWithOneInsideAnotherShouldBeTrue() {
        Rectangle insideRect = new Rectangle(5, 5, 2, 2);
        assertTrue("Rectangle inside another should intersect", rect1.intersect(
            insideRect));
    }


    /**
     * Tests that rectangles with no overlap are correctly identified as not
     * intersecting.
     */
    @Test
    public void testIntersectWithNoOverlapShouldBeFalse() {
        Rectangle noOverlapRect = new Rectangle(20, 20, 5, 5);
        assertFalse("Non-overlapping rectangles should not intersect", rect1
            .intersect(noOverlapRect));
    }


    /**
     * Tests that rectangles with partial overlap are correctly identified as
     * intersecting.
     */
    @Test
    public void testIntersectWithPartialOverlapShouldBeTrue() {
        Rectangle partialOverlapRect = new Rectangle(3, 3, 10, 10);
        assertTrue("Partially overlapping rectangles should intersect", rect1
            .intersect(partialOverlapRect));
    }


    /**
     * Tests that rectangles sharing only a common line are correctly identified
     * as not intersecting.
     */
    @Test
    public void testIntersectWithCommonLineShouldBeFalse() {
        Rectangle commonLineRect = new Rectangle(0, 10, 10, 10);
        assertFalse("Rectangles with a common line should not intersect", rect1
            .intersect(commonLineRect));
    }


    /**
     * Tests that rectangles touching at a single point are correctly identified
     * as not intersecting.
     */
    @Test
    public void testIntersectWithTouchingAtPointShouldBeFalse() {
        Rectangle touchAtPointRect = new Rectangle(10, 10, 5, 5);
        assertFalse("Rectangles touching at a point should not intersect", rect1
            .intersect(touchAtPointRect));
    }


    /**
     * Tests that a rectangle intersects with itself.
     */
    @Test
    public void testIntersectWithSameRectangleShouldBeTrue() {
        assertTrue("Rectangle should intersect with itself", rect1.intersect(
            rect1));
    }


    /**
     * Tests that two identical rectangles are considered equal.
     */
    @Test
    public void testEqualsTrue() {
        Rectangle rect4 = new Rectangle(0, 0, 10, 10);
        assertTrue("rect1 should be equal to rect4", rect1.equals(rect4));
    }


    /**
     * Tests that two rectangles with different properties are not considered
     * equal.
     */
    @Test
    public void testEqualsFalse() {
        assertFalse("rect1 should not be equal to rect2", rect1.equals(rect2));
    }


    /**
     * Tests that a rectangle is not considered equal to an object of a
     * different type.
     */
    @Test
    public void testEqualsDifferentObject() {
        assertFalse("Rectangle should not be equal to a string", rect1.equals(
            "test"));
    }

    /////


    /**
     * Verifies that two identical rectangles are considered equal.
     */
    @Test
    public void testEqualsIdenticalRectangles() {
        Rectangle rect4 = new Rectangle(0, 0, 10, 10);
        assertTrue("Identical rectangles should be equal", rect1.equals(rect4));
    }


    /**
     * Tests equality for rectangles with different x coordinates.
     */
    @Test
    public void testEqualsDifferentXCoordinate() {
        Rectangle rectDiffX = new Rectangle(1, 0, 10, 10);
        assertFalse("Rectangles with different xCoordinate should not be equal",
            rect1.equals(rectDiffX));
    }


    /**
     * Tests equality for rectangles with different y coordinates.
     */
    @Test
    public void testEqualsDifferentYCoordinate() {
        Rectangle rectDiffY = new Rectangle(0, 1, 10, 10);
        assertFalse("Rectangles with different yCoordinate should not be equal",
            rect1.equals(rectDiffY));
    }


    /**
     * Tests equality for rectangles with different widths.
     */
    @Test
    public void testEqualsDifferentWidth() {
        Rectangle rectDiffWidth = new Rectangle(0, 0, 11, 10);
        assertFalse("Rectangles with different width should not be equal", rect1
            .equals(rectDiffWidth));
    }


    /**
     * Tests equality for rectangles with different heights.
     */
    @Test
    public void testEqualsDifferentHeight() {
        Rectangle rectDiffHeight = new Rectangle(0, 0, 10, 11);
        assertFalse("Rectangles with different height should not be equal",
            rect1.equals(rectDiffHeight));
    }


    /**
     * Verifies that a rectangle is not equal to a null object.
     */
    @Test
    public void testEqualsNullObject() {
        assertFalse("Rectangle should not be equal to null", rect1.equals(
            null));
    }


    /**
     * Tests that a rectangle is not considered equal to an object of a
     * different type.
     */
    @Test
    public void testEqualsDifferentType() {
        Object differentType = new Object();
        assertFalse(
            "Rectangle should not be equal to an object of a different type",
            rect1.equals(differentType));
    }


    /**
     * Confirms that a rectangle is equal to itself.
     */
    @Test
    public void testEqualsWithItselfShouldBeTrue() {
        assertTrue("Rectangle should be equal to itself", rect1.equals(rect1));
    }


    /**
     * Verifies that a rectangle is not equal to null.
     */
    @Test
    public void testEqualsWithNullShouldBeFalse() {
        assertFalse("Rectangle should not be equal to null", rect1.equals(
            null));
    }


    /**
     * Tests that a rectangle is not considered equal to an object of a
     * different class.
     */
    @Test
    public void testEqualsWithDifferentClassShouldBeFalse() {
        Object differentClassObject = new Object();
        assertFalse(
            "Rectangle should not be equal to an object of a different class",
            rect1.equals(differentClassObject));
    }


    /**
     * Checks the string representation of a rectangle.
     */
    @Test
    public void testToString() {
        assertEquals("Checking toString format", "(0, 0, 10, 10)", rect1
            .toString());
    }


    /**
     * Verifies the invalidation of rectangles with negative dimensions.
     */
    @Test
    public void testIsInvalidNegativeDimensions() {
        Rectangle invalidRect = new Rectangle(0, 0, -10, -10);
        assertTrue("Rectangle with negative dimensions should be invalid",
            invalidRect.isInvalid());
    }


    /**
     * Tests for invalidation of rectangles that exceed the allowed boundary.
     */
    @Test
    public void testIsInvalidExceedsBoundary() {
        Rectangle boundaryExceedingRect = new Rectangle(1020, 1020, 10, 10);
        assertTrue("Rectangle exceeding boundary should be invalid",
            boundaryExceedingRect.isInvalid());
    }


    /**
     * Verifies a valid rectangle does not violate any constraints.
     */
    @Test
    public void testIsInvalidValidCase() {
        assertFalse("rect1 should be valid", rect1.isInvalid());
    }

    // Continue with JavaDoc comments for the remaining test methods in a
    // similar manner.


    /**
     * Verifies that a rectangle with zero width is considered invalid.
     */
    @Test
    public void testIsInvalidWithZeroWidthShouldBeTrue() {
        Rectangle zeroWidthRect = new Rectangle(10, 10, 0, 20);
        assertTrue("Rectangle with zero width should be invalid", zeroWidthRect
            .isInvalid());
    }


    /**
     * Verifies that a rectangle with zero height is considered invalid.
     */
    @Test
    public void testIsInvalidWithZeroHeightShouldBeTrue() {
        Rectangle zeroHeightRect = new Rectangle(10, 10, 20, 0);
        assertTrue("Rectangle with zero height should be invalid",
            zeroHeightRect.isInvalid());
    }


    /**
     * Tests whether a rectangle extending beyond the predefined boundary is
     * marked as invalid.
     */
    @Test
    public void testIsInvalidWithLargeCoordinatesShouldBeTrue() {
        Rectangle largeRect = new Rectangle(1000, 1000, 30, 30);
        assertTrue("Rectangle extending beyond boundary should be invalid",
            largeRect.isInvalid());
    }


    /**
     * Verifies that a rectangle with negative coordinates is still considered
     * valid if it does not extend beyond the boundary.
     */
    @Test
    public void testIsInvalidWithNegativeCoordinatesShouldBeTrue() {
        Rectangle negativeCoordinatesRect = new Rectangle(-10, -10, 20, 20);
        assertFalse("Rectangle with negative coordinates should be valid",
            negativeCoordinatesRect.isInvalid());
    }


    /**
     * Confirms that a rectangle extending beyond the 1024x1024 boundary on
     * width is considered invalid.
     */
    @Test
    public void testIsInvalidWidthBeyondBoundary() {
        Rectangle rectWidthBeyondBoundary = new Rectangle(1020, 10, 10, 10);
        assertTrue(
            "Rectangle width extending past 1024 is invalid",
            rectWidthBeyondBoundary.isInvalid());
    }


    /**
     * Confirms that a rectangle extending beyond the 1024x1024 boundary on
     * height is considered invalid.
     */
    @Test
    public void testIsInvalidHeightBeyondBoundary() {
        Rectangle rectHeightBeyondBoundary = new Rectangle(10, 1020, 10, 10);
        assertTrue(
            "Rectangle height extending past 1024 is invalid",
            rectHeightBeyondBoundary.isInvalid());
    }


    /**
     * Tests if a rectangle starting exactly at the boundary with zero width or
     * height is considered invalid.
     */
    @Test
    public void testIsInvalidWidthExactlyBoundary() {
        Rectangle rectWidthExactlyBoundary = new Rectangle(1024, 10, 0, 10);
        assertTrue(
            "Rectangle start at the boundary on width should be invalid",
            rectWidthExactlyBoundary.isInvalid());
    }


    /**
     * Tests if a rectangle starting exactly at the boundary with zero width or
     * height is considered invalid.
     */
    @Test
    public void testIsInvalidHeightExactlyBoundary() {
        Rectangle rectHeightExactlyBoundary = new Rectangle(10, 1024, 10, 0);
        assertTrue(
            "Rectangle starting at the boundary on height should be invalid",
            rectHeightExactlyBoundary.isInvalid());
    }


    /**
     * Verifies that rectangles with negative coordinates but within the
     * boundary are not marked invalid.
     */
    @Test
    public void testIsInvalidWidthNegativeButWithinBoundary() {
        Rectangle rectWidthNegativeWithinBoundary = new Rectangle(-5, 10, 15,
            10);
        assertFalse(
            "Rectangle negative xCoord but in boundary isn't be invalid",
            rectWidthNegativeWithinBoundary.isInvalid());
    }


    /**
     * Verifies that rectangles with negative coordinates but within the
     * boundary are not marked invalid.
     * This uses negative y coordinate
     */
    @Test
    public void testIsInvalidHeightNegativeButWithinBoundary() {
        Rectangle rectHeightNegativeWithinBoundary = new Rectangle(10, -5, 10,
            15);
        assertFalse(
            "Rectangle negative yCoord but in bound isn't invalid",
            rectHeightNegativeWithinBoundary.isInvalid());
    }


    /**
     * Validates that rectangles with both zero width and height are considered
     * invalid.
     */
    @Test
    public void testIsInvalidWidthAndHeightZero() {
        Rectangle rectZeroWidthAndHeight = new Rectangle(10, 10, 0, 0);
        assertTrue("Rectangle with zero width and height should be invalid",
            rectZeroWidthAndHeight.isInvalid());
    }


    /**
     * Checks if rectangles partially extending beyond the 1024x1024 boundary
     * are marked as invalid.
     */
    @Test
    public void testIsInvalidPartiallyBeyondBoundary() {
        Rectangle rectPartiallyBeyondBoundary = new Rectangle(1015, 1015, 20,
            20);
        assertTrue(
            "Rectangle partially beyond the 1024 boundary should be invalid",
            rectPartiallyBeyondBoundary.isInvalid());
    }


    /**
     * Asserts that rectangles completely within the 1024x1024 boundary are
     * considered valid.
     */
    @Test
    public void testIsInvalidCompletelyWithinBoundary() {
        Rectangle rectCompletelyWithinBoundary = new Rectangle(500, 500, 100,
            100);
        assertFalse(
            "Rectangle fully within the 1024 shouldn't be invalid",
            rectCompletelyWithinBoundary.isInvalid());
    }


    /**
     * Confirms that rectangles with negative width or height are deemed
     * invalid.
     */
    @Test
    public void testIsInvalidNegativeWidth() {
        Rectangle rectNegativeWidth = new Rectangle(10, 10, -1, 10);
        assertTrue("Rectangle with negative width should be invalid",
            rectNegativeWidth.isInvalid());
    }


    /**
     * Confirms that rectangles with negative height are considered invalid.
     */
    @Test
    public void testIsInvalidNegativeHeight() {
        Rectangle rectNegativeHeight = new Rectangle(10, 10, 10, -1);
        assertTrue("Rectangle with negative height should be invalid",
            rectNegativeHeight.isInvalid());
    }


    /**
     * Tests that a rectangle touching the 1024 boundary on width is considered
     * valid.
     */
    @Test
    public void testIsValidAtBoundaryWidth() {
        // A rectangle that touches the 1024 boundary on width but does not
        // exceed it
        Rectangle boundaryWidthRect = new Rectangle(0, 0, 1024, 10);
        assertFalse(
            "Rectangle touching the boundary on width should not be invalid",
            boundaryWidthRect.isInvalid());
    }


    /**
     * Tests that a rectangle touching the 1024 boundary on height is considered
     * valid.
     */
    @Test
    public void testIsValidAtBoundaryHeight() {
        // A rectangle that touches the 1024 boundary on height but does not
        // exceed it
        Rectangle boundaryHeightRect = new Rectangle(0, 0, 10, 1024);
        assertFalse(
            "Rectangle touching the boundary on height should not be invalid",
            boundaryHeightRect.isInvalid());
    }


    /**
     * Tests that a rectangle slightly exceeding the 1024 boundary on width is
     * considered invalid.
     */
    @Test
    public void testIsInvalidJustBeyondBoundaryWidth() {
        // A rectangle that just exceeds the 1024 boundary on width
        Rectangle justBeyondWidth = new Rectangle(0, 0, 1025, 10);
        assertTrue(
            "Rectangle just beyond the boundary on width should be invalid",
            justBeyondWidth.isInvalid());
    }


    /**
     * Tests that a rectangle slightly exceeding the 1024 boundary on height is
     * considered invalid.
     */
    @Test
    public void testIsInvalidJustBeyondBoundaryHeight() {
        // A rectangle that just exceeds the 1024 boundary on height
        Rectangle justBeyondHeight = new Rectangle(0, 0, 10, 1025);
        assertTrue(
            "Rectangle just beyond the boundary on height should be invalid",
            justBeyondHeight.isInvalid());
    }

}
