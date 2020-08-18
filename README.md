# ClusterAndMulticoreProgramming

This assignments and report were made in a Master's Degree. The assignments were made in Java so the parallelization/concurrency made is only in Java programming language. 

# ParallelLib
The ParallelLib is a library that we can use to parallelize essentially arrays by divide them equaly with the threads that the machine has. You can see examples of use in the Assignment1.

# Assignment1

In this assignment I implemented various examples of parallelization/concurrency using the ParallelLib library, java [synchronized](https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html) and use the [ForkJoin Framework](https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html) in Knapsack and Nbody problems. For details, see the report in this assignment folder.

# Assignment2

I implemented the actor model in this assignment. It consists in split the work that has to be done in various actors and they connect with each other sendin messages and with this approach we can have parallelism, improve time of execution and is very useful for concurrent systems.For details, see the report in this assignment folder.

# Final Report

The goal of this final project is to implement a tool that has the objective of transform a sequecial version into a parallel one using [Future](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Future.html) interface and [Spoon] (http://spoon.gforge.inria.fr). Because we didn't had enought time to do more, we focus on recursive problems.
