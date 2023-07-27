/**
 * Advanced Programming - Assignment 2.2
 * Concrete Job: Anagrams Counter
 */

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnagramsCounter extends AJob<String, String>{
    private String file;

    public AnagramsCounter(String file){ this.file = file; }

    /**
     * read this.file and returns a stream of pairs of the form (ciao(w), w)
     * @return  a stream of pair <ciao(w), w> for each word w into this.file
     *          an empty stream if file not found
     */
    @Override
    public Stream<Pair<String,String>> execute() {
        BufferedReader buf = null;

        try {
            buf = new BufferedReader(new FileReader(this.file));
        } catch (FileNotFoundException e) { // File not found -> return empty stream
            System.out.println("Error! File "+this.file+" was not found and will not be computed");
            return Stream.empty(); // doesn't compute this file, returning an empty stream
        }
        return buf
            .lines()
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .filter( s -> s.matches("^[a-zA-Z]{4,}$"))
            .map(String::toLowerCase)
            .map(s -> new Pair(ciao(s), s));
    }

    /**
     * ciao(s) is a string having the same length of s and containing all
     * the characters of s in lower case and alphabetical order.
     * @param s: a string to map into its ciao
     * @return the ciao of s
     */
    private String ciao (String s){
        return Stream
            .of(s.split(""))
            .sorted()
            .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return "AnagramsCounter for file "+this.file;
    }
}
