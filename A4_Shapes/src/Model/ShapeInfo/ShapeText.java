package Model.ShapeInfo;

import ca.cmpt213.as4.UI.Canvas;

/**
 * The ShapeText interface supports one operation, which is to place text on the canvas given a
 * starting point and the bounds on the area on which to place text. Having this method supports runtime
 * modification of a shape's text.
 */
public interface ShapeText {

    void placeText(Canvas canvas, int top, int left, int verticalSpace, int horizontalSpace);

}
