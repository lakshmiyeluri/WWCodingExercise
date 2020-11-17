package ww;

import java.util.Random;

public class WW_smallest_number {

	public static void main(String[] args) {

		int startcounter = 0;
		int n = 0;
		int endCounter = 499;
		int arraysize = endCounter - startcounter;
		double numbers[] = new double[arraysize + 1];
		Random rnum = new Random();
		for (int counter = startcounter; counter <= endCounter; counter++) {
			int c = n++;
			double num = rnum.nextDouble();
			numbers[c] = num;
			System.out.println(numbers[c]);

		}
		double smallest = numbers[0];

		for (int i = 1; i < numbers.length; i++) {
			if (numbers[i] < smallest)
				smallest = numbers[i];
		}
		System.out.println("Smallest Number is : " + smallest);
	}
}