package Model;

import ca.cmpt213.as4.UI.Canvas;

public class BoxPosition {

    private int top;
    private int left;

    public BoxPosition(int top, int left){
        this.top = top;
        this.left = left;
    }

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
