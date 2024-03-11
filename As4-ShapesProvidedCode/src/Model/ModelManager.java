package Model;

import Model.ShapeInfo.ShapeList;
import ca.cmpt213.as4.ShapeModel;
import ca.cmpt213.as4.UI.DrawableShape;

import java.io.File;
import java.util.Iterator;

public class ModelManager implements ShapeModel {

    private ShapeList listOfBoxes = new BoxList();

    public ModelManager(){}

    @Override
    public void populateFromJSON(File jsonFile) {
        listOfBoxes.readFromJSON(jsonFile);
    }

    @Override
    public void redact() {
        while(listOfBoxes.getShapeIterator().hasNext()){
            listOfBoxes.getShapeIterator()
                    .next();

        }
    }

    @Override
    public Iterator<? extends DrawableShape> iterator() {
        return listOfBoxes.getShapeIterator();
    }

}
