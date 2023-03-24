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
            int[] inputSizes = {50, 100, 200, 500, 800, 1000, 2000, 5000, 10000,50000, 100000, 200000};
            for(var inputSize : inputSizes) {
                tests(DataClass.BIG, inputSize);
                tests(DataClass.SMALL, inputSize);
            }
        }

    /**
     * Runs every test needed
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
                long startTime = System.nanoTime();// Timer Starts

                for (int key : randomKeys) {
                    if (searches[i].Search(key, size) != null)
                        found++;
                }

                long timeTook = System.nanoTime() - startTime; // Timer diff

                System.out.println("Search: " + (i + 1) +
                        " - Mean Time: " + timeTook/1000 + " ns" +
                        " - Average disk accesses: " + String.format("%.2f" , (float) Counter.getCounter(i) / 1000) +
                        " - Percentage(FOUND KEYS): " + String.format("" + (float)((100 * found) / 1000), "%.2f") + " %");
                searches[i].closeFiles();

            }

            Counter.zeroAllCounters();
        }
}
