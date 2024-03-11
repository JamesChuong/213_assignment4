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

        }
    }

    private void setCharBorder(Canvas canvas, int startX, int startY, int width, int height){
        boolean drawHorizontal = true;
        setCharBorderLine(canvas, startX, startY, width, lineChar, drawHorizontal);
        setCharBorderLine(canvas, startX + width, startY-height, height, lineChar, !drawHorizontal);
        setCharBorderLine(canvas, startX, startY-height, width, lineChar, drawHorizontal);
        setCharBorderLine(canvas, startX, startY-height, height, lineChar, !drawHorizontal);
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

    private void setAsciiBorder(Canvas canvas, int startX, int startY, int width, int height){
        boolean drawHorizontal = true;
        if(height == 1){
            setCharBorderLine(canvas, startX, startY, width, '■', drawHorizontal);
        } else if (width == 1){
            setCharBorderLine(canvas, startX, startY, height, '■', !drawHorizontal);
        }
        canvas.setCellText(startX, startY, '╔');
        canvas.setCellText(startX, startY-height, '╚');
        canvas.setCellText(startX+width, startY, '╗');
        canvas.setCellText(startX+width, startY-height, '╝');
        setCharBorderLine(canvas, startX+1, startY, width-2, '═', drawHorizontal);

    }

    private void setSequenceBorder(Canvas canvas, int startX, int startY, int width, int height){
        int borderSequence = 0;
        for(int i = 0; i < width; i++){
            borderSequence++;
            canvas.setCellText(startX+i, startY, Character.forDigit(borderSequence%5+1, 10));
        }
        for(int i = 1; i <= height-1; i++){
            borderSequence++;
            canvas.setCellText(startX+width, startY-i, Character.forDigit(borderSequence%5+1, 10));
        }
        for(int i = width-1; i >= 0; i--){
            borderSequence++;
            canvas.setCellText(startX+width-i-1, startY-height, Character.forDigit(borderSequence%5+1, 10));
        }
        for(int i = 1; i < height-2; i++){
            borderSequence++;
            canvas.setCellText(startX, startY-height+i+1, Character.forDigit(borderSequence%5+1, 10));
        }
    }

    @Override
    public void setBorderStyle(String line, char lineChar) {
        this.line = line;
        this.lineChar = lineChar;
    }

}
