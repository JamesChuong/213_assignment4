package Model;

import ca.cmpt213.as4.ShapeModel;
import ca.cmpt213.as4.UI.DrawableShape;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelManager implements ShapeModel {

    private ShapeList listOfBoxes = new ShapeList();

    public ModelManager(){}

    @Override
    public void populateFromJSON(File jsonFile) {

    }

    @Override
    public void redact() {

    }

    @Override
    public Iterator<? extends DrawableShape> iterator() {
        return listOfBoxes.iterator();
    }

}
