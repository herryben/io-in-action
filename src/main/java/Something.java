package main.java;

import org.junit.Test;

import java.util.Arrays;

public class Something {
    @Test
    public void minDiff(){
        int[] a = {-2147483648,1};
        int[] b = {2147483647,0};
        Arrays.sort(a);
        Arrays.sort(b);
        long diff = Integer.MIN_VALUE;
        int i = 0 ,j = 0;
        while(i < a.length && j < b.length){
            diff = Math.min(Math.abs(diff), Math.min(Math.abs(a[i] - b[j]), Math.abs(b[i] - a[i])));
            if(a[i] < b[j]){
                i++;
            }else {
                j++;
            }
        }
        System.out.println(diff);
    }

    @Test
    public void testSearch(){
        System.out.println(search(new int[]{5,7,7,8,8,10}, 8));
    }

    public int search(int[] nums, int target) {
        return rightBound(nums, target) - leftBound(nums, target);
    }

    private int leftBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] == target) {
                right = mid - 1;
            }
        }
        return left;
    }

    public int rightBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] == target) {
                left = mid + 1;
            }
        }
        return left;
    }
}
