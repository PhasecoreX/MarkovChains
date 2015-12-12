package com.pcxserver.markovChains;

import java.util.ArrayList;
import java.util.Iterator;

public class FrequencyWord {

    String                    word;
    ArrayList<FrequencyPairs> pairs;

    public FrequencyWord(String word) {
        this.word = word;
        pairs = new ArrayList<FrequencyPairs>();
    }

    /**************************************************************************
     * Takes in a String and tries to find it in the database. If it succeeds
     * and finds a matching instance, it increases its frequency by 1. If it
     * does not find an instance, it creates a new instance of the word and
     * makes the frequency 1.
     *
     * @param first
     *            The string being appended to the database.
     * @return 0 for addition to previous instance, or 1 for new instance
     *         created.
     *************************************************************************/
    public int appendTarget(String first) {
        Iterator<FrequencyPairs> it = pairs.iterator();
        int index = 0;
        // While here is something to check.
        while (it.hasNext()) {
            FrequencyPairs temp = it.next();
            if (temp.getFirst().equals(first)) {
                // Increase the existing entry's frequency by one.
                temp.setSecond(temp.getSecond() + 1);
                pairs.set(index, temp);
                return 0;
            }
            index++;
        }
        // Create a new entry.
        FrequencyPairs element = new FrequencyPairs(first, 1);
        pairs.add(element);
        return 1;
    }

    public ArrayList<FrequencyPairs> getList() {
        return pairs;
    }

    /**************************************************************************
     * Getter method for "word".
     *
     * @return Word associated with this object.
     *************************************************************************/
    public String getWord() {
        return word;
    }

    /**************************************************************************
     * Searches through the list of FrequencyPairs to find the next most
     * probable word, then returns it after lowering the frequency to avoid
     * endless loops.
     *
     * @return The most probable next word to be the next perspective.
     *************************************************************************/
    public String nextPerspective() {
        Iterator<FrequencyPairs> it = pairs.iterator();
        FrequencyPairs temp1 = null;
        int freq = 0;
        while (it.hasNext()) {
            FrequencyPairs temp = it.next();
            if (temp.getSecond() > freq) {
                temp1 = temp;
                freq = temp1.getSecond();
            }
        }
        if (temp1 == null) {
            return ".";
        }
        temp1.setSecond(temp1.getSecond() / 5); // Frequency Lower-er
        return temp1.getFirst();
    }
}
