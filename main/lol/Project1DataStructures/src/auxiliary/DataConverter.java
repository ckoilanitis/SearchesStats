package auxiliary;

import data.DataClass;
import data.DataPage;
import data.IndexPage;
import data.IndexPair;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataConverter {

    /**
     * This method takes an IndexPage and returns its bytes in an array.
     * @param page IndexPage object.
     * @return a byte[] array of the parameter's content.
     */
    public static byte[] convertIndexPagesToBytes(IndexPage page){
        ByteBuffer bb = ByteBuffer.allocate(256);
        for (int i=0;i< page.getInstances();++i){
            bb.putInt(page.getPairs()[i].getKey());
            bb.putInt(page.getPairs()[i].getPageNumber());
        }

        return bb.array();
    }

    /**
     * This method takes an DataPage and returns its bytes in an array.
     * @param page DataPage object.
     * @return a byte[] array of the parameter's content.
     */
    public static byte[] convertPageToBytes(DataPage page){
        int num = page.numberOfInstances();
        byte[] bytes = new byte[256];
        byte[] classesBytes = convertArrayDataClassToBytes(page.getClasses());
        for(int i=0;i<classesBytes.length;++i){
            bytes[i] = classesBytes[i];
        }
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(num);
        byte[] numOfInstances = bb.array();
        for(int i=0;i<numOfInstances.length;++i){
            bytes[i+252] = numOfInstances[i];
        }

        return bytes;
    }

    /**
     * This method takes an array of bytes and returns an IndexPage object.
     * @param bytes a byte[] array.
     * @return an IndexPage object.
     */
    public static IndexPage convertBytesToIndexPage(byte[] bytes){
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        int size = (int) Math.ceil((float)bytes.length/8.0);
        IndexPair[] pairs = new IndexPair[size];
        for(int i=0;i<size;++i){
            pairs[i] = new IndexPair(bb.getInt(), bb.getInt());
        }
        return new IndexPage(pairs);
    }

    /**
     * This method takes the size of the data (DataClass.BIG/SMALL) and a byte array and returns
     * a DataPage object.
     * @param bytes a byte[] array
     * @param size the size of data string DataClass.BIG/SMALL(55/27).
     * @return a DataPage object.
     */
    public static DataPage convertBytesToDataPage(byte[] bytes, int size){
        ByteBuffer bb = ByteBuffer.wrap(bytes,252,4);
        int numOfInstancesPerPage = bb.getInt();
        DataClass[] classes = new DataClass[numOfInstancesPerPage];
        int start = 0;
        int end = size+4;
        for(int i=0;i<numOfInstancesPerPage;++i) {
            classes[i] = convertBytesToDataClass(Arrays.copyOfRange(bytes,start,end));
            start+=(size+4);
            end+=(size+4);
        }
        DataPage page = new DataPage(classes);
        //page.printPage();
        return page;
    }

    /**
     * This method takes an array of DataClasses and returns it in a byte array.
     * @param data an array of DataClass[] objects
     * @return a byte[] array with the data contents
     */
    public static byte[] convertArrayDataClassToBytes(DataClass[] data) {

        int size = data[0].getData().length() == 55 ? 59 : 31;
        ByteBuffer byteBuffer = ByteBuffer.allocate(size*data.length);

        for (DataClass obj : data) {
            byteBuffer.putInt(obj.getKey());
            byteBuffer.put(obj.getData().getBytes(StandardCharsets.US_ASCII));
        }
        return byteBuffer.array();
    }

    /**
     * This method takes a byte array and returns a DataClass object.
     * @param bytes a byte[] array
     * @return a DataClass object
     * @throws IllegalArgumentException if bytes length is other than 27 or 55
     */
    public static DataClass convertBytesToDataClass(byte[] bytes) throws IllegalArgumentException{

        int size  = bytes.length;

        // the array's length should always be either 31 or 59
        if(size != 31 && size != 59)
            throw new IllegalArgumentException("Illegal byte array length");

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        byte[] infoBytes = new byte[size - 4]; // create byte[] for info

        byteBuffer.get(4, infoBytes); // read info with appropriate length

        int key = byteBuffer.getInt(0);
        String info = new String(infoBytes, StandardCharsets.US_ASCII); // convert infoBytes to string
        return new DataClass(key, info);
    }
}
