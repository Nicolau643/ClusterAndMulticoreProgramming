package knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class ParallelLib {
	
	public static <T> List<T> makeTask(BiFunction<Integer, Integer, T> task, int interations) {
			
			int Nthreads = Runtime.getRuntime().availableProcessors();
			
			Thread[] threads = new Thread[Nthreads];
			
			List<T> l = new ArrayList<T>();
			
			for (int i = 0; i < Nthreads; i++) {
			
				final int iInside = i;
				Runnable r = () -> {
					
					int startIndex = iInside * interations / Nthreads;
					int endIndex = (iInside + 1) * interations / Nthreads;
					T res = task.apply(startIndex, endIndex);
					
					l.add(res);
					
					
				};
				
				threads[iInside] = new Thread(r);
				threads[iInside].start();
				
			}
			
			
			
			Arrays.stream(threads).forEach(t->{
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			
			
			
			return l; 
			
		}

}
