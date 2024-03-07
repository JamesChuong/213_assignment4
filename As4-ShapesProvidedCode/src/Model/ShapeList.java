package Model;

import ca.cmpt213.as4.UI.DrawableShape;

import java.util.Iterator;
import java.util.List;

public class ShapeList implements Iterable<DrawableShape>{

    private List<DrawableShape> shapes;


    @Override
    public Iterator<DrawableShape> iterator() {
        return shapes.iterator();
    }
}
