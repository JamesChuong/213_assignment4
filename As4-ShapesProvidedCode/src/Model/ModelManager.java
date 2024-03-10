package Model;

import ca.cmpt213.as4.ShapeModel;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelManager implements ShapeModel {

    private ShapeList listOfBoxes;

    public ModelManager(){}

    @Override
    public void populateFromJSON(File jsonFile) {
        listOfBoxes = ShapeList.readFromJSON(jsonFile);
    }

    @Override
    public void redact() {

    }

    @Override
    public Iterator<? extends DrawableShape> iterator() {
        return listOfBoxes.iterator();
    }

}
