package Model.ShapeInfo;

import ca.cmpt213.as4.UI.Canvas;

public interface ShapeBorder {

    void drawBorder(Canvas canvas, int top, int left, int height, int width);

    void setBorderStyle(String line, char lineChar);

}
