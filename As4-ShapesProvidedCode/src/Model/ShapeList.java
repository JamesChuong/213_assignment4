package Model;

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

public class ShapeList implements Iterable<DrawableShape>{

    private List<DrawableShape> shapes = new ArrayList<>();

    @Override
    public Iterator<DrawableShape> iterator() {
        return shapes.iterator();
    }

    public static ShapeList readFromJSON(File JSONFile){
        ShapeList newShapeList = new ShapeList();
        try{
            JsonElement JSONfile = JsonParser.parseReader( new FileReader(JSONFile) );
            JsonObject currentObject = JSONfile.getAsJsonObject();
            JsonArray jsonlistOfShapes = currentObject.getAsJsonArray("shapes");
            for (JsonElement currentShape: jsonlistOfShapes){
                JsonObject jsonShapeObject = currentShape.getAsJsonObject();
                newShapeList.shapes.add( new BoxShape(jsonShapeObject.get("top").getAsInt()
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
        return newShapeList;
    }
}
