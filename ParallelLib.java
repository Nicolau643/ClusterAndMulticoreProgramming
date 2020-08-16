package nbody;

import java.util.Arrays;
import java.util.function.BiFunction;

public class ParallelLib {
	
	public static void makeTask(BiFunction<Integer, Integer, Void> task, int interations) {
			
			int Nthreads = Runtime.getRuntime().availableProcessors();
			
			Thread[] threads = new Thread[Nthreads];
					
			for (int i = 0; i < Nthreads; i++) {
			
				final int iInside = i;
				Runnable r = () -> {
					
					int startIndex = iInside * interations / Nthreads;
					int endIndex = (iInside + 1) * interations / Nthreads;
					task.apply(startIndex, endIndex);
					
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
			
		}

}
