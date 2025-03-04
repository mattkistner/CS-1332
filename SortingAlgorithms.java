import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.PriorityQueue;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author MATTTHEW KISTNER
 * @version 1.0
 * @userid MKISTNER6
 * @GTID 903677868
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Parameter entries cannot be null.");
        } else if (arr.length != 1) {
            for (int k = 1; k < arr.length; k++) {
                for (int n = k; n > 0; n--) {
                    if (comparator.compare(arr[n], arr[n - 1]) < 0) {
                        swap(arr, n, n - 1);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Swap method.
     *
     * @param arr array of type T to perform swap in.
     * @param i index of item to swap.
     * @param ii secondary index to swap.
     * @param <T> data type to sort.
     */
    private static <T> void swap(T[] arr, int i, int ii) {
        T stor = arr[i];
        arr[i] = arr[ii];
        arr[ii] = stor;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else {
            int front = 0;
            int back = arr.length - 1;
            while (front < back) {
                int lastSwap = 0;
                for (int i = front; i < back; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        swap(arr, i, i + 1);
                        lastSwap = i;
                    }
                }
                back = lastSwap;
                for (int i = back; i > front; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        swap(arr, i, i - 1);
                        lastSwap = i;
                    }
                }
                front = lastSwap;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else {
            if (arr.length <= 1) {
                return;
            }
            int length = arr.length;
            int mid = length / 2;
            T[] leftArr = (T[]) new Object[mid];
            T[] rightArr = (T[]) new Object[length - mid];
            for (int i = 0; i < mid; i++) {
                leftArr[i] = arr[i];
            }
            for (int i = 0; i < rightArr.length; i++) {
                rightArr[i] = arr[mid + i];
            }
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);
            int leftI = 0;
            int rightI = 0;
            while (leftI < leftArr.length && rightI < rightArr.length) {
                if (comparator.compare(leftArr[leftI], rightArr[rightI]) <= 0) {
                    arr[leftI + rightI] = leftArr[leftI];
                    ++leftI;
                } else {
                    arr[leftI + rightI] = rightArr[rightI];
                    ++rightI;
                }
            }
            while (leftI < leftArr.length) {
                arr[leftI + rightI] = leftArr[leftI];
                ++leftI;
            }
            while (rightI < rightArr.length) {
                arr[leftI + rightI] = rightArr[rightI];
                ++rightI;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        } else {
            quickerPickerUpper(arr, 0, arr.length - 1, comparator, rand);
        }
    }

    /**
     * Quick Sort Recursive Helper Method.
     *
     * @param arr array to be sorted.
     * @param start int indicating first variable/start of array.
     * @param end int indicating last variable/end of array.
     * @param comparator comparator to do comparisons.
     * @param rand random generator for the pivot.
     * @param <T> Type T of items in array.
     */
    private static <T> void quickerPickerUpper(T[] arr, int start, int end,
                                               Comparator<T> comparator, Random rand) {
        if ((end - start) < 1) {
            return;
        } else {
            int pivoti = rand.nextInt(end - start + 1) + start;
            T pivot = arr[pivoti];
            swap(arr, start, pivoti);
            int i = start + 1;
            int j = end;
            while (i <= j) {
                while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                    i++;
                }
                while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                    j--;
                }
                if (i <= j) {
                    swap(arr, i, j);
                    i++;
                    j--;
                }
            }
            swap(arr, start, j);
            quickerPickerUpper(arr, start, j - 1, comparator, rand);
            quickerPickerUpper(arr, j + 1, end, comparator, rand);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new  IllegalArgumentException("Array cannot be null.");
        } else {
            int max = getLargest(arr);
            int maxDigits = numDigits(max);
            LinkedList[] buckets = new LinkedList[19];
Instructor
| 11/12 at 11:28 am
Grading comment:
Make sure to specify that the LinkedList contains integers

            for (int bucket = 0; bucket < buckets.length; bucket++) {
                buckets[bucket] = new LinkedList();
            }
            lsdRadixH(maxDigits, 1, 0, arr, buckets);
        }
    }

    /**
     * LSD Radix Recursive Helper Method.
     *
     * @param digits int representing the max digits of any number in the array.
     * @param place current digit's place value.
     * @param iterations number of times the recursive method has been called to ensure
     *                   it does not exceed digits.
     * @param arr array to be sorted.
     * @param buckets buckets holding data in LinkedList array.
     */
    private static void lsdRadixH(int digits, int place, int iterations,
                                  int[] arr, LinkedList[] buckets) {
        if (iterations > digits) {
            return;
        } else {
            for (int i = 0; i < arr.length; i++) {
                int bucket = (arr[i] / place) % 10;
                buckets[bucket + 9].add(arr[i]);
            }
            int indx = 0;
            for (LinkedList bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[indx++] = (int) bucket.remove();
                }
            }
            lsdRadixH(digits, place * 10, iterations + 1, arr, buckets);
        }
    }

    /**
     * Method that finds the largest int in an array.
     *
     * @param arr array of int to be traversed through.
     * @return largest int in the array.
     */
    private static int getLargest(int[]arr) {
        int largest = 0;
        for (int i : arr) {
            if (i == Integer.MIN_VALUE || i == Integer.MAX_VALUE) {
                return 1234567890;
            } else if (Math.abs(i) >= largest) {
                largest = Math.abs(i);
            }
        }
        return largest;
    }

    /**
     * Finds the number of digits in a given number.
     *
     * @param num int to find number of digits of.
     * @return number of digits in the int parameter.
     */
    private static int numDigits(int num) {
        if (num / 10 < 10) {
            return 1;
        } else {
            return 1 + numDigits(num / 10);
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        } else {
            PriorityQueue queue = new PriorityQueue<Integer>(data);
            int[] sorted = new int[data.size()];
            for (int i = 0; i < data.size(); i++) {
                sorted[i] = (int) queue.remove();
            }
            return sorted;
        }
    }
}
