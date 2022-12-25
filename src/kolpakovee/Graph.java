package kolpakovee;

import java.util.ArrayList;

public class Graph {
    /**
     * Поле, которое хранит отсортированные вершины графа, если
     * вызывался метод topologicalSort и граф не содержит циклов.
     * В противном случае это поле имеет значение null.
     */
    private ArrayList<Vertex> sortedGraph;
    /**
     * Массив вершин, который заполняется на этапе чтения текстовых файлов.
     * С ним работают почти все методы, когда определяют Vertex по пути до файла.
     */
    private final ArrayList<Vertex> fileGraph;
    /**
     * Поле, которое отвечает на вопрос, цикличный граф или нет.
     * По умолчанию имеет значение false, но если вызвать метод проверки на цикличность,
     * то может поменять своё значение.
     */
    boolean isCyclic = false;

    public Graph() {
        fileGraph = new ArrayList<>();
    }

    void addVertex(Vertex vertex) {
        fileGraph.add(vertex);
    }

    public String getSortedGraph() {
        topologicalSort();
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : sortedGraph) {
            sb.append(vertex.data);
        }
        return sb.toString();
    }

    /**
     * Метод
     */
    private void topologicalSort() {
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

    private void topologicalSort(String path) {
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

    private Vertex getVertexByPath(String path) {
        for (Vertex vertex : fileGraph) {
            if (path.equals(vertex.file.getAbsolutePath())) {
                return vertex;
            }
        }
        return null;
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
