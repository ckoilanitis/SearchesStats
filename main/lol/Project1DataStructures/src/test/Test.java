package test;

import data.DataClass;
import fileIO.FileAccess;
import fileIO.SearchBinary;
import fileIO.SearchLinearWithIndexes;
import fileIO.SearchLinearWithoutIndexes;

import java.util.Random;

public class Test {

        public static void runAllTests() {
            System.out.println("\n" + "STARTING TESTS");
            // all input sizes to be examined
            int[] inputSizes = {50, 100, 200, 500, 5000, 800, 1000, 10000, 50000, 100000, 200000};
            //int[] inputSizes = {10000};
            for(var inputSize : inputSizes) {
                tests(DataClass.BIG, inputSize);
                tests(DataClass.SMALL, inputSize);
            }
        }

    /**
     * Runs every type of search for every different type of input
     * @param size data size
     * @param numOfInputs number of inputs
     */
    public static void tests(int size, int numOfInputs) {

            FileAccess[] searches = new FileAccess[3];


            searches[0] = new SearchLinearWithoutIndexes();
            searches[1] = new SearchLinearWithIndexes();
            searches[2] = new SearchBinary();

            DataClass[] data = DataClass.createData(numOfInputs,size);

            System.out.println("\n".repeat(2) +
                    "SEARCHING FOR: " + numOfInputs + " " + size + "-byte data");

            Random r = new Random();
            int[] randomKeys = size >= 1000 ?
                    r.ints(0, 2*numOfInputs + 1).distinct().limit(1000).toArray()
                    : r.ints(0, 2*numOfInputs + 1).limit(1000).toArray();

            for(int i = 0; i < searches.length; i++) {
                searches[i].writeToFile(data);

                int found = 0;
                long startTime = System.nanoTime();
                for(int j = 0; j < randomKeys.length; j++) {
                    int key = randomKeys[j];
                    if(searches[i].Search(key,size) != null)
                        found++;
                }
                long timeTook = System.nanoTime() - startTime;

                System.out.println("Search: " + (i + 1) +
                        " - Mean Time: " + timeTook/1000 + " ns" +
                        " - Average disk accesses: " + String.format("%.2f" , (float) Counter.getCounter(i) / 1000) +
                        " - Percentage(FOUND KEYS): " + String.format("" + (float)((100 * found) / 1000), "%.2f") + " %");
                searches[i].closeFiles();
            }
            Counter.zeroAllCounters(); // zero all counters
        }
}
