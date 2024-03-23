package Model.BoxInfo;

import Model.ShapeInfo.*;
import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;

/**
 * The BoxShape class implements the methods from the DrawableShape interface.
 * It contains all the attributes a box can have, but each one is encapsulated with
 * its respective interface.This means each operation this class needs is composed
 * of a series of operations for each field.
 */

public class BoxShape implements DrawableShape {

    private ShapeBorder border;
    private ShapeColor color;
    private ShapeText text;
    private BoxSize size;
    private BoxPosition position;

    public BoxShape(int top, int left, int width, int height, String background, 
                    String backgroundColor, String line, char lineChar, String fill, String fillText){
        border = new BoxBorder(line, lineChar);
        color = new BoxColor(background, backgroundColor);
        text = new BoxText(fill, fillText);
        size = new BoxSize(height, width);
        position = new BoxPosition(top, left);
    }
    @Override
    public void draw(Canvas canvas) {
        color.drawColor(canvas, position.getLeft(),position.getTop(), size.getWidth(), size.getHeight());
        border.drawBorder(canvas, position.getLeft(), position.getTop(), size.getWidth(), size.getHeight());
        text.placeText(canvas, position.getTop()+1, position.getLeft()+1
                , size.getHeight()-2, size.getWidth()-2);
    }

    public void changeBorderStyle(ShapeBorder newBorder){
        this.border = newBorder;
    }

    public void changeColor(ShapeColor newColor){
        this.color = newColor;
    }

    public void changeText(ShapeText newText){
        this.text = newText;
    }

}
