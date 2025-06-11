package com.example.ch2labs.labs03;

import java.util.Random;

public class CustomRandom {
    private int min, max;

    private Random random = new Random();
    public CustomRandom(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return random.nextInt(max-min+1)+min+"";
    }
}
