package data;

import static java.lang.Math.ceil;

public class DataPage {

    private DataClass[] classes;

    /**
     * Simple Constructor initializes the page using a DataClass[] array.
     * @param classes a DataClass[] array.
     */
    public DataPage(DataClass[] classes) {
        this.classes = classes;
    }

    /**
     * Getter for classes (only not null classes).
     * @return a DataClass[] array.
     */
    public DataClass[] getClasses() {
        DataClass[] temp = new DataClass[numberOfInstances()];
        for (int i = 0; i < temp.length; ++i) {
            temp[i] = classes[i];
        }
        return temp;
    }

    /**
     * This method takes a DataClass[] array and returns a DataPage[] array.
     * @param classes a DataClass[] array.
     * @return a DataPage[] array.
     */
    public static DataPage[] createPages(DataClass[] classes) {
        if (classes[0] == null) {
            return null;
        }
        int size = classes[0].getData().length() + 4;//string length + key length
        int instancePerPage = 256 / size;
        DataPage[] arr = new DataPage[(int) ceil((float) classes.length / instancePerPage)];


        for (int i = 0; i < arr.length; ++i) {
            DataClass[] temp = new DataClass[instancePerPage];
            for (int j = 0; j < instancePerPage; j++) {
                if (instancePerPage * i + j >= classes.length) {

                    break;
                }
                temp[j] = classes[instancePerPage * i + j];
            }
            arr[i] = new DataPage(temp);
        }
        return arr;
    }

    /**
     * This method finds the number of DataClass objects inside a DataPage.
     * @return number of DataClass objects.
     */
    public int numberOfInstances() {
        int count = 0;
        for (int i = 0; i < classes.length; ++i) {
            if (classes[i] == null) {
                break;
            }
            count++;
        }
        return count;
    }

    /**
     * This method finds the number of DataClasses objects in several pages.
     * @param pages a DataPage[] array.
     * @return number of DataClass objects in several pages.
     */
    public static int numberOfInstances(DataPage[] pages) {
        int count = 0;
        for (int i = 0; i < pages.length; ++i) {
            count += pages[i].numberOfInstances();
        }
        return count;
    }

    /**
     * This method searches (linearly) for a key inside the page,
     * if it is there it returns the DataClass object it was found in otherwise
     * it returns null
     * @param key the key the method is searching
     * @return the DataClass obj with the key or null
     */
    public DataClass hasKey(int key) {
        for (int i = 0; i < classes.length; ++i) {
            if (classes[i] == null) {
                break;
            }
            if (classes[i].getKey() == key) {
                return classes[i];
            }
        }
        return null;
    }

    /**
     * This method prints the data sting and the key integer of all classes inside a page
     */
    public void printPage() {

        for (int i = 0; i < classes.length; ++i) {

            if (classes[i] == null) {
                break;
            }
            System.out.println(classes[i].getData());
            System.out.println(classes[i].getKey());
        }
    }
}
