package Model.ShapeInfo;

import ca.cmpt213.as4.UI.Canvas;

public interface ShapeBorder {

    public void drawBorder(Canvas canvas, int top, int left, int height, int width);

    public void setBorderStyle(String line, char lineChar);

}
