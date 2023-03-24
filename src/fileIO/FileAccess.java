package fileIO;

import data.DataClass;
import data.DataPage;
import data.IndexPair;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface FileAccess {

    /**
     * Close the files that were used for the particular search.
     */
    void closeFiles();

    /**
     * Search the key inside a file that has objects of size (size+4) and
     * if the key is inside return the object that has the key otherwise return null.
     * @param key key we search for.
     * @param size size of bytes per String data.
     * @return either the DataClass res with res.key == key or null.
     */
    DataClass Search(int key,int size);

    /**
     * This method takes a DataClass[] array and converts it to a DataPages[] array
     * and then writes each page in a file.
     * @param classes a DataClass[] array.
     */
    void writeToFile(DataClass[] classes);

    /**
     * Write an array of bytes with length 256 with a particular offset in the RandomAccessFile needed.
     * @param page the byte array to be written.
     * @param offset the offset times 256 will give the position in which the insertion will happen.
     * @param file the file in which we want to write in.
     */
    static void writeArrayOfAPage(byte[] page, int offset,RandomAccessFile file) {

        try{
            file.seek((long) offset * 256);
            file.write(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read an array of bytes from an offset in a file.
     * @param offset the number of page to be read.
     * @param file the file that is read.
     * @return the byte array.
     */
    static byte[] readArrayOfAPage(int offset,RandomAccessFile file) {

        try {
            byte[] data = new byte[256];
            file.seek((long) offset * 256);
            file.read(data);
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
