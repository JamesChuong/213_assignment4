package Model.BoxInfo;

import ca.cmpt213.as4.UI.Canvas;

/**
 * The BoxShape class contains the starting position of a box, since the position is always fixed,
 * there is no need for it to be an interface, but for consistency, it is its own class
 */
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

}
