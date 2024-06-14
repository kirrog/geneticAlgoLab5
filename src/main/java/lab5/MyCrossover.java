package lab5;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyCrossover extends AbstractCrossover<MySolution> {
    protected MyCrossover() {
        super(1);
    }

    protected List<MySolution> mate(MySolution p1, MySolution p2, int i, Random random) {
        ArrayList<MySolution> children = new ArrayList<>(2);
        int[][] solutionData1 = p1.getSolutionCoordinates();
        int[][] solutionData2 = p2.getSolutionCoordinates();
        MySolution newSol1 = new MySolution(solutionData1.length);
        MySolution newSol2 = new MySolution(solutionData2.length);
        newSol1.setSolutionCoordinates(Arrays.copyOf(solutionData1, solutionData1.length));
        newSol2.setSolutionCoordinates(Arrays.copyOf(solutionData2, solutionData2.length));

        children.add(newSol1);
        children.add(newSol2);
        return children;
    }
}
