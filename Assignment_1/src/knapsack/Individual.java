package knapsack;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Individual {
	
	// This is the definition of the problem
	public static final int GENE_SIZE = 1000; // Number of possible items
	public static int[] VALUES = new int[GENE_SIZE];
	public static int[] WEIGHTS = new int[GENE_SIZE];
	public static int WEIGHT_LIMIT = 300; 
	
	static {
		// This code initializes the problem.
		Random r = new Random(1L);
		
		for (int i =0; i<GENE_SIZE; i++) {
			VALUES[i] = r.nextInt(100);
			WEIGHTS[i] = r.nextInt(100);
		}
		
		//VALUES = Arrays.stream(VALUES).parallel().map(n -> r.nextInt()).toArray();
		//WEIGHTS = Arrays.stream(WEIGHTS).parallel().map(n->r.nextInt()).toArray();
		/*
		BiFunction<Integer, Integer, Void> func = (a,b) -> {
			System.out.println("adeus");
	
			for (int i = a; i < b;i++) { 
				VALUES[i] = r.nextInt(100);
				WEIGHTS[i] = r.nextInt(100);
			}
			
		  return null;
		};
		
		System.out.println("ola");
		ParallelLib.makeTask(func, GENE_SIZE);
		System.out.println("adeus");
		
		*/
	}
	
	/*
	 *  This array corresponds to whether the object at a given index
	 *  is selected to be placed inside the knapsack.
	 *  The goal is to find the items that maximize the total value with
	 *  surpassing the weight limit.
	 */
	public boolean[] selectedItems = new boolean[GENE_SIZE];
	public int fitness;

	public static Individual createRandom() {
		Random r = new Random();
		Individual ind = new Individual();
		
		for (int i=0; i<GENE_SIZE; i++) {
			ind.selectedItems[i] = r.nextBoolean(); 
		}
		
		//IntStream.range(0, GENE_SIZE).parallel().forEach(i->ind.selectedItems[i] = r.nextBoolean());
		
		/*
		BiFunction<Integer, Integer, Void> func = (a,b) -> { 
			for (int i = a; i < b;i++) { 
				ind.selectedItems[i] = r.nextBoolean(); 
			} 
		  return null;
		};

		ParallelLib.makeTask(func, GENE_SIZE);
		*/
		
		return ind;
		
	}

	/*
	 * This method evaluates how good a solution the current individual is.
	 * Returns +totalValue if within the weight limit, otherwise returns 
	 * -overlimit. The goal is to maximize the fitness.
	 */
	public void measureFitness() {
		int totalWeight = 0;
		int totalValue = 0;
		
		for (int i=0; i<GENE_SIZE; i++) {
			if (selectedItems[i]) {
				totalValue += VALUES[i];
				totalWeight += WEIGHTS[i];
			}
		}
		
		//totalValue = IntStream.range(0, GENE_SIZE).parallel().filter(i ->selectedItems[i]).map(i -> VALUES[i]).sum();
		//totalWeight = IntStream.range(0, GENE_SIZE).parallel().filter(i ->selectedItems[i]).map(i -> WEIGHTS[i]).sum();
		
		/*
		BiFunction<Integer, Integer, Integer[]> func = (a,b) -> {
			
			Integer[] v = new Integer[2];
			v[0] = 0;
			v[1] = 0;
			
			for (int i = a; i < b;i++) { 
				if (selectedItems[i]) {
					v[0] += VALUES[i];
					v[1] += WEIGHTS[i];
				}
			}
			
			return v;
		};
	  			
		int[] weight_Value = paramSum(ParallelLib.makeTask(func, GENE_SIZE));
		
		if (weight_Value[0] > WEIGHT_LIMIT) {
			this.fitness = -(weight_Value[0]-WEIGHT_LIMIT);
		} else {
			this.fitness = weight_Value[1];
		}*/
		
		if (totalWeight > WEIGHT_LIMIT) {
			this.fitness = -(totalWeight-WEIGHT_LIMIT);
		} else {
			this.fitness = totalValue;
		}
	}
	
	/*
	 * retorna na primeira posicao o total de weights e na segunda posicao 
	 * o total de values
	 */
	/*private int[] paramSum(List<Integer[]> list) {
		
		int[] result = new int[2]; 
		
		for (Integer[] v : list) {
			result[0] += v[0];
			result[1] += v[1];
		}
		
		return result;
	}*/

	/*
	 * Generates a random point in the genotype (selected Items)
	 * Until that point, uses genes from dad (current)
	 * After that point, uses genes from mom (mate)
	 */
	public Individual crossoverWith(Individual mate) {
		Random r = new Random();
		Individual child = new Individual();
		int crossoverPoint = r.nextInt(GENE_SIZE);
		
		/*
		BiFunction<Integer,Integer,Void> func = (a,b) -> {
			
			for (int i=a; i<b; i++) {
				if (i<crossoverPoint) {
					child.selectedItems[i] = this.selectedItems[i];
				} else {
					child.selectedItems[i] = mate.selectedItems[i];
				}
			}
			return null;
		};
		*/
		
		for (int i=0; i<GENE_SIZE; i++) {
			if (i<crossoverPoint) {
				child.selectedItems[i] = this.selectedItems[i];
			} else {
				child.selectedItems[i] = mate.selectedItems[i];
			}
		}
		
		//ParallelLib.makeTask(func,GENE_SIZE);
		
		return child;
	}

	public void mutate() {
		Random r = new Random();
		int mutationPoint = r.nextInt(GENE_SIZE);
		this.selectedItems[mutationPoint] = !this.selectedItems[mutationPoint];
	}
}
