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
     * Список вершин, который заполняется на этапе чтения текстовых файлов.
     * С ним работают почти все методы, когда определяют Vertex по пути до файла.
     */
    private final ArrayList<Vertex> fileGraph;
    /**
     * Поле, которое отвечает на вопрос, цикличный граф или нет.
     * По умолчанию имеет значение false, но если вызвать метод проверки на цикличность,
     * то может поменять своё значение.
     */
    private boolean isCyclic = false;

    /**
     * Конструктор, который инициализирует список fileGraph.
     */
    public Graph() {
        fileGraph = new ArrayList<>();
    }

    /**
     * Добавление вершины в граф.
     *
     * @param vertex вершина.
     */
    void addVertex(Vertex vertex) {
        fileGraph.add(vertex);
    }

    /**
     * Метод, который вызывает сортировку графа и возвращает строку.
     *
     * @return содержимое файлов в нужном порядке.
     */
    public String getSortedGraph() {
        topologicalSort();
        StringBuilder sb = new StringBuilder();
        if (sortedGraph == null) {
            return "Найдена циклическая зависимость";
        }
        for (Vertex vertex : sortedGraph) {
            sb.append(vertex.getData());
        }
        return sb.toString();
    }

    /**
     * Метод,который проверет граф на цикличность и вызывает топологическую сортровку.
     */
    private void topologicalSort() {
        if (isCyclic()) {
            return;
        }
        sortedGraph = new ArrayList<>();
        for (Vertex vertex : fileGraph) {
            if (!vertex.getAttendance()) {
                topologicalSort(vertex.getFile().getAbsolutePath());
            }
        }
    }

    /**
     * Метод, выполняющий топологическую сортировку.
     *
     * @param path путь к вершине, относительно которой выполнятеся топологическая сортировка.
     */
    private void topologicalSort(String path) {
        // получаем вершину
        Vertex inputVertex = getVertexByPath(path);
        // проверяем, на null
        if (inputVertex == null) {
            return;
        }
        // отмечаем, что уже посещали вершину
        inputVertex.setAttendance(true);

        for (String str : inputVertex.getNeighboringVertices()) {
            Vertex vertex = getVertexByPath(str);
            assert vertex != null;
            if (vertex.getAttendance()) {
                continue;
            }
            topologicalSort(str);
        }

        sortedGraph.add(inputVertex);
    }

    // Сделать вход с вершины в которую не идут стрелки

    /**
     * Метод, проверяющий на граф на цикличность.
     *
     * @return true, если граф содержит циклы, иначе false.
     */
    public boolean isCyclic() {
        isCyclic(fileGraph.get(0).getFile().getAbsolutePath());
        return isCyclic;
    }

    /**
     * проверяет, есть ли вершна в графе.
     *
     * @param path путь к вершине.
     * @return вершина, если она есть в графе, иначе null
     */
    private Vertex getVertexByPath(String path) {
        for (Vertex vertex : fileGraph) {
            if (path.equals(vertex.getFile().getAbsolutePath())) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * Метод для провери графа на цикличнсть.
     *
     * @param path путь к вершине, с которой начинаем проверку.
     */
    public void isCyclic(String path) {
        // получаем вершину
        Vertex inputVertex = getVertexByPath(path);

        // если такой вершины нет, завершаем метод
        if (inputVertex == null) {
            return;
        }

        // красим вершину в серый цвет
        inputVertex.setColor(Colors.GREY);

        for (String str : inputVertex.getNeighboringVertices()) {
            Vertex vertex = getVertexByPath(str);
            assert vertex != null;
            if (vertex.getColor() == Colors.GREY) {
                isCyclic = true;
                return;
            }
            if (vertex.getColor() == Colors.WHITE) {
                isCyclic(str);
            }
        }

        inputVertex.setColor(Colors.BLACK);
    }
}
