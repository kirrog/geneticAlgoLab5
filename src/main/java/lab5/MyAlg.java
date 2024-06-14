package lab5;

import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MyAlg {

    public static void main(String[] args) {
        int dimension = 16; // dimension of problem
        int populationSize = 5; // size of population
        int generations = 1000; // number of generations

        Random random = new Random(); // random

        CandidateFactory<MySolution> factory = new MyFactory(dimension); // generation of solutions

        ArrayList<EvolutionaryOperator<MySolution>> operators = new ArrayList<EvolutionaryOperator<MySolution>>();
        operators.add(new MyCrossover()); // Crossover
        operators.add(new MyMutation()); // Mutation
        EvolutionPipeline<MySolution> pipeline = new EvolutionPipeline<MySolution>(operators);

        SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

        FitnessEvaluator<MySolution> evaluator = new FitnessFunction(dimension); // Fitness function

        EvolutionEngine<MySolution> algorithm = new SteadyStateEvolutionEngine<MySolution>(
                factory, pipeline, evaluator, selection, populationSize, false, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                double mean = populationData.getMeanFitness();
                double std = populationData.getFitnessStandardDeviation();
                System.out.println("Generation " + populationData.getGenerationNumber() +
                        ": Best fit: " + bestFit +
                        " Mean fit: " + mean +
                        " Std: " + std);
                System.out.println("\tBest solution = " + (MySolution) populationData.getBestCandidate());
                System.out.println("\tPop size = " + populationData.getPopulationSize());
            }
        });

//        TerminationCondition terminate = new GenerationCount(generations);
        TerminationCondition terminate = new TargetFitness(0.0, false);
        algorithm.evolve(populationSize, 1, terminate);

    }
}
