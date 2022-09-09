package net.thumbtack.school.introduction;

public class FirstSteps
{
    public int sum(int x, int y){
        return x+y;
    }

    public int mul(int x, int y){
        return x*y;
    }

    public int div(int x, int y){
        if(y == 0) { return Integer.MAX_VALUE;}
        return x/y;
    }

    public int mod(int x, int y){
        if(y == 0) {return Integer.MAX_VALUE;}
        return x%y;
    }

    public boolean isEqual(int x, int y){
        return x == y;
    }

    public boolean isGreater(int x, int y){
        return x > y;
    }

    public boolean isInsideRect(int xLeft, int yTop, int xRight, int yBottom, int x, int y){
        // REVU сразу return ...
        if (xLeft < xRight && yTop < yBottom){
            if( (xLeft <= x && x <= xRight) && (yTop <= y && yBottom >= y) ){
                return true;
            }
        }
        return false;
    }

    public int sum(int[] array)
    {
        // REVU не используйте подчеркивание
        // camelCase, то есть sumArr
        // здесь и везде
        int sum_arr = 0;
        if(array.length == 0){
            return sum_arr;
        }
        for(int item: array){
            sum_arr+=item;
        }
        return sum_arr;
    }

    public int mul(int[] array){
        if(array.length == 0){
            return 0;
        }
        int sum_arr = 1;
        for(int item: array){
            sum_arr*=item;
        }
        return sum_arr;
    }

    public int min(int[] array){
        if(array.length == 0){
            return Integer.MAX_VALUE;
        }

        int min_arr = array[0];
        for(int item: array){
            if (min_arr >= item){
                min_arr = item;
            }
        }
        return min_arr;
    }

    public int max(int[] array){
        if(array.length == 0){
            return Integer.MIN_VALUE;
        }

        int max_arr = array[0];
        for(int item: array){
            if (max_arr <= item){
                max_arr = item;
            }
        }
        return max_arr;
    }

    public double average(int[] array){
        if(array.length == 0){
            return 0;
        }

        // REVU вызовите sum
        double average = 0;
        for(int item: array){
                average += item;
        }
        average/= array.length;
        return average;
    }

    public boolean isSortedDescendant(int[] array){
        for(int i = 0; i < array.length-1; ++i){
            if(array[i] <= array[i+1]){
                return false;
            }
        }
        return true;
    }

    public void cube(int[]array){
        for(int i =0; i < array.length; i++){
            // REVU умножение быстрее, *=
            array[i]= (int) Math.pow(array[i], 3);
        }
    }

    public boolean find(int[]array, int value){
        for(int item: array){
            if(item == value){
                return true;
            }
        }
        return false;
    }

    public void reverse(int[]array)
    {
        int temp;
        for(int i = 0; i < div(array.length,2); i++){
                temp = array[i];
                array[i] = array[array.length -1 -i];
                array[array.length-1 -i] = temp;
            }
    }

    public boolean isPalindrome(int[]array)
    {
        for(int i = 0; i < div(array.length,2); i++)
        {
            if (array[i] != array[array.length -1 -i]){
                return false;
            }
        }
        return true;
    }

    public int sum(int[][] matrix){
        int sum = 0;
        int max_lenght = matrix.length;
        // REVU for each
        for (int i = 0; i < max_lenght; i++) {
            // REVU вызовите sum для линейного массива
            for (int j = 0; j < max_lenght; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public int max(int[][] matrix) {

        Integer max = null;
        // REVU аналогично
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(max == null) max = matrix[i][j];
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        max = max != null ? max : Integer.MIN_VALUE;
        return max;

    }


    public int diagonalMax(int[][] matrix) {
        Integer max = null;
        // REVU двойной цикл не нужен
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(max == null) max = matrix[i][j];
                if (i == j && matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        max = max != null ? max : Integer.MIN_VALUE;
        return max;
    }

    public boolean isSortedDescendant(int[][] matrix){
        for(int[] item: matrix){
            if(item.length == 0){
                continue;
            }
            if(!isSortedDescendant(item)){
                return false;
            }
        }
        return true;
    }














}
