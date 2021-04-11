
package ni.edu.uni.programacion.utils;

import java.io.IOException;
import java.io.RandomAccessFile;


public class SearchAlgorithms {

    public static int randomBinarySearch(RandomAccessFile raf, int key, int low, int high) throws IOException {
        int index = Integer.MIN_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            
            long pos = 8 + (mid)*4;
            raf.seek(pos);
            int id = raf.readInt();
            if (id < key) {
                low = mid + 1;
            } else if (id > key) {
                high = mid - 1;
            } else if (id == key) {
                index = (int) pos;
                break;
            }
        }
        return index;
    }
}
