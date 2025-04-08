/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank;

public class test {
    public static void main(String[] args) {
        System.out.println(new test().generateAccountNumber());
    }

    private String generateAccountNumber() {
        return String.format("%011d", Math.abs(new java.util.Random().nextLong() % 1_000_000_000_00L));
    }
}
