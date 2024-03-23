package Model.ShapeInfo;

import ca.cmpt213.as4.UI.Canvas;

/**
 * The ShapeBorder interface only has one method, which is to draw itself
 * on the border, given a starting point and the bounds of where to draw. By having an
 * interface to take care of all info about the border, it allows for runtime modification
 * of the shape
 */
public interface ShapeBorder {

    void drawBorder(Canvas canvas, int startX, int startY, int width, int height);


}
