package Model.BoxInfo;

import Model.ShapeInfo.ShapeText;
import ca.cmpt213.as4.UI.Canvas;

/**
 * The BoxText class implements the methods declared in the ShapeText interface. It supports
 * operations for placing specific types of text on the box, such as actual text, or a single character
 * to fill the entire box
 */
public class BoxText implements ShapeText {

    private String fill;
    private String fillText;

    public BoxText(String fill, String fillText){
        this.fill = fill;
        this.fillText = fillText;
    }

    @Override
    public void placeText(Canvas canvas, int top, int left, int verticalSpace, int horizontalSpace){
        switch (fill){
            case "wrapper":
                placeWrapperText(canvas, left, top, verticalSpace, horizontalSpace);
                break;
            case "solid":
                placeSolidText(canvas, left, top, verticalSpace, horizontalSpace);
                break;
            default:
                throw new RuntimeException("ERROR: Invalid fill option in JSON file");
        }
    }

    private void placeSolidText(Canvas canvas, int left, int top, int verticalSpace, int horizontalSpace){
        char fillCharacter = fillText.charAt(0);    //First character of the string
        //From top to bottom, fill each line with the fill character
        for(int currentLine = top; currentLine < top+verticalSpace; currentLine++){
            for(int i = 0; i < horizontalSpace; i++){
                canvas.setCellText(left+i, currentLine, fillCharacter);
            }
        }
    }

    private void placeWrapperText(Canvas canvas, int left, int top, int verticalSpace, int horizontalSpace) {
        String currentText = fillText.trim();   //Remove all space aroun the text
        for(int currentLine = top; currentLine < top+verticalSpace; currentLine++){
            //If there is enough space on the current line to place the entire text or what is left, then place it
            //and we're done
            if(currentText.length() <= horizontalSpace){
                alignAndPlaceText(canvas, currentLine, left, horizontalSpace, currentText);
                break;
            }
            int indexLastSpace = -1;    //The position of the last space to break the text on (default value is -1)
            for(int currentCharIndex = 0; currentCharIndex < currentText.length(); currentCharIndex++){
                boolean isWithinSpace = currentCharIndex < horizontalSpace-1;
                if(currentText.charAt(currentCharIndex) == ' '){
                    indexLastSpace = currentCharIndex;
                }
                //True if the entire line until the last space can be placed on the line, false if not
                boolean breakOnNextSpace = currentCharIndex+1 < fillText.length()
                        && fillText.charAt(currentCharIndex+1) == ' ';
                if(!isWithinSpace && indexLastSpace != -1 && !breakOnNextSpace){
                    alignAndPlaceText(canvas, currentLine, left
                            , horizontalSpace, currentText.substring(0, indexLastSpace));
                    //Remove the portion of the text that has been placed
                    currentText = currentText.substring(indexLastSpace).trim();
                    break;
                } else if (!isWithinSpace && indexLastSpace == -1){
                    alignAndPlaceText(canvas, currentLine, left
                            , horizontalSpace, currentText.substring(0, horizontalSpace));
                    currentText = currentText.substring(horizontalSpace).trim();
                    break;
                }

            }
        }
    }

    //Given a line of text, place it such that it aligns in the middle of the line
    private void alignAndPlaceText(Canvas canvas, int top, int left, int width, String fillText){
        int spaceBetweenTextAndBorder = (width-fillText.length())/2;
        for(int currentIndex = 0; currentIndex < fillText.length(); currentIndex++){
            canvas.setCellText(left+spaceBetweenTextAndBorder+currentIndex, top, fillText.charAt(currentIndex));
        }
    }

}
