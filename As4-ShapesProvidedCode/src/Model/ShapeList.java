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

    public void readFromJSON(File JSONFile){
        try{
            JsonElement JSONfile = JsonParser.parseReader( new FileReader(JSONFile) );
            JsonObject currentObject = JSONfile.getAsJsonObject();
            JsonArray jsonlistOfShapes = currentObject.getAsJsonArray("shapes");
            for (JsonElement currentShape: jsonlistOfShapes){
                JsonObject jsonShapeObject = currentShape.getAsJsonObject();
                shapes.add( new BoxShape() );
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
