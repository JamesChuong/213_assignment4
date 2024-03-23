package Model.BoxInfo;

import Model.ShapeInfo.ShapeColor;
import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;
import java.lang.reflect.Field;

/**
 * The BoxColor interface implements the methods declared in ShapeColor.
 * It contains information about the color of the box and which color pattern
 * it has. It supports coloring the box solidly, in a checkered patten, or only
 * the upper triangular
 */
public class BoxColor implements ShapeColor {

    private final String background;
    private Color backgroundColor;

    public BoxColor(String background, String backgroundColor){
        this.background = background;
        this.backgroundColor = parseColorName(backgroundColor);
    }

    @Override
    public void drawColor(Canvas canvas, int startX, int startY, int width, int height) {
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

    //Given a starting point, and the length of the line and how many lines down from the starting point,
    //color the line
    private void colorLine(Canvas canvas, int startX, int startY, int length, int offSet, int currentLine){
        for(int k = offSet; k < length; k++){
            canvas.setCellColor(startX + k, startY + currentLine , backgroundColor);
        }
    }

    //Color each line horizontally from top to bottom
    private void colorSolid(Canvas canvas, int startX, int startY, int width, int height) {
        for(int i = 0; i < height; i++){
            colorLine(canvas, startX, startY, width, 0, i);
        }
    }

    //Color in a checkered style, where on every line, starting from top to bottom, skip every next
    //cell from the last cell colored. The first cell colored is alternated between the first
    //and second cell on a line depending on the ith line being drawn
    private void colorCheckers(Canvas canvas, int startX, int startY, int width, int height){
        for(int i = 0; i < height; i++){
            for(int k = (i%2); k < width; k+=2){
                canvas.setCellColor(startX+k, startY + i, backgroundColor);
            }
        }
    }

    //Color in an upper triangular pattern, where for each line, starting from top to bottom,
    //start coloring on the (n-i)th cell on the ith line of length n
    private void colorTriangle(Canvas canvas, int startX, int startY, int width, int height){
        for(int i = 0; i < height; i++){
            colorLine(canvas, startX, startY, width, i, i);
        }
    }
}
