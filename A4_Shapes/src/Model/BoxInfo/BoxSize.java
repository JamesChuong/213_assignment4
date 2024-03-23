package Model.BoxInfo;

/**
 * The BoxSize interface contain the width and height of a box. Since these attributes are fixed,
 * there is no need for it to be a separate interface.
 */
public class BoxSize {

    private int height;
    private int width;

    public BoxSize(int height, int width){
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
