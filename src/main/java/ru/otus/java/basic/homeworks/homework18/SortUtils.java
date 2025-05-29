package ru.otus.java.basic.homeworks.homework18;

public class SortUtils {
    public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }
    public static void quickSort(int[] array) {
        quickSortRecursive(array, 0, array.length - 1);
    }

    public static void quickSortRecursive(int[] array, int low, int high) {
        if (low < high) {
            int p = partition(array, low, high);
            quickSortRecursive(array, low, p - 1);
            quickSortRecursive(array, p + 1, high);
        }
    }

    public static int partition(int[] array, int low, int high){
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;

                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }
}
