package Model.ShapeInfo;

import ca.cmpt213.as4.UI.DrawableShape;

import java.io.File;
import java.util.Iterator;

/**
 * The ShapeList Interface contains methods which is needed for the model, such as reading from a JSON file,
 * returning an iterator to a list of shapes, and changing the attributes of each shape. By having these methods
 * as part of an interface, it allows the model to have a list of various shapes and still have it work with
 * the rest of the application
 */
public interface ShapeList {

    void readFromJSON(File JSONFile);

    Iterator<? extends DrawableShape> getShapeIterator();

    void ChangeShapeAttributes(ShapeBorder newBorder, ShapeColor newColor, ShapeText newText);
}
