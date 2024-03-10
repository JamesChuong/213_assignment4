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

        }
    }

    private void setCharBorder(Canvas canvas, int startX, int startY, int width, int height){
        setCharBorderLine(canvas, startX, startY, width, lineChar, true);
        setCharBorderLine(canvas, startX + width, startY-height, height, lineChar, false);
        setCharBorderLine(canvas, startX, startY-height, width, lineChar, true);
        setCharBorderLine(canvas, startX, startY-height, height, lineChar, false);
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

    private void setSequenceBorderLine(Canvas canvas, int startX, int startY, int length, int sequence, boolean isHorizontal){
        for(int i = 0; i < length; i++){
            sequence++;
            if(isHorizontal){
                canvas.setCellText(startX+i, startY, Character.forDigit(sequence%5+1, 10));
            } else {
                canvas.setCellText(startX, startY+i, Character.forDigit(sequence%5+1, 10));
            }
        }
    }

    private void setSequenceBorder(Canvas canvas, int startX, int startY, int width, int height){
        int borderSequence = 0;

    }

    @Override
    public void setBorderStyle(String line, char lineChar) {
        this.line = line;
        this.lineChar = lineChar;
    }

}
