package kolpakovee;

import java.io.File;
import java.util.ArrayList;

/**
 * Класс - вершина графа
 */
public class Vertex {
    /**
     * Путь к файлу, который хранит данная вершина.
     */
    private final File file;
    /**
     * Содержимое файла.
     */
    private String data;
    /**
     * Массив путей к соседним вершинам.
     */
    private final ArrayList<String> neighboringVertices;
    /**
     * Цвет вершины. Нужен для проверки на цикличность.
     */
    private Colors color = Colors.WHITE;
    /**
     * Посещаемость вершины. Нужна для топологичской сортировки.
     */
    private boolean isVisited = false;

    /**
     * Конструктор с одним пераметром.
     * @param file - основной файл.
     */
    Vertex(File file) {
        this.file = file;
        neighboringVertices = new ArrayList<>();
    }

    public File getFile() {
        return file;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ArrayList<String> getNeighboringVertices() {
        return neighboringVertices;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public boolean getAttendance() {
        return isVisited;
    }

    public void setAttendance(boolean b) {
        isVisited = b;
    }
}
