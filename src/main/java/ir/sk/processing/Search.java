package ir.sk.processing;

public class Search {

    public int[] searchRange(String[][] arr, String target) {
        int leftBound = leftBoundBinarySearch(arr, target);
        int rightBound = rightBoundBinarySearch(arr, target);
        return new int[]{leftBound, rightBound};
    }

    /**
     * Given a sorted array and a target number.
     * Return the index of the first target number in the array if it exists, otherwise return -1.
     *
     * @param array
     * @param target
     * @return
     */
    public static int leftBoundBinarySearch(String[][] array, String target) {
        int left = 0, right = array.length - 1; // interval [left, right]
        // search interval is [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid][1].compareTo(target) < 0) {
                // search interval is [mid+1, right]
                left = mid + 1;
            } else if (array[mid][1].compareTo(target) > 0) {
                // search interval is [left, mid-1]
                right = mid - 1;
            } else if (array[mid][1].equals(target)) {
                // shrink right border
                right = mid - 1;
            }
        }
        // check out of bounds
        if (left >= array.length || !array[left][1].equals(target))
            return -1;
        return left;
    }

    /**
     * Given a sorted array and a target number.
     * Return the index of the last target number in the array if it exists, otherwise return -1.
     *
     * @param array
     * @param target
     * @return
     */
    public static int rightBoundBinarySearch(String[][] array, String target) {
        int left = 0, right = array.length - 1; // interval [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid][1].compareTo(target) < 0) {
                left = mid + 1;
            } else if (array[mid][1].compareTo(target) > 0) {
                right = mid - 1;
            } else if (array[mid].equals(target)) {
                // here~ change to shrink left bounds
                left = mid + 1;
            }
        }
        // here~ change to check right out of bounds, see below
        if (right < 0 || !array[right].equals(target))
            return -1;
        return right;
    }
}
