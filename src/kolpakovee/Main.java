package kolpakovee;

import java.io.IOException;
import java.util.Scanner;

// /Users/macbookair/Desktop/testFolder

public class Main {
    public static void main(String[] args) throws IOException {
        String rootFolderPath;
        Scanner scanner = new Scanner(System.in);
        rootFolderPath = scanner.next();

        FileIO fileIO = new FileIO(rootFolderPath);

        Graph graph = new Graph();

        fileIO.readDirectory(fileIO.getRootFolder(), graph);

        String sortedGraph = graph.getSortedGraph();

        System.out.print(sortedGraph);
    }
}