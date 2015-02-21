package main;

import book.Book;
import book.BoundedBook;
import book.Location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main (String[] args) throws IOException {
        if (args.length > 0) {
            List<String> lines = Files.readAllLines(Paths.get(args[0]));
            char[][] out = new char[lines.size()][];
            for (int i = 0; i < lines.size(); i++)
                out[i] = lines.get(i).toCharArray();
            process(out);
            return;
        }
    }

    public static void process (char[][] p) {
        int maxY = p.length;
        int maxX = 0;
        for (int x = 0; x < p.length; x++)
            maxX = Math.max(maxX, p[x].length);
        Book b = new BoundedBook(maxX, maxY);
        for (int y = 0; y < maxY; y++)
            for (int x = 0; x < maxX; x++)
                if (x < p[y].length)
                    b.addCode(new Location(x, y), p[y][x]);
        b.run();
    }
}
