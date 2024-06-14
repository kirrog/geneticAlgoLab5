package lab5;

import java.util.ArrayList;
import java.util.Arrays;

public class MySolution {
    private int[][] solutionCoordinates;

    public MySolution(int size) {
        this.solutionCoordinates = new int[size][2];
        for (int i = 0; i < size; i++) {
            this.solutionCoordinates[i][0] = i + 1;
            this.solutionCoordinates[i][1] = i + 1;
        }
    }

    public int[][] getSolutionCoordinates() {
        return solutionCoordinates;
    }

    public void setSolutionCoordinates(int[][] solutionCoordinates) {
        this.solutionCoordinates = solutionCoordinates;
    }

    @Override
    public String toString() {
        ArrayList<String> result = new ArrayList<>();
        for (int[] solutionCoordinate : this.solutionCoordinates) {
            result.add(Arrays.toString(solutionCoordinate));
        }
        return String.join(",", result);
    }
}
