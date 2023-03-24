package data;

import auxiliary.*;
import java.util.Random;

public class DataClass {
    private int key;
    private String data;
    public static int SMALL = 27;
    public static int BIG = 55;

    public DataClass(int key, String data) {
        this.key = key;
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

    /**
     * This method takes the desired number of instances and the size of the data string.
     * @param num_of_instances number of instances (integer).
     * @param size size of the data string (DataClass.BIG/SMALL(55/27)).
     * @return a DataClass[] array
     */
    public static DataClass[] createData(int num_of_instances, int size) {

        DataClass[] datarr = new DataClass[num_of_instances];

        Random r = new Random();
        int[] randomKeys = r.ints(0, 2 * num_of_instances).distinct().limit(num_of_instances).toArray();

        for (int i = 0; i < num_of_instances; ++i) {
            datarr[i] = new DataClass(randomKeys[i], RandomString.getAlphaNumericString(size));
        }
        return datarr;
    }
}
