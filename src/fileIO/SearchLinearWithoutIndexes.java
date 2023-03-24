package fileIO;

import auxiliary.DataConverter;
import data.DataClass;
import data.DataPage;
import test.Counter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class SearchLinearWithoutIndexes implements FileAccess {

    private RandomAccessFile file;

    public SearchLinearWithoutIndexes() {
        try {
            Files.deleteIfExists(Path.of("data1.bin"));
            file = new RandomAccessFile("data1.bin", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile(DataClass[] classes){
        DataPage[] dataPages = DataPage.createPages(classes);
        for(int i = 0; i< dataPages.length; ++i){
            FileAccess.writeArrayOfAPage(DataConverter.convertPageToBytes(dataPages[i]), i,file);
        }
    }

    @Override
    public DataClass Search(int key,int size) {
        int num_of_pages;
        DataClass temp;
        try {
            num_of_pages = (int) Math.ceilDiv(file.length(), 256);
            for(int i=0;i<num_of_pages;++i){
                Counter.plusOne(0);
                DataPage page = DataConverter.convertBytesToDataPage(FileAccess.readArrayOfAPage(i,file),size);
                temp = page.hasKey(key);
                if(temp!= null){
                    return temp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void closeFiles() {
        try {
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
