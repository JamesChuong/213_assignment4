package Model.ShapeInfo;

import Model.BoxBorder;
import Model.BoxColor;
import Model.BoxText;
import ca.cmpt213.as4.UI.DrawableShape;

import java.io.File;
import java.util.Iterator;

public interface ShapeList {

    void readFromJSON(File JSONFile);

    Iterator<? extends DrawableShape> getShapeIterator();

    void ChangeShapeAttributes(ShapeBorder newBorder, ShapeColor newColor, ShapeText newText);
}
