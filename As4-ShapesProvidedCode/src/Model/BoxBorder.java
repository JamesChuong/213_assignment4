package Model;

import Model.ShapeInfo.ShapeBorder;
import ca.cmpt213.as4.UI.Canvas;

public class BoxBorder implements ShapeBorder {

    private String line;
    private char lineChar;

    @Override
    public void drawBorder(Canvas canvas, int top, int left, int height, int width) {

    }

    @Override
    public void setBorderStyle(String line, char lineChar) {
        this.line = line;
        this.lineChar = lineChar;
    }

}
