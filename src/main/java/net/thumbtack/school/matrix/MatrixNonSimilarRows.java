package net.thumbtack.school.matrix;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MatrixNonSimilarRows {

    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    public List<int[]> getNonSimilarRows() {
        List<int[]> result = new CopyOnWriteArrayList<>();
        Set<Integer> Set1 = new HashSet<>();
        Set<Integer> Set2 = new HashSet<>();

        for (int[] element : matrix) {
            result.add(element);
        }

        for (int[] array : matrix) {
            for (int element : array) {
                Set1.add(element);
            }

            boolean checker = false;

            for (int[] array1 : matrix) {
                for (int element1 : array1) {
                    Set2.add(element1);
                }

                if (Set1.equals(Set2)) {
                    if (checker) result.remove(array1);
                    checker = true;
                }
                Set2.clear();
            }
            Set2.clear();
        }
        return result;
    }


}
