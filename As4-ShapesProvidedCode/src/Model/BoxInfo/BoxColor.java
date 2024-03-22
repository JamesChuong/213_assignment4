package Model.BoxInfo;

import Model.ShapeInfo.ShapeColor;
import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;
import java.lang.reflect.Field;

public class BoxColor implements ShapeColor {

    private final String background;
    private Color backgroundColor;

    public BoxColor(String background, String backgroundColor){
        this.background = background;
        this.backgroundColor = parseColorName(backgroundColor);
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

    //Converts a string with the name of a color to an actual color object
    private Color parseColorName(String backgroundColor){
        String colorName = backgroundColor.toLowerCase();
        //If there is a space between words (e.g, 'light gray'), then remove the space,
        //capitalize the second half of the word, and join them together to form a single word (e.g, 'lightGray')
        for(int currentCharacter = 0; currentCharacter < colorName.length(); currentCharacter++){
            if(colorName.charAt(currentCharacter) == ' '){
                String firstCharOfSecondHalf = colorName.substring(currentCharacter+1,currentCharacter+2).toUpperCase();
                String secondHalf = colorName.substring(currentCharacter+2);
                String firstHalf = colorName.substring(0, currentCharacter);
                colorName = firstHalf + firstCharOfSecondHalf + secondHalf;
            }
        }
        //Code borrowed from https://stackoverflow.com/questions/2854043/converting-a-string-to-color-in-java
        try {
            Field field = Class.forName("java.awt.Color").getField(colorName);
            return  (Color)field.get(null);
        } catch (Exception e) {
            return null; // Not defined
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
