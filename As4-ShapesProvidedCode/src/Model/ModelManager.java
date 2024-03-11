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
                setCharBorderLine(canvas, startX, startY, width, true);
                setCharBorderLine(canvas, startX+width,startY-height, height, false);
                setCharBorderLine(canvas, startX, startY-height, width, true);
                setCharBorderLine(canvas, startX, startY-height, height, false);
            }
        };
    }

    private ShapeColor getRedactedColor(){
        return (canvas, startX, startY, width, height) -> {
            for(int i = 0; i < width; i++){
                for(int k = 0; k < height; k++){
                    canvas.setCellColor(startX+i, startY+k, Color.getColor("light gray"));
                }
            }
        };
    }

    private ShapeText getRedactedText(){
        return (canvas, top, left, verticalSpace, horizontalSpace) -> {
            for(int currentLine = top; currentLine >= top-verticalSpace; currentLine--){
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
