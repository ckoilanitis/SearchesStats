package data;

public class IndexPair implements Comparable<IndexPair>{

    private int key;
    private int page_number;

    /**
     * Simple constructor initializing an IndexPair.
     * @param key the integer that is used as a key.
     * @param page_number the corresponding page number that the key is saved.
     */
    public IndexPair(int key, int page_number) {
        this.key = key;
        this.page_number = page_number;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    /**
     * This method takes a DataPage[] array and returns an IndexPair[] array.
     * @param pages a DataPage[] array.
     * @return an IndexPage[] array.
     */
    public static IndexPair[] pagesToPairs(DataPage[] pages){
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
     * Getter for the key.
     * @return the key integer of the IndexPair.
     */
    public int getKey() {
        return key;
    }

    /**
     * Getter for the number of the page the corresponding key is saved.
     * @return the page number integer.
     */
    public int getPageNumber() {
        return page_number;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(IndexPair o) {
        return Integer.compare(this.key, o.key);
    }
}
