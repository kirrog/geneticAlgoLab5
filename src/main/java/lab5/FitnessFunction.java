package lab5;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.List;
import java.util.Random;

public class FitnessFunction implements FitnessEvaluator<MySolution> {

    public FitnessFunction(int dimension) {
        this.dimension = dimension;
    }

    private int dimension;
    private double best_result = 0.0;

    public double getFitness(MySolution solution, List<? extends MySolution> list) {
        int[][] solutionArray = solution.getSolutionCoordinates();
        double result = 0.0;

        for (int i = 0; i < solutionArray.length; i++) {
            for (int j = i+1; j < solutionArray.length; j++) {
                int x1 = solutionArray[i][0];
                int y1 = solutionArray[i][1];
                int x2 = solutionArray[j][0];
                int y2 = solutionArray[j][1];

                if (x1 == x2) {
                    result += 1;
                }

                if (y1 == y2) {
                    result += 1;
                }

                if (x1 + y1 == x2 + y2) {
                    result += 1;
                }
                if (x1 - y1 == x2 - y2) {
                    result += 1;
                }
            }
        }
//        if (result == 1.0){
//            int a = 0;
//        }

        return result;
    }


    public boolean isNatural() {
        return false;
    }
}
