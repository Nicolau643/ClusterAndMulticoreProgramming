package knapsack;

import java.util.Comparator;
import java.util.Random;

public class Sorter {
		
	public <T> T[] sort(T[] arr, Comparator<? super T> comp) {
		
		Random r = new Random();
		
		if (r.nextInt(2) == 1) {
			return executeMergeSort(arr, 0,arr.length-1,comp);
		}
		return executeQuickSort(arr,0,arr.length-1,comp);
		
		
	}

	private static <T> T[] executeMergeSort(T[] arr, int ini, int end,Comparator<? super T> comp) {
		
		MergeSort<T> m = new MergeSort<>(arr,ini,end,comp);
		m.compute();
		
		
		return arr;
		
		
	}

	private static <T> T[] executeQuickSort(T[] arr, int ini, int end,Comparator<? super T> comp) {
		
		QuickSort<T> q = new QuickSort<>(arr,ini,end,comp);
		q.compute();
		return arr;
		
		
	}
	
	
}


