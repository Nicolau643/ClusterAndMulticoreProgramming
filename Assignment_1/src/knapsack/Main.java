package knapsack;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

		long[] a = new long[5];
		

		for (int i = 0; i < 5; i++) {
			long t = System.nanoTime();
			KnapsackGA ga = new KnapsackGA();
			ga.run();
			a[i]= (System.nanoTime() - t);
		}

		System.out.println("Time:" + (Arrays.stream(a).sum() / 5));
	}
}
