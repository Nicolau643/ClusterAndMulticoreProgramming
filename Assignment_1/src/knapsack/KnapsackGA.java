package knapsack;

import java.util.Comparator;
import java.util.Random;
import java.util.function.BiFunction;

public class KnapsackGA {
	private static final int N_GENERATIONS = 100;
	private static final int POP_SIZE = 100000;
	private static final double PROB_MUTATION = 0.5;
	
	private Random r = new Random();
	
	private Individual[] population = new Individual[POP_SIZE];
	
	public KnapsackGA() {
		populateInitialPopulationRandomly();
	}

	private void populateInitialPopulationRandomly() {
		/*
		for (int i=0; i<POP_SIZE; i++) {
			population[i] = Individual.createRandom();
		}*/
		
		BiFunction<Integer, Integer, Void> func = (a,b) -> { 
			for (int i = a; i < b;i++) { 
				population[i] = Individual.createRandom();
			} 
		  return null;
		};
	  
		ParallelLib.makeTask(func, POP_SIZE);
		

	}

	public void run() {
		
		Sorter s = new Sorter();
		
		for (int generation=0; generation<N_GENERATIONS; generation++) {

			// Step1 - Calculate Fitness
			/*
			for (int i=0; i<POP_SIZE; i++) { 
				population[i].measureFitness(); 
			}*/
			
			
			BiFunction<Integer, Integer, Void> func = (a,b) -> { 
				for (int i = a; i < b;i++) { 
					population[i].measureFitness();
				} 
			  return null;
			};
		  
			ParallelLib.makeTask(func, POP_SIZE);
			
			// Step2 - Sort by Fitness descending
			
			s.sort(population, new Comparator<Individual>() {
				@Override
				public int compare(Individual o1, Individual o2) {
					if (o1.fitness > o2.fitness) return -1;
					if (o1.fitness < o2.fitness) return 1;
					return 0;
				}
			});
			/*
			Arrays.sort(population, new Comparator<Individual>() {
				@Override
				public int compare(Individual o1, Individual o2) {
					if (o1.fitness > o2.fitness) return -1;
					if (o1.fitness < o2.fitness) return 1;
					return 0;
				}
			});*/
			
			// Debug
			
			System.out.println("Best fitness at " + generation + " is " + population[0].fitness);
			
			// Step3 - Find parents to mate (cross-over)
			Individual[] newPopulation = new Individual[POP_SIZE];
			newPopulation[0] = population[0]; // The best individual remains
			
			/*
			for (int i=1; i<POP_SIZE; i++) {
				// The first elements in the population have higher probability of being selected
				int pos1 = (int) (- Math.log(r.nextDouble()) * POP_SIZE) % POP_SIZE;
				int pos2 = (int) (- Math.log(r.nextDouble()) * POP_SIZE) % POP_SIZE;
				
				newPopulation[i] = population[pos1].crossoverWith(population[pos2]);
			}
			*/
			
			func = (a,b) -> { 
				for (int i = a; i < b;i++) { 
					// The first elements in the population have higher probability of being selected
					int pos1 = (int) (- Math.log(r.nextDouble()) * POP_SIZE) % POP_SIZE;
					int pos2 = (int) (- Math.log(r.nextDouble()) * POP_SIZE) % POP_SIZE;
					
					newPopulation[i] = population[pos1].crossoverWith(population[pos2]);
				} 
			  return null;
			};
		  
			ParallelLib.makeTask(func, POP_SIZE);
			
			// Step4 - Mutate
			/*for (int i=1; i<POP_SIZE; i++) {
				if (r.nextDouble() < PROB_MUTATION) {
					newPopulation[i].mutate();
				}
			}*/
			
			func = (a,b) -> { 
				for (int i = a; i < b;i++) { 
					if (r.nextDouble() < PROB_MUTATION) {
						newPopulation[i].mutate();
					}
				} 
			  return null;
			};
			
			ParallelLib.makeTask(func, POP_SIZE);
			
			population = newPopulation;
		}
	}

}
