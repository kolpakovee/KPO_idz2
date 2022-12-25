package kolpakovee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Класс, предназначенный для работы с текстовыми файлами и директориями.
 */
public class FileIO {
    /**
     * Поле, которое хранит отсортированные вершины графа, если
     * вызывался метод topologicalSort и граф не содержит циклов.
     * В противном случае это поле имеет значение null.
     */
    ArrayList<Vertex> sortedGraph;
    /**
     * Массив вершин, который заполняется на этапе чтения текстовых файлов.
     * С ним работают почти все методы, когда определяют Vertex по пути до файла.
     */
    ArrayList<Vertex> fileGraph;
    /**
     * Корневая папка.
     */
    File rootFolder;
    /**
     * Поле, которое отвечает на вопрос, цикличный граф или нет.
     * По умолчанию имеет значение false, но если вызвать метод проверки на цикличность,
     * то может поменять своё значение.
     */
    boolean isCyclic = false;

    /**
     * Конструктор класса. Инициализирует fileGraph и rootFolder.
     *
     * @param path пусть к корневой папке.
     */
    FileIO(String path) {
        fileGraph = new ArrayList<Vertex>();
        rootFolder = new File(path);
    }

    /**
     * Метод для чтения текстовых файлов и папок.
     * Если файл имеет расширение .txt, то создаётся новая вершина в графе.
     *
     * @param root корневая папка, в которой читаются все файлы.
     * @throws IOException возвращает ошибку из методы readFile.
     */
    public void readDirectory(File root) throws IOException {
        for (File file : Objects.requireNonNull(root.listFiles())) {
            if (file.isDirectory()) {
                readDirectory(file);
            } else if (file.getName().toLowerCase().endsWith(".txt")) {
                Vertex vertex = readFile(file.getAbsolutePath());
                fileGraph.add(vertex);
            }
        }
    }

    /**
     * Метод, предназначенный для чтения текстового файла. По данным,
     * полученным из него, создаёт вершину в графе.
     * @param path полный путь к текстовому файлу.
     * @return вершину для графа.
     * @throws IOException выбрасывает исключени, если произошёл сбой
     * при чтении фалйа.
     */
    public Vertex readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        Vertex vertex = new Vertex(new File(path));
        BufferedReader bf = new BufferedReader(new FileReader(path));

        while ((line = bf.readLine()) != null) {
            sb.append(line);
            sb.append('\n');

            if (line.startsWith("require")) {
                String absolutePath = line.substring(9, line.length() - 1);
                vertex.neighboringVertices.add(absolutePath);
            }
        }

        vertex.data = sb.toString();
        return vertex;
    }

    /**
     * Метод
     */
    public void topologicalSort() {
        if (isCyclic()) {
            return;
        }
        sortedGraph = new ArrayList<>();
        for (Vertex vertex : fileGraph) {
            if (!vertex.isVisited) {
                topologicalSort(vertex.file.getAbsolutePath());
            }
        }
    }

    public void topologicalSort(String path) {
        // получаем вершину
        Vertex inputVertex = getVertexByPath(path);
        // проверяем, на null
        if (inputVertex == null) {
            return;
        }
        // отмечаем, что уже посещали вершину
        inputVertex.isVisited = true;

        for (String str : inputVertex.neighboringVertices) {
            Vertex vertex = getVertexByPath(str);
            if (vertex.isVisited) {
                continue;
            }
            topologicalSort(str);
        }

        sortedGraph.add(inputVertex);
    }

    // Сделать вход с вершины в которую не идут стрелки
    public boolean isCyclic() {
        isCyclic(fileGraph.get(0).file.getAbsolutePath());
        return isCyclic;
    }

    public Vertex getVertexByPath(String path) {
        for (Vertex vertex : fileGraph) {
            if (path.equals(vertex.file.getAbsolutePath())) {
                return vertex;
            }
        }
        return null;
    }

    public String getStringConcatenation() {
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : sortedGraph) {
            sb.append(vertex.data);
        }
        return sb.toString();
    }

    public void isCyclic(String path) {
        // получаем вершину
        Vertex inputVertex = getVertexByPath(path);

        // если такой вершины нет, завершаем метод
        if (inputVertex == null) {
            return;
        }

        // красим вершину в серый цвет
        inputVertex.color = Colors.GREY;


        for (String str : inputVertex.neighboringVertices) {
            Vertex vertex = getVertexByPath(str);
            if (vertex.color == Colors.GREY) {
                isCyclic = true;
                return;
            }
            if (vertex.color == Colors.WHITE) {
                isCyclic(str);
            }
        }

        inputVertex.color = Colors.BLACK;
    }
}
