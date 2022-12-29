package kolpakovee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //путь к корневой директории
        String rootFolderPath;
        Scanner scanner = new Scanner(System.in);

        //считываем путь к корневой папке
        rootFolderPath = scanner.next();

        //создаём объект класса FileIO
        FileIO fileIO = new FileIO(rootFolderPath);

        //создаём объект класса Graph
        Graph graph = new Graph();

        //читаем директорию
        fileIO.readDirectory(fileIO.getRootFolder(), graph);

        //получаем сортированный список вершин
        ArrayList<Vertex> sortedGraph = graph.getSortedGraph();

        //печатаем отсортированный список вершин
        if (sortedGraph != null) {
            System.out.println("Отсортированный список файлов:");
            for (Vertex vertex : sortedGraph) {
                System.out.println(vertex.getFile().getName());
            }
        }

        // получаем строку отсортированной содержимое файлов
        String sortedFileData = graph.sortedFileData();

        System.out.println("Отсортированные данные файлов:");
        System.out.print(sortedFileData);
    }
}