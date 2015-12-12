package com.pcxserver.markovChains;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class MarkovDatabase {

    ArrayList<FrequencyWord> list;

    public MarkovDatabase() {
        list = new ArrayList<FrequencyWord>();
        // We are going to use period as the beginning token and the end token.
        list.add(new FrequencyWord("."));
    }

    private FrequencyWord getNextWord(String word) {
        Iterator<FrequencyWord> it = list.iterator();
        while (it.hasNext()) {
            FrequencyWord temp = it.next();
            if (temp.getWord().equals(word)) {
                return temp;
            }
        }
        return null;
    }

    private String nextSentence() {
        int start = (int) (Math.random() * ((double) list.get(0).getList().size() - 1));
        return list.get(0).getList().get(start).getFirst();
    }

    public int read(String perspective, String target) {
        int index = 0;
        Iterator<FrequencyWord> it = list.iterator();
        while (it.hasNext()) {
            FrequencyWord temp = it.next();
            if (temp.getWord().equals(perspective)) {
                // Append the target to the existing perspective.
                temp.appendTarget(target);
                list.set(index, temp);
                return 0;
            }
            index++;
        }
        // Create a new perspective to house the target.
        FrequencyWord temp = new FrequencyWord(perspective);
        temp.appendTarget(target);
        list.add(temp);
        return 1;
    }

    public boolean readfile(String filename) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        String last = "."; // We always start with the period, to signify the
                           // beginning of a sentence.
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("[\"()]", "");
            line = line.replaceAll("[.?!]", " . ");
            String[] parts = line.split(" ");
            for (int i = 0; i < parts.length - 1; i++) { // For all words in the
                                                         // line...
                if (!parts[i].equals("")) { // If the word is actually a word
                                            // and not an empty string...
                    read(last, parts[i]); // Read it in with the previous
                                          // word...
                    last = parts[i]; // And set last to that word for next time.
                }
            }
        }
        read(last, "."); // And close the last word with a period.
        reader.close();
        return true;
    }

    public void writeParagraph(String filename) throws Exception {
        PrintWriter writer = new PrintWriter(filename + ".txt");
        writer.print(nextSentence() + " " + nextSentence() + "\nA heartfelt story by Michael Currie and Ryan Foster\n\n\n");

        for (int i = 5; i > 0; i--) {
            int numberOfSentences = 6;
            FrequencyWord temp;
            String next = nextSentence();
            boolean first = true;
            while (numberOfSentences > 0) {
                if (first) {
                    writer.print(next);
                    first = false;
                } else {
                    writer.print(" " + next);
                }
                temp = getNextWord(next);
                next = temp.nextPerspective();
                if (next.equals(".")) {
                    writer.print(". ");
                    numberOfSentences--;
                    next = nextSentence();
                    temp = getNextWord(next);
                    first = true;
                }
            }
            writer.print("\n\n");
        }
        writer.print("\nThe End...?\n");
        writer.close();
    }

    public String writeSentence() {
        String sentence = "";
        String next = nextSentence();
        sentence = sentence + next;
        FrequencyWord temp;
        while (true) {
            temp = getNextWord(next);
            next = temp.nextPerspective();
            if (next.equals(".")) {
                sentence = sentence + ".";
                return sentence;
            } else {
                sentence = sentence + " " + next;
            }
        }
    }
}
