package Model.BoxInfo;

import Model.ShapeInfo.ShapeBorder;
import ca.cmpt213.as4.UI.Canvas;

/**
 * The BoxBorder class implements the methods declared in the ShapeBorder interface.
 * It is responsible for containing information about the border for this specific shape,
 * like the border style and border text. It contains operations to draw different styles
 * of border, depending on its fields.
 */
public class BoxBorder implements ShapeBorder {

    private String line;
    private char lineChar;

    public BoxBorder(String line, char lineChar){
        this.line = line;
        this.lineChar = lineChar;
    }

    //Depending on the 'line' field, there are 3 border styles which can be drawn
    @Override
    public void drawBorder(Canvas canvas, int startX, int startY, int width, int height) {
        switch (line){
            case "char":
                setCharBorder(canvas, startX, startY, width, height);
                break;
            case "sequence":
                setSequenceBorder(canvas, startX, startY, width, height);
                break;
            case "ascii line":
                setAsciiBorder(canvas, startX, startY, width, height);
                break;
            default:
                throw new RuntimeException("ERROR: Invalid line value in JSON");
        }
    }

    //Draws the border following the 'char' style
    private void setCharBorder(Canvas canvas, int startX, int startY, int width, int height){
        boolean drawHorizontal = true;
        drawBorderLine(canvas, startX, startY, width, lineChar, drawHorizontal);
        drawBorderLine(canvas, startX + width-1, startY, height, lineChar, !drawHorizontal);
        drawBorderLine(canvas, startX, startY+height-1, width, lineChar, drawHorizontal);
        drawBorderLine(canvas, startX, startY, height, lineChar, !drawHorizontal);
    }

    //Draws the border following the 'ascii' style
    private void setAsciiBorder(Canvas canvas, int startX, int startY, int width, int height){
        //The direction which the border is being drawn
        boolean drawHorizontal = true;      //If true, then the border is drawn in the horizontal direction, false for vertical
        //If the height or width is 1, then the entire border consists of blocks
        if(height == 1){
            drawBorderLine(canvas, startX, startY, width, '■', drawHorizontal);
        } else if (width == 1){
            drawBorderLine(canvas, startX, startY, height, '■', !drawHorizontal);
        } else {
            //Place border texts on the corners for continuity around the box
            canvas.setCellText(startX, startY, '╔');
            canvas.setCellText(startX, startY+height-1, '╚');
            canvas.setCellText(startX+width-1, startY, '╗');
            canvas.setCellText(startX+width-1, startY+height-1, '╝');
            drawBorderLine(canvas, startX+1, startY, width-2, '═', drawHorizontal);
            drawBorderLine(canvas, startX+width-1, startY+1, height-2, '║', !drawHorizontal);
            drawBorderLine(canvas, startX+1, startY+height-1, width-2, '═', drawHorizontal);
            drawBorderLine(canvas, startX, startY+1, height-2, '║', !drawHorizontal);
        }
    }

    //A function to place a border in either a verical or horizontal line (specified by the isHorizontal parameter)
    private void drawBorderLine(Canvas canvas, int startX, int startY, int length, char border, boolean isHorizontal){
        for(int i = 0; i < length; i++){
            if(isHorizontal){
                canvas.setCellText(startX+i, startY, border);
            } else {
                canvas.setCellText(startX, startY+i, border);
            }
        }
    }

    //Draw the border in the 'sequence' style
    private void setSequenceBorder(Canvas canvas, int startX, int startY, int width, int height){
        //To maintain continuity, the current number in the sequence is kept in borderSequence
        int borderSequence = 0;
        for(int i = 0; i < width; i++){
            canvas.setCellText(startX+i, startY, Character.forDigit(borderSequence%5+1, 10));
            borderSequence++;
        }
        for(int i = 1; i <= height-1; i++){
            canvas.setCellText(startX+width-1, startY+i, Character.forDigit(borderSequence%5+1, 10));
            borderSequence++;
        }
        for(int i = 1; i < width; i++){
            canvas.setCellText(startX+width-i-1, startY+height-1, Character.forDigit(borderSequence%5+1, 10));
            borderSequence++;
        }
        for(int i = 1; i < height-1; i++){
            canvas.setCellText(startX, startY+height-i-1, Character.forDigit(borderSequence%5+1, 10));
            borderSequence++;
        }
    }

}
