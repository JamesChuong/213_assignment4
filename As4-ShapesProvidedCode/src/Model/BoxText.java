package Model;

import Model.ShapeInfo.ShapeText;
import ca.cmpt213.as4.UI.Canvas;

public class BoxText implements ShapeText {

    private String fill;
    private String fillText;

    public BoxText(String fill, String fillText){
        this.fill = fill;
        this.fillText = fillText.trim();
    }

    @Override
    public void placeText(Canvas canvas, int top, int left, int verticalSpace, int horizontalSpace){
        switch (fill){
            case "wrapper":
                placeWrapperText(canvas, top, left, verticalSpace, horizontalSpace);
                break;
            case "solid":
        }
    }

    private void placeWrapperText(Canvas canvas, int top, int left, int verticalSpace, int horizontalSpace) {
        String currentText = fillText;
        for(int currentLine = 0; currentLine < verticalSpace; currentLine++){
            if(currentText.length() <= horizontalSpace){
                alignAndPlaceText(canvas, currentLine, left, horizontalSpace, currentText);
                break;
            }
            int indexLastSpace = -1;
            for(int currentCharIndex = 0; currentCharIndex < currentText.length(); currentCharIndex++){
                boolean isWithinSpace = currentCharIndex >= horizontalSpace;
                if(!isWithinSpace && indexLastSpace != -1){
                    alignAndPlaceText(canvas, currentLine, left
                            , horizontalSpace, currentText.substring(0, indexLastSpace+1));
                    currentText = currentText.substring(indexLastSpace);
                    break;
                } else if (!isWithinSpace && indexLastSpace == -1){
                    alignAndPlaceText(canvas, currentLine, left
                            , horizontalSpace, currentText.substring(0, horizontalSpace));
                    currentText = currentText.substring(horizontalSpace);
                    break;
                }
                if(currentText.charAt(currentCharIndex) == ' '){
                    indexLastSpace = currentCharIndex;
                }
            }
        }
    }

    private void alignAndPlaceText(Canvas canvas, int top, int left, int width, String fillText){
        int spaceBetweenTextAndBorder = (width-fillText.length())/2;
        for(int currentIndex = 0; currentIndex < fillText.length(); currentIndex++){
            canvas.setCellText(left+spaceBetweenTextAndBorder+currentIndex+1, top, fillText.charAt(currentIndex));
        }
    }

}
