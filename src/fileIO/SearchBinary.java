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
import java.util.Arrays;

public class SearchBinary implements FileAccess {

    private RandomAccessFile file, indexes;

    public SearchBinary() {
        try {
            Files.deleteIfExists(Path.of("data3.bin"));
            Files.deleteIfExists(Path.of("indexes_binary.bin"));
            file = new RandomAccessFile("data3.bin", "rw");
            indexes = new RandomAccessFile("indexes_binary.bin", "rw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataClass Search(int key, int size) {

        try {
            int low = 0;
            int high = (int) Math.ceil((float) indexes.length() / 256) - 1;
            int page_found = -1;
            while (low <= high) {
                int mid = low + ((high - low) / 2);
                IndexPage page = DataConverter.convertBytesToIndexPage(FileAccess.readArrayOfAPage(mid,indexes));
                Counter.plusOne(2);
                if (page.hasKey(key)!= null) {
                    if(page.hasKey(key).getKey() == key){
                        page_found = page.hasKey(key).getPageNumber();
                        break;
                    }

                } else if (page.getPairs()[0].getKey() > key) {
                    high = mid - 1;
                } else if (page.getPairs()[page.getInstances() - 1].getKey() < key) {
                    low = mid + 1;
                } else {
                    break;
                }
            }
            if(page_found == -1) {
                return null;
            }
            Counter.plusOne(2);
            return DataConverter.convertBytesToDataPage(FileAccess.readArrayOfAPage(page_found,file),size).hasKey(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public void writeToFile(DataClass[] classes) {
        DataPage[] dataPages = DataPage.createPages(classes);
        for(int i = 0; i< dataPages.length; ++i){
            FileAccess.writeArrayOfAPage(DataConverter.convertPageToBytes(dataPages[i]), i,file);
        }
        IndexPair[] pairs = IndexPair.pagesToPairs(dataPages);
        Arrays.sort(pairs);
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
