package Model.ShapeInfo;

import ca.cmpt213.as4.UI.Canvas;

public abstract class ShapePosition {

    private int top;
    private int left;

    public int getTop(){
        return top;
    }

    public int getLeft(){
        return left;
    }

    public void setTop() { this.top = top; }

    public void setLeft() { this.left = left; }

    public void placeBox(Canvas canvas) {}

}
