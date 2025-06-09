package sk.kuchta.eshop.utility;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {
    public static int getRandomNumber(int Min, int Max) {
        return ThreadLocalRandom.current().nextInt(Min, Max + 1);
    }
}
