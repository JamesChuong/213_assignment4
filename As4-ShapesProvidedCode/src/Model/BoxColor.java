package Model;

import Model.ShapeInfo.ShapeColor;
import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;

public class BoxColor implements ShapeColor {

    private String background;
    private Color backgroundColor;

    public BoxColor(String background, String backgroundColor){
        this.background = background;
        this.backgroundColor = Color.getColor(backgroundColor);
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
            canvas.setCellColor(startX + k, startY + currentLine , backgroundColor);
        }
    }

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
