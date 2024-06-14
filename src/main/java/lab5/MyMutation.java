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
        for (MySolution solution : population) {
            mutateSolution(solution, random);
        }
        return population;
    }

    private int chooseFigure(int[][] solutionCoordinates) {
        int[] result = new int[solutionCoordinates.length];

        for (int i = 0; i < solutionCoordinates.length; i++) {
            for (int j = i + 1; j < solutionCoordinates.length; j++) {
                int x1 = solutionCoordinates[i][0];
                int y1 = solutionCoordinates[i][1];
                int x2 = solutionCoordinates[j][0];
                int y2 = solutionCoordinates[j][1];

                if (x1 == x2) {
                    result[i] += 1;
                    result[j] += 1;
                }

                if (y1 == y2) {
                    result[i] += 1;
                    result[j] += 1;
                }

                if (x1 + y1 == x2 + y2) {
                    result[i] += 1;
                    result[j] += 1;
                }
                if (x1 - y1 == x2 - y2) {
                    result[i] += 1;
                    result[j] += 1;
                }
            }
        }


        int figure = 0;
        int maxNumOfCollisions = 0;

        for (int i = 0; i < solutionCoordinates.length; i++) {
            int curNumOfCollisions = result[i];
            if (maxNumOfCollisions < curNumOfCollisions) {
                figure = i;
                maxNumOfCollisions = curNumOfCollisions;
            }
        }



        return figure;
    }

    // # - coordinates start, * - figure
    //#
    // 012
    // 3*4
    // 567
    private void mutateSolution(MySolution solution, Random random) {
        int[][] solutionCoordinates = solution.getSolutionCoordinates();

        int figure = chooseFigure(solutionCoordinates);

        int x1 = solutionCoordinates[figure][0];
        int y1 = solutionCoordinates[figure][1];

        boolean[] orders2move = new boolean[4];

        int[] borders_distance = new int[8];

        borders_distance[1] = y1 - 1;
        borders_distance[3] = x1 - 1;
        borders_distance[4] = solutionCoordinates.length - x1;
        borders_distance[6] = solutionCoordinates.length - y1;

        borders_distance[0] = Math.min(borders_distance[1], borders_distance[3]);
        borders_distance[2] = Math.min(borders_distance[1], borders_distance[4]);
        borders_distance[5] = Math.min(borders_distance[3], borders_distance[6]);
        borders_distance[7] = Math.min(borders_distance[4], borders_distance[6]);


        for (int j = 0; j < solutionCoordinates.length; j++) {
            if (figure == j) {
                continue;
            }
            int x2 = solutionCoordinates[j][0];
            int y2 = solutionCoordinates[j][1];

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

        ArrayList<Integer> ordersVariants = new ArrayList<>();

        if (orders2move[0] && orders2move[1] && orders2move[2] && orders2move[3]) {
            ordersVariants.add(0);
            ordersVariants.add(1);
            ordersVariants.add(2);
            ordersVariants.add(3);
        } else {
            for (int i = 0; i < orders2move.length; i++) {
                if (!orders2move[i]) {
                    ordersVariants.add(i);
                }
            }
        }

        int order = ordersVariants.get(random.nextInt(ordersVariants.size()));
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
        solutionCoordinates[figure][0] = x1;
        solutionCoordinates[figure][1] = y1;


        solution.setSolutionCoordinates(solutionCoordinates);
    }
}
