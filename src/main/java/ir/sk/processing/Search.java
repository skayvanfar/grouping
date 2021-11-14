package ir.sk.processing;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Search {

    public final static int THREAD_NUMS = 2;

    public int[] searchRange(List<List<String>> arr, String target) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMS);
        Future<Integer> leftFuture = executorService.submit(() -> leftBoundBinarySearch(arr, target));
        Future<Integer> rightFuture = executorService.submit(() -> rightBoundBinarySearch(arr, target));
        return new int[]{leftFuture.get(), rightFuture.get()}; // blocking
    }

    /**
     * Given a sorted array and a target number.
     * Return the index of the first target number in the array if it exists, otherwise return -1.
     *
     * @param array
     * @param target
     * @return
     */
    public static int leftBoundBinarySearch(List<List<String>> array, String target) {
        int left = 0, right = array.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            LocalDate date = LocalDate.parse(array.get(mid).get(1));
            LocalDate targetDate = LocalDate.parse(target);
            if (targetDate.isBefore(date)) {
                left = mid + 1;
            } else if (targetDate.isAfter(date)) {
                right = mid - 1;
            } else if (targetDate.isEqual(date)) {
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
    public static int rightBoundBinarySearch(List<List<String>> array, String target) {
        int left = 0, right = array.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            LocalDate date = LocalDate.parse(array.get(mid).get(1));
            LocalDate targetDate = LocalDate.parse(target);
            if (targetDate.isBefore(date)) {
                left = mid + 1;
            } else if (targetDate.isAfter(date)) {
                right = mid - 1;
            } else if (targetDate.isEqual(date)) {
                // here~ change to shrink left bounds
                left = mid + 1;
            }
        }
        return right;
    }
}
