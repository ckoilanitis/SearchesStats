package fileIO;

import data.DataClass;
import data.DataPage;
import data.IndexPair;

import java.io.IOException;
import java.io.RandomAccessFile;

public interface FileAccess {

    void closeFiles();

    DataClass Search(int key,int size);

    /**
     * This method takes a DataClass[] array and converts it to a DataPages[] array
     * and then writes each page in a file.
     * @param classes a DataClass[] array.
     */
    void writeToFile(DataClass[] classes);

    /**
     * This method takes a DataPage[] array and returns an IndexPair[] array.
     * @param pages a DataPage[] array.
     * @return an IndexPage[] array.
     */
    static IndexPair[] pagesToPairs(DataPage[] pages){
        IndexPair[] pairs = new IndexPair[DataPage.numberOfInstances(pages)];
        int k=0;
        for(int i=0;i< pages.length;++i){
            for(var object : pages[i].getClasses()){
                if(object == null){
                    break;
                }
                pairs[k] = new IndexPair(object.getKey(),i);
                k++;
            }
        }
        return pairs;
    }

    /**
     *
     * @param page
     * @param offset
     */
    static void writeArrayOfAPage(byte[] page, int offset,RandomAccessFile file) {

        try{
            file.seek((long) offset * 256);
            file.write(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static byte[] readArrayOfAPage(int index,RandomAccessFile file) {

        try {
            byte[] data = new byte[256];
            file.seek((long) index * 256);
            file.read(data);
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
