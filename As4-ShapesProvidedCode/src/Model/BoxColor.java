package Model;

import Model.ShapeInfo.ShapeColor;
import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;
import java.lang.reflect.Field;

public class BoxColor implements ShapeColor {

    private final String background;
    private Color backgroundColor;

    public BoxColor(String background, String backgroundColor){
        this.background = background;
        try {
            Field field = Class.forName("java.awt.Color").getField(backgroundColor);
            this.backgroundColor = (Color)field.get(null);
        } catch (Exception e) {
            this.backgroundColor = null; // Not defined
        }
        //System.out.println(this.backgroundColor.toString());
    }

    @Override
    public void placeColor(Canvas canvas, int startX, int startY, int width, int height) {
        if(background.equals("solid")){
            colorSolid(canvas, startX, startY, width, height);
        } else if (background.equals("checker")){
            colorCheckers(canvas, startX, startY, width, height);
        } else if (background.equals("triangle")){
            colorTriangle(canvas, startX, startY, width, height);
        }
    }

    private void colorLine(Canvas canvas, int startX, int startY, int width, int offSet, int currentLine){
        for(int k = offSet; k < width; k++){
            if(backgroundColor == null){
                System.out.println("No color!!!");
            }
            canvas.setCellColor(startX + k, startY + currentLine , backgroundColor);
        }
    }

    //Color each line horizontally
    private void colorSolid(Canvas canvas, int startX, int startY, int width, int height) {
        for(int i = 0; i < height; i++){
            colorLine(canvas, startX, startY, width, 0, i);
        }
    }

    private void colorCheckers(Canvas canvas, int startX, int startY, int width, int height){
        for(int i = 0; i < height; i++){
            for(int k = (i%2); k < width; k+=2){
                canvas.setCellColor(startX+k, startY + i, backgroundColor);
            }
        }
    }

    private void colorTriangle(Canvas canvas, int startX, int startY, int width, int height){
        for(int i = 0; i < height; i++){
            colorLine(canvas, startX, startY, width, i, i);
        }
    }
}
