package Model;

import Model.ShapeInfo.ShapeList;
import ca.cmpt213.as4.UI.DrawableShape;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoxList implements ShapeList {

    private List<BoxShape> shapes = new ArrayList<>();

    @Override
    public Iterator<? extends DrawableShape> getShapeIterator() {
        return shapes.iterator();
    }

    //Replace current list of shapes with those in a JSON file
    @Override
    public void readFromJSON(File JSONFile){
        List<BoxShape> newShapeList = new ArrayList<>();
        try{
            JsonElement JSONfile = JsonParser.parseReader( new FileReader(JSONFile) );
            JsonObject currentObject = JSONfile.getAsJsonObject();
            JsonArray jsonlistOfShapes = currentObject.getAsJsonArray("shapes");
            for (JsonElement currentShape: jsonlistOfShapes){
                JsonObject jsonShapeObject = currentShape.getAsJsonObject();
                newShapeList.add( new Model.BoxShape(jsonShapeObject.get("top").getAsInt()
                        , jsonShapeObject.get("left").getAsInt(), jsonShapeObject.get("width").getAsInt()
                        , jsonShapeObject.get("height").getAsInt(), jsonShapeObject.get("background").getAsString()
                        , jsonShapeObject.get("backgroundColor").getAsString()
                        , jsonShapeObject.get("line").getAsString(), jsonShapeObject.get("lineChar").getAsCharacter()
                        , jsonShapeObject.get("fill").getAsString(), jsonShapeObject.get("fillText").getAsString())
                );
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        this.shapes = newShapeList;
    }

    @Override
    public void ChangeShapeAttributes() {

    }
}
