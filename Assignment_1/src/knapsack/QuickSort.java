package knapsack;

import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

public class QuickSort<T> extends RecursiveTask<T>{
	
	private T[] arr;
	private int ini;
	private int last;
	private Comparator<? super T> comp;

	public QuickSort(T[] arr, int ini, int end,Comparator<? super T> comp) {
		
		this.arr = arr;
		this.ini = ini;
		this.last = end;
		this.comp = comp;
		
	}

	@Override
	protected T compute() {
		if (ini < last) {
			
			int p = partition(arr,ini,last);
			
			QuickSort<T> q1 = new QuickSort<>(arr, ini, p-1, comp);
			QuickSort<T> q2 = new QuickSort<>(arr, p+1, last, comp);
			
			q1.fork();
			q2.fork();
			
			q1.join();
			q2.join();
			
		}
		
		
		return null;
	}

	private int partition(T[] array, int begin, int end) {
		
		T pivot = array[begin + (end - begin) / 2];
		int i = begin - 1;
		int j = end + 1;
		
		while (true) {
			
			do {
				i++;
			}while(comp.compare(array[i], pivot) < 0);
			
			do {
				j--;
			}while(comp.compare(array[j], pivot) > 0);
			
			if (i >= j) {
				return j;
			}
			
			T temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			
		}
		
	}
	
	 

}
