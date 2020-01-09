package com.troy.practice.datastructure;

import java.util.Arrays;

public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {99,12,102,-1,8};
        System.out.println("排序前:");
        System.out.println(Arrays.toString(arr));
        selectSort(arr);
    }

    public static void selectSort(int arr[]){
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int minIndex = i;
            for (int j = i+1; j < arr.length; j++) {
                if (min > arr[j]){
                    min = arr[j];
                    minIndex = j;
                }
            }

            arr[minIndex] = arr[i];
            arr[i] = min;
        }

        System.out.println("排序后:");
        System.out.println(Arrays.toString(arr));

    }
}
