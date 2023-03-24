package fileIO;

import auxiliary.DataConverter;
import data.DataClass;
import data.DataPage;
import data.IndexPage;
import data.IndexPair;
import test.Counter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class SearchLinearWithIndexes implements FileAccess{

    private RandomAccessFile file, indexes;

    public SearchLinearWithIndexes() {
        try {
            Files.deleteIfExists(Path.of("data2.bin"));
            Files.deleteIfExists(Path.of("indexes.bin"));
            file = new RandomAccessFile("data2.bin", "rw");
            indexes = new RandomAccessFile("indexes.bin", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataClass Search(int key, int size) {
        int num_of_pages;
        IndexPair temp;
        int page_found = -1;
        try{
            num_of_pages = (int) Math.ceilDiv(indexes.length(), 256);
            for(int i=0;i<num_of_pages;++i){
                Counter.plusOne(1);
                temp = DataConverter.convertBytesToIndexPage(FileAccess.readArrayOfAPage(i,indexes)).hasKey(key);
                if(temp!= null){
                    page_found = temp.getPageNumber();
                    break;
                }
            }
            if(page_found == -1) {
                return null;
            }
            Counter.plusOne(1);
            return DataConverter.convertBytesToDataPage(FileAccess.readArrayOfAPage(page_found,file),size).hasKey(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeToFile(DataClass[] classes) {
        DataPage[] dataPages = DataPage.createPages(classes);
        for(int i = 0; i< dataPages.length; ++i){
            FileAccess.writeArrayOfAPage(DataConverter.convertPageToBytes(dataPages[i]), i,file);
        }
        IndexPair[] pairs = IndexPair.pagesToPairs(dataPages);
        IndexPage[] pages = IndexPage.createIndexPages(pairs);
        for (int i=0;i<pages.length;++i){
            FileAccess.writeArrayOfAPage(DataConverter.convertIndexPagesToBytes(pages[i]),i,indexes);
        }
    }

    @Override
    public void closeFiles() {
        try {
            file.close();
            indexes.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
