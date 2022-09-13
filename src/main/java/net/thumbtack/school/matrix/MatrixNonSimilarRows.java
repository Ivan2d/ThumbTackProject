package net.thumbtack.school.matrix;
import java.util.*;

public class MatrixNonSimilarRows {

    private int[][] matrix;

    public MatrixNonSimilarRows(int[][] matrix) {
        this.matrix = matrix;
    }

    public List<int[]> getNonSimilarRows() {
        Set<Set<Integer>> sets = new HashSet<>();
        Map<Set<Integer>, int[]> setMap = new HashMap<>();

        for (int[] array : matrix) {
            Set<Integer> integers = new HashSet<>();
            for (int element : array) {
                integers.add(element);
            }
            sets.add(integers);
            setMap.putIfAbsent(integers, array);
        }

        return new ArrayList<>(setMap.values());
    }


}
