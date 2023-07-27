/**
 * Advanced Programming - Assignment 2.2
 * Concrete Job Scheduler for AnagramsCounter jobs
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class AnagramsCounterScheduler extends JobScheduler<String, String>{
    private String out_file = "count_anagrams.txt";

    /**
     * Asks the user for the absolute path of a directory.
     * It visits the directory and creates a new job for each file ending with .txt.
     * @return a stream of Jobs, one for each txt file into the directory
     * @throws IllegalArgumentException if the specified directory name doesn't exist or is not a directory
     */
    @Override
    protected Stream<AJob<String, String>> emit() throws IllegalArgumentException {
        Scanner in = new Scanner(System.in);

        // get directory name and check it is well-defined
        System.out.println("Absolute path of the directory:");
        String dir_name = in.nextLine();
        Path dir = Paths.get(dir_name);
        if (Files.exists(dir) && Files.isDirectory(dir) && dir.isAbsolute()){

            // map each file f into an instance AnagramsCounter(f) and returns the stream of jobs
            try {
                return Files
                    .list(dir)
                    .filter(d -> d.toString().endsWith(".txt"))
                    .map(path -> new AnagramsCounter(path.toString()));
            } catch (IOException e) { // IOException -> return empty stream
                System.out.println("An IO error occurs when opening the directory!");
                e.printStackTrace();
                return Stream.empty();
            }
        }
        else throw new IllegalArgumentException("Error! "+dir_name+" doesn't exist, is not a directory or is not an absolute path!");
    }

    /**
     * write the list of ciao keys and the number of words associated with each key,
     * one per line, in this.out_file, in the format "<ciao_key> - <num>".
     * @param stream: a stream of pairs resulting from collect;
     *                  each pair is in the format <ciao(wi), {w1,w2,...,wn}>
     *                  such that 1 <= i <= n && ciao(wi)==ciao(wj) for each 1 <= i, j <= n
     */
    @Override
    protected void output(Stream<Pair<String, List<String>>> stream) {
        try (PrintWriter pw = new PrintWriter(this.out_file, "UTF-8")) {
            stream
                .map(pair -> "<"+pair.getKey()+"> - <"+pair.getValue().size()+">")
                .forEach(pw::println);
            System.out.println("Output wrote in "+this.out_file);
        } catch (IOException e) { // IOException -> return
            System.out.println("An IO error occurs when opening the file "+this.out_file);
            e.printStackTrace();
            return;
        }
    }
}
