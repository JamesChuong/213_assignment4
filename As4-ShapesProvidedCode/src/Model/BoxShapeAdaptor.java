package Model;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class BoxShapeAdaptor extends TypeAdapter<BoxShape> {

    @Override
    public void write(JsonWriter jsonWriter, BoxShape boxShape) throws IOException {

    }

    @Override
    public BoxShape read(JsonReader jsonReader) throws IOException {


        return null;
    }
}

