package ir.sk.processing;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Search {

    public final static int THREAD_NUMS = 2;

    public int[] searchRange(List<List<String>> arr, int index, TypeCompareStrategy strategy, String target) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMS);
        Future<Integer> leftFuture = executorService.submit(() -> leftBoundBinarySearch(arr, index, strategy, target));
        Future<Integer> rightFuture = executorService.submit(() -> rightBoundBinarySearch(arr, index, strategy, target));
        int leftBound = leftFuture.get(); // blocking
        int rightBound = rightFuture.get(); // blocking
        executorService.shutdown();
        return new int[]{leftBound, rightBound};
    }

    /**
     * Given a sorted lists and a target number.
     * Return the index of the first target number in the lists if it exists, otherwise return -1.
     *
     * @param lists
     * @param target
     * @return
     */
    public static int leftBoundBinarySearch(List<List<String>> lists, int listIndex, TypeCompareStrategy strategy, String target) {
        int left = 0, right = lists.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String date = lists.get(mid).get(listIndex);

            if (strategy.compareTo(target, date) < 0) {
                left = mid + 1;
            } else if (strategy.compareTo(target, date) > 0) {
                right = mid - 1;
            } else if (strategy.compareTo(target, date) == 0) {
                // shrink right border
                right = mid - 1;
            }
        }
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
    public static int rightBoundBinarySearch(List<List<String>> array, int listIndex, TypeCompareStrategy strategy, String target) {
        int left = 0, right = array.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String date = array.get(mid).get(listIndex);
            if (strategy.compareTo(target, date) < 0) {
                left = mid + 1;
            } else if (strategy.compareTo(target, date) > 0) {
                right = mid - 1;
            } else if (strategy.compareTo(target, date) == 0) {
                // here~ change to shrink left bounds
                left = mid + 1;
            }
        }
        return right;
    }
}
