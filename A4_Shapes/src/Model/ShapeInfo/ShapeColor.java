package Model.ShapeInfo;

import ca.cmpt213.as4.UI.Canvas;

/**
 * The ShapeColor interface is responsible for containing all info about a shape's color,
 * it only supports the operation to draw on the canvas given a starting point and bounds on
 * the area to color
 */
public interface ShapeColor {

    void drawColor(Canvas canvas, int startX, int startY, int width, int height);

}
