package lab5;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyMutation implements EvolutionaryOperator<MySolution> {

    private double changeCoefficient = 0.5;

    public void setChangeCoefficient(double changeCoefficient) {
        this.changeCoefficient = changeCoefficient;
    }

    public List<MySolution> apply(List<MySolution> population, Random random) {
        ArrayList<MySolution> result = new ArrayList<>(population.size());
        for (MySolution solution : population) {
            int[][] solutionData = solution.getSolutionCoordinates();
            MySolution newSol = new MySolution(solutionData.length);
            newSol.setSolutionCoordinates(Arrays.copyOf(solutionData, solutionData.length));
            mutateSolution(newSol, random);
            result.add(newSol);
        }

        return population;
    }

    // # - coordinates start, * - figure
    //#
    // 012
    // 3*4
    // 567
    private void mutateSolution(MySolution solution, Random random) {
        int[][] solutionPath = solution.getSolutionCoordinates();
        int figure = random.nextInt(solutionPath.length);

        int x1 = solutionPath[figure][0];
        int y1 = solutionPath[figure][1];

        boolean[] orders2move = new boolean[4];

        int[] borders_distance = new int[8];

        borders_distance[1] = y1 - 1;
        borders_distance[3] = x1 - 1;
        borders_distance[4] = solutionPath.length - x1;
        borders_distance[6] = solutionPath.length - y1;

        borders_distance[0] = Math.min(borders_distance[1], borders_distance[3]);
        borders_distance[2] = Math.min(borders_distance[1], borders_distance[4]);
        borders_distance[5] = Math.min(borders_distance[3], borders_distance[6]);
        borders_distance[7] = Math.min(borders_distance[4], borders_distance[6]);


        for (int j = 0; j < solutionPath.length; j++) {
            if (figure == j) {
                continue;
            }
            int x2 = solutionPath[j][0];
            int y2 = solutionPath[j][1];

            if (x1 == x2) {
                orders2move[1] = true;
                if (y1 > y2) {
                    borders_distance[1] = y1 - y2 - 1;
                } else {
                    borders_distance[6] = y2 - y1 - 1;
                }
            }

            if (y1 == y2) {
                orders2move[3] = true;
                if (x1 > x2) {
                    borders_distance[3] = x1 - x2 - 1;
                } else {
                    borders_distance[4] = x2 - x1 - 1;
                }
            }

            if (x1 + y1 == x2 + y2) {
                orders2move[2] = true;
                if (x1 > x2) {
                    borders_distance[5] = Math.min(x1 - x2 - 1, y2 - y1 - 1);
                } else {
                    borders_distance[2] = Math.min(x2 - x1 - 1, y1 - y2 - 1);
                }
            }

            if (x1 - y1 == x2 - y2) {
                orders2move[0] = true;
                if (x1 > x2) {
                    borders_distance[0] = Math.min(x1 - x2 - 1, y1 - y2 - 1);
                } else {
                    borders_distance[7] = Math.min(x2 - x1 - 1, y2 - y1 - 1);
                }
            }
        }

        ArrayList<Integer> orders_choosed = new ArrayList<>();

        if (orders2move[0] && orders2move[1] && orders2move[2] && orders2move[3]) {
            orders_choosed.add(0);
            orders_choosed.add(1);
            orders_choosed.add(2);
            orders_choosed.add(3);
        } else if (!(orders2move[0] || orders2move[1] || orders2move[2] || orders2move[3])) {
            return;
        } else {
            for (int i = 0; i < orders2move.length; i++) {
                if (!orders2move[i]) {
                    orders_choosed.add(i);
                }
            }
        }

        int order = orders_choosed.get(random.nextInt(orders_choosed.size()));
        int order_side = random.nextInt(2);

        switch (order) {
            case (0):
                if (order_side == 0) {
                    int steps = borders_distance[0];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    x1 -= step2do;
                    y1 -= step2do;
                } else {
                    int steps = borders_distance[7];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    x1 += step2do;
                    y1 += step2do;
                }
                break;
            case (1):
                if (order_side == 0) {
                    int steps = borders_distance[1];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    y1 -= step2do;
                } else {
                    int steps = borders_distance[6];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    y1 += step2do;
                }
                break;
            case (2):
                if (order_side == 0) {
                    int steps = borders_distance[2];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    x1 += step2do;
                    y1 -= step2do;
                } else {
                    int steps = borders_distance[5];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    x1 -= step2do;
                    y1 += step2do;
                }
                break;
            case (3):
                if (order_side == 0) {
                    int steps = borders_distance[3];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    x1 -= step2do;
                } else {
                    int steps = borders_distance[4];
                    if (steps == 0) {
                        break;
                    }
                    int step2do = random.nextInt(steps);
                    x1 += step2do;
                }
                break;
            default:
                break;
        }
        solutionPath[figure][0] = x1;
        solutionPath[figure][1] = y1;


        solution.setSolutionCoordinates(solutionPath);
    }
}
