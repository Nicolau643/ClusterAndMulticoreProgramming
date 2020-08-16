package knapsack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

public class MergeSort<T> extends RecursiveTask<T>{
	
	private T[] arr;
	private int ini;
	private int end; 
    private Comparator<? super T> comp;
    
    private static final int THRESHOLD = 128;
	
	public MergeSort(T[] arr, int ini, int end,Comparator<? super T> comp) {
		
		this.arr = arr;
		this.ini = ini;
		this.end = end;
		this.comp = comp;
	}

	@Override
	protected T compute() {
		if (ini + 1 < end) {

            if (end - ini < THRESHOLD) {
                insertionSort();
            }else{

                MergeSort<T> m1 = new MergeSort<>(arr, ini, (ini + end) / 2,comp);
                m1.fork();
                MergeSort<T> m2 = new MergeSort<>(arr, ((ini + end) / 2)+1, end,comp);
                m2.fork();
                
                m1.join();
                m2.join();
                
                merge(arr,ini,(ini + end) / 2,end); 
            }
			
			
			
		}
		
		return null;
		
    }
    
    private void insertionSort() {
        for (int i = ini+1; i <= end; ++i) {
            T current = arr[i];
            int j = i-1;
            while (ini <= j && comp.compare(current, arr[j])<0) {
                arr[j+1] = arr[j--];
            }
            values[j+1] = current;
        }
    }

	private void merge(T[] arr2, int ini2, int mid, int end2) {
		
		int l = mid - ini2 + 1;
		int r = end2 - mid;
		
        ArrayList<T> left = new ArrayList<>(l);
        ArrayList<T> right = new ArrayList<>(r);
  
        for (int i=0; i< l; ++i) 
            left.add(arr2[ini2 + i]); 
        for (int j=0; j< r; ++j) 
            right.add(arr2[mid + 1+ j]); 
  
  
        int i = 0; 
        int j = 0;
        int k = ini2;
        
        while (i < l && j < r){ 
            
        	if (comp.compare(left.get(i), right.get(j)) <= 0) { 
                arr2[k] = left.get(i); 
                i++; 
            }else{ 
                arr2[k] = right.get(j); 
                j++; 
            } 
            k++; 
        } 
  
        while (i < l) { 
            arr2[k] = left.get(i); 
            i++; 
            k++; 
        } 
  
        while (j < r){ 
            arr2[k] = right.get(j); 
            j++; 
            k++; 
        } 
        
    } 
		
}
