package com.dimash.securityApp;

import java.util.*;

public class justFor {
    public static void main(String[] args) {
        int[] sum = new int[]{1, 2, 3, 4};
        System.out.println(containsDuplicate(sum));
    }

    public static boolean containsDuplicate(int[] nums) {
        if (Arrays.stream(nums).distinct().count()!=nums.length) {
            return true;
        } else {
            return false;
        }
    }
}
