package Model;

import Model.ShapeInfo.ShapeBorder;
import ca.cmpt213.as4.UI.Canvas;

public class BoxBorder implements ShapeBorder {

    private String line;
    private char lineChar;

    public BoxBorder(String line, char lineChar){
        this.line = line;
        this.lineChar = lineChar;
    }

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

    private void setCharBorder(Canvas canvas, int startX, int startY, int width, int height){
        boolean drawHorizontal = true;
        setCharBorderLine(canvas, startX, startY, width, lineChar, drawHorizontal);
        setCharBorderLine(canvas, startX + width-1, startY, height, lineChar, !drawHorizontal);
        setCharBorderLine(canvas, startX, startY+height-1, width, lineChar, drawHorizontal);
        setCharBorderLine(canvas, startX, startY, height, lineChar, !drawHorizontal);
    }

    private void setAsciiBorder(Canvas canvas, int startX, int startY, int width, int height){
        boolean drawHorizontal = true;
        if(height == 1){
            setCharBorderLine(canvas, startX, startY, width, '■', drawHorizontal);
        } else if (width == 1){
            setCharBorderLine(canvas, startX, startY, height, '■', !drawHorizontal);
        } else {
            canvas.setCellText(startX, startY, '╔');
            canvas.setCellText(startX, startY+height-1, '╚');
            canvas.setCellText(startX+width-1, startY, '╗');
            canvas.setCellText(startX+width-1, startY+height-1, '╝');
            setCharBorderLine(canvas, startX+1, startY, width-2, '═', drawHorizontal);
            setCharBorderLine(canvas, startX+width-1, startY+1, height-2, '║', !drawHorizontal);
            setCharBorderLine(canvas, startX+1, startY+height-1, width-2, '═', drawHorizontal);
            setCharBorderLine(canvas, startX, startY+1, height-2, '║', !drawHorizontal);
        }
    }

    private void setCharBorderLine(Canvas canvas, int startX, int startY, int length, char border, boolean isHorizontal){
        for(int i = 0; i < length; i++){
            if(isHorizontal){
                canvas.setCellText(startX+i, startY, border);
            } else {
                canvas.setCellText(startX, startY+i, border);
            }
        }
    }

    private void setSequenceBorder(Canvas canvas, int startX, int startY, int width, int height){
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
