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

        fileIO.readDirectory(fileIO.rootFolder);

        fileIO.topologicalSort();

        if (fileIO.sortedGraph == null){
            return;
        }

        System.out.print(fileIO.getStringConcatenation());
    }
}