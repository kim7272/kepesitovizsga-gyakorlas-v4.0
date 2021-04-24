package hu.nive.ujratervezes.kepesitovizsga.sumofdigits;

import java.util.Random;

public class SumOfDigits {

    public int getSumOfDigits(Random random) {
        int sum = 0;
        int number = -1 * random.nextInt();
        while (Integer.toString(number).length() > 1) {
            sum = getSumOfDigits(sum, number);
            number = sum;
            sum = 0;
        }
        return number;
    }

    private int getSumOfDigits(int sum, int number) {
        while (number >= 1) {
            sum = sum + number % 10;
            number = number /10;
        }
        return sum;
    }
}
