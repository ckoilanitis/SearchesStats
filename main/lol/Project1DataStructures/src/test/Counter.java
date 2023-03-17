package test;

import java.util.Arrays;

public class Counter {

    /**
     * The 3 counters, one foreach method
     */
    private static final int[] counters = new int[3];

    /**
     * Returns the value of a counter
     */
    public static int getCounter(int i) {
        return counters[i];
    }

    /**
     * Increments a counter by 1
     */
    public static void plusOne(int i) {
        ++counters[i];
    }

    /**
     * Sets all counters to 0
     */
    public static void zeroAllCounters() {
        Arrays.fill(counters, 0);
    }
}
