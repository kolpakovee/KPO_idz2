package kolpakovee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс, предназначенный для чтения/записи в текстовые файлы и работы с директориями.
 */
public class FileIO {
    /**
     * Полный путь к корневой папке
     */
    private final File rootFolder;

    /**
     * Конструктор класса. Инициализирует rootFolder.
     *
     * @param path пусть к корневой папке.
     */
    FileIO(String path) {
        rootFolder = new File(path);
    }

    /**
     * Метод для инкапсуляции rootFolder
     *
     * @return полный путь к корневой папке
     */
    public File getRootFolder() {
        return rootFolder;
    }

    /**
     * Метод для чтения текстовых файлов и папок.
     * Если файл имеет расширение .txt, то создаётся новая вершина в графе.
     *
     * @param root корневая папка, в которой читаются все файлы.
     * @throws IOException возвращает ошибку из методы readFile.
     */
    public void readDirectory(File root, Graph graph) throws IOException {
        for (File file : Objects.requireNonNull(root.listFiles())) {
            if (file.isDirectory()) {
                readDirectory(file, graph);
            } else if (file.getName().toLowerCase().endsWith(".txt")) {
                Vertex vertex = readFile(file.getAbsolutePath());
                graph.addVertex(vertex);
            }
        }
    }

    /**
     * Метод, предназначенный для чтения текстового файла. По данным,
     * полученным из него, создаёт вершину в графе.
     *
     * @param path полный путь к текстовому файлу.
     * @return вершину для графа.
     * @throws IOException выбрасывает исключени, если произошёл сбой
     *                     при чтении фалйа.
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
                vertex.getNeighboringVertices().add(absolutePath);
            }
        }

        vertex.setData(sb.toString());

        return vertex;
    }
}
