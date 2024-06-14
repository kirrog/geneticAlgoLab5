package lab5;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class MyFactory extends AbstractCandidateFactory<MySolution> {

    private final int dimension;

    public MyFactory(int dimension) {
        this.dimension = dimension;
    }

    public MySolution generateRandomCandidate(Random random) {
        MySolution solution = new MySolution(dimension);
        shuffleArray(solution.getSolutionCoordinates());
        return solution;
    }

    static void shuffleArray(int[][] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index][0];
            ar[index][0] = ar[i][0];
            ar[i][0] = a;

            index = rnd.nextInt(i + 1);
            // Simple swap
            a = ar[index][1];
            ar[index][1] = ar[i][1];
            ar[i][1] = a;
        }
    }
}

