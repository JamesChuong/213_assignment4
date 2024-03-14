package Model;

import Model.ShapeInfo.ShapeBorder;
import Model.ShapeInfo.ShapeColor;
import Model.ShapeInfo.ShapeList;
import Model.ShapeInfo.ShapeText;
import ca.cmpt213.as4.ShapeModel;
import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.DrawableShape;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

public class ModelManager implements ShapeModel {

    private ShapeList listOfBoxes = new BoxList();

    public ModelManager(){}

    @Override
    public void populateFromJSON(File jsonFile) {
        listOfBoxes.readFromJSON(jsonFile);
    }

    @Override
    public void redact() {
        listOfBoxes.ChangeShapeAttributes(getRedactedBorder(),getRedactedColor(),getRedactedText());
    }

    private ShapeBorder getRedactedBorder() {
        return new ShapeBorder(){
            private void setCharBorderLine(Canvas canvas, int startX, int startY, int length, boolean isHorizontal){
                for(int i = 0; i < length; i++){
                    if(isHorizontal){
                        canvas.setCellText(startX+i, startY, '+');
                    } else {
                        canvas.setCellText(startX, startY+i, '+');
                    }
                }
            }
            @Override
            public void drawBorder(Canvas canvas, int startX, int startY, int width, int height) {
                boolean drawHorizontal = true;
                setCharBorderLine(canvas, startX, startY, width, drawHorizontal);
                setCharBorderLine(canvas, startX + width-1, startY, height, !drawHorizontal);
                setCharBorderLine(canvas, startX, startY+height-1, width, drawHorizontal);
                setCharBorderLine(canvas, startX, startY, height, !drawHorizontal);
            }
        };
    }

    private ShapeColor getRedactedColor(){
        return (canvas, startX, startY, width, height) -> {
            Color redactedColor;
            try {
                Field field = Class.forName("java.awt.Color").getField("lightGray");
                redactedColor = (Color)field.get(null);
            } catch (Exception e) {
                redactedColor = null; // Not defined
            }
            for(int i = 0; i < width; i++){
                for(int k = 0; k < height; k++){
                    canvas.setCellColor(startX+i, startY+k, redactedColor);
                }
            }
        };
    }

    private ShapeText getRedactedText(){
        return (canvas, top, left, verticalSpace, horizontalSpace) -> {
            for(int currentLine = top; currentLine < top+verticalSpace; currentLine++){
                for(int i = 0; i < horizontalSpace; i++){
                    canvas.setCellText(left+i, currentLine, 'X');
                }
            }
        };
    }

    @Override
    public Iterator<? extends DrawableShape> iterator() {
        return listOfBoxes.getShapeIterator();
    }

}
