package data;

public class IndexPage {

    private IndexPair[] pairs;

    /**
     * Simple constructor of IndexPage initializing pairs.
     * @param pairs an IndexPair[] array.
     */
    public IndexPage(IndexPair[] pairs){
        this.pairs = pairs;
    }

    /**
     * Getter for index pairs.
     * @return an IndexPair[] array.
     */
    public IndexPair[] getPairs() {
        return pairs;
    }

    /**
     * This method takes an IndexPair[] array and returns and IndexPage[] array.
     * @param pairs an IndexPair[] array.
     * @return an IndexPage[] array.
     */
    public static IndexPage[] createIndexPages(IndexPair[] pairs) {

        if (pairs[0] == null) {
            return null;
        }

        int size = Math.ceilDiv(pairs.length,32);
        IndexPage[] pages = new IndexPage[size];
        for (int i = 0; i < pages.length; ++i) {
            IndexPair[] temp = new IndexPair[32];
            for (int j = 0; j < 32; j++) {
                if (32 * i + j >= pairs.length) {
                    break;
                }
                temp[j] = pairs[32 * i + j];
            }
            pages[i] = new IndexPage(temp);
        }
        return pages;
    }

    /**
     * This method returns the number of not null IndexPair objects that this IndexPage has.
     * @return the number of IndexPair objects in this IndexPage.
     */
    public int getInstances(){
        int count=0;
        for(int i=0;i<pairs.length;++i){
            if(pairs[i] == null){
                break;
            }
            count++;
        }
        return count;
    }

    /**
     * This method searches (linearly) for a key inside the IndexPage,
     * if it is there it returns the IndexPair object it was found in otherwise
     * it returns null
     * @param key the key the method is searching
     * @return the IndexPair object with the key or null
     */
    public IndexPair hasKey(int key){
        for(var p: pairs){
            if(p.getKey() == key){
                return p;
            }
        }
        return null;
    }

    public void printPage(){
        for(int i=0;i<getInstances();++i){
            System.out.println(pairs[i].getKey() + " " + pairs[i].getPageNumber());
        }
    }
}
