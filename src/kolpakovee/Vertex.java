package kolpakovee;

import java.io.File;
import java.util.ArrayList;

public class Vertex {
    File file;
    String data;
    ArrayList<String> neighboringVertices;
    Colors color = Colors.WHITE;
    boolean isVisited = false;

    Vertex(File file){
        this.file = file;
        neighboringVertices = new ArrayList<>();
    }
}
