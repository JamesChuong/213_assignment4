package Model;

import Model.ShapeInfo.ShapeText;
import ca.cmpt213.as4.UI.Canvas;

public class BoxText implements ShapeText {

    private String fill;
    private String fillText;

    public BoxText(String fill, String fillText){
        this.fill = fill;
        this.fillText = fillText;
    }
    @Override
    public void placeText(Canvas canvas, int top, int left, int height, int width) {

    }
}
