
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Yuqi Cao
 * @userid ycao344
 * @GTID 903352025
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *  adaptive
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException(
                    "The array passed in cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "The comparator passed in cannot be null");
        }
        int length = arr.length;
        boolean swapped = true;
        int i = 0;
        while (swapped) {
            swapped = false;
            for (int j = (length - 1); j > i; j--) {
                int result = comparator.compare(arr[j - 1], arr[j]);
                if (result > 0) {
                    T temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swapped = true;
                }
            }
            i++;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *  adaptive
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException(
                    "The array we wanna sort cannot be null.");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                    "The comparator cannot be null");
        }
        int length = arr.length;
        for (int i = 0; i < (length - 1); i++) {
            int j = i + 1;
            while (j > 0 && comparator.compare(arr[j], arr[j - 1]) < 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;
                j--;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException(
                "The comparator cannot be null.");
        }

        mergeHelper(arr, comparator);
        //It can also be written as having a return object
        //arr = mergeHelp(arr, comparator);
    }

    //    private static <T> T[] mergeHelp(T[] array,
    //                                      Comparator<T> comparator) {
    //        int length = array.length;
    //        if (length == 1) {
    //            return array;
    //        }
    //        int n = length / 2;
    //        T[] arr1 = (T[]) new Object[n];
    //        T[] arr2 = (T[]) new Object[length - n];
    //        for (int i = 0; i < n; i++) {
    //            arr1[i] = array[i];
    //        }
    //        for (int i = 0; i < (length - n); i++) {
    //            arr2[i] = array[i + n];
    //        }
    //
    //        return mergeTwo(mergeHelp(arr1, comparator),
    //                  mergeHelp(arr2, comparator), comparator);
    //    }
    //
    //    private static <T> T[] mergeTwo(T[] arr1, T[] arr2,
    //                                    Comparator<T> comparator) {
    //        int len1 = arr1.length;
    //        int len2 = arr2.length;
    //        T[] newArray = (T[]) new Object[len1 + len2];
    //        int i = 0;
    //        int j = 0;
    //        int p = 0;
    //        while (i < len1 && j < len2) {
    //            int result = comparator.compare(arr1[i], arr2[j]);
    //            if (result < 0 || result == 0) {
    //                newArray[p] = arr1[i];
    //                i++;
    //            } else {
    //                newArray[p] = arr2[j];
    //                j++;
    //            }
    //            p++;
    //        }
    //        while (j < len2) {
    //            newArray[p] = arr2[j];
    //            j++;
    //            p++;
    //        }
    //        while (i < len1) {
    //            newArray[p] = arr1[i];
    //            i++;
    //            p++;
    //        }
    //        return newArray;
    //    }*/

    /**
     * this method is used to help mergesort recursively
     * @param array the array passed in need to be sorted
     * @param comparator the comparator used to compare two T type element
     * @param <T> data type to sort
     */
    private static <T> void mergeHelper(T[] array,
        Comparator<T> comparator) {        
        int length = array.length;
        if (length <= 1) {
            return;
        }
        int n = length / 2;
        T[] arr1 = (T[]) new Object[n];
        T[] arr2 = (T[]) new Object[length - n];
        for (int i = 0; i < n; i++) {
            arr1[i] = array[i];
        }
        for (int i = 0; i < (length - n); i++) {
            arr2[i] = array[i + n];
        }
        mergeHelper(arr1, comparator);
        mergeHelper(arr2, comparator);
        //T[] newarray = (T[]) new Object[length];
        //mergeTwoArray(mergeHelper(arr1, comparator),
        // mergeHelper(arr2, comparator), comparator);
        mergeTwoArray(array, arr1, arr2, comparator);
        //for (int p = 0; p < (newarray.length); p++) {
        //    System.out.println(newarray[p]);
        //    System.out.println("~");
        //}

        //return newarray;
    }

    /**
     * the private method used to integrate two arrays together
     * @param orig the original array passed in as a reference
     * @param arr1 the first array
     * @param arr2 the second array
     * @param comparator the comparator passed in to compare two T element
     * @param <T> type to be sorted
     */
    private static <T> void mergeTwoArray(T[] orig, T[] arr1, T[] arr2,
        Comparator<T> comparator) {
        int len1 = arr1.length;
        int len2 = arr2.length;
        //T[] newArray = (T[]) new Object[len1 + len2];
        int i = 0;
        int j = 0;
        int p = 0;
        while (i < len1 && j < len2) {
            int result = comparator.compare(arr1[i], arr2[j]);
            if (result < 0 || result == 0) {
                orig[p] = arr1[i];
                i++;
            } else {
                orig[p] = arr2[j];
                j++;
            }
            p++;
        }
        while (j < len2) {
            orig[p] = arr2[j];
            j++;
            p++;
        }
        while (i < len1) {
            orig[p] = arr1[i];
            i++;
            p++;
        }
        //return newArray;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException(
                    "The array passed in cannot be null.");
        }
        radixHelper(arr);
    }

    /**
     * this private method is used to do LSD radix sort on array
     * @param array this array passed in the original array need to be sorted
     */
    private static void radixHelper(int[] array) {
        //List<LinkedList<Integer>> bucket = new ArrayList<>(19);
        LinkedList<Integer>[] bucket = new LinkedList[19];
        boolean continueOrNot = true;
        int power = 1;
        while (continueOrNot) {
            continueOrNot = false;
            //array to bucket
            for (int i = 0; i < array.length; i++) {
                if (array[i] / power / 10 != 0) {
                    continueOrNot = true;
                }
                int index = (array[i] / power) % 10 + 9;
                if (bucket[index] == null) {
                    bucket[index] = new LinkedList<>();
                }
                bucket[index].add(array[i]);
            }
            power = power * 10;
            //bucket to array
            int j = 0;
            for (int i = 0; i < 19; i++) {
                while (bucket[i] != null && bucket[i].peek() != null) {
                    //System.out.println("what happened? " + bucket[i].peek());
                    array[j] = bucket[i].poll();
                    //System.out.println(bucket[i].peek());
                    j++;
                }
            }
            
        }
    }
}

