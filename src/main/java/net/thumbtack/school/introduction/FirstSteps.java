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
        int sumArr = 0;
        if(array.length == 0){
            return sumArr;
        }
        for(int item: array){
            sumArr+=item;
        }
        return sumArr;
    }

    public int mul(int[] array){
        if(array.length == 0){
            return 0;
        }
        int sumArr = 1;
        for(int item: array){
            sumArr*=item;
        }
        return sumArr;
    }

    public int min(int[] array){
        if(array.length == 0){
            return Integer.MAX_VALUE;
        }

        int minArr = array[0];
        for(int item: array){
            if (minArr >= item){
                minArr = item;
            }
        }
        return minArr;
    }

    public int max(int[] array){
        if(array.length == 0){
            return Integer.MIN_VALUE;
        }

        int maxArr = array[0];
        for(int item: array){
            if (maxArr <= item){
                maxArr = item;
            }
        }
        return maxArr;
    }

    public double average(int[] array){
        if(array.length == 0){
            return 0;
        }

        // REVU вызовите sum
        double average = sum(array);
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
            array[i] = array[i]*array[i]*array[i];
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
        int res = 0;
        for (int[] item: matrix)
        {
            res += sum(item);
        }
        return res;
    }

    public int max(int[][] matrix) {

        Integer max = null;
        // REVU аналогично
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                if (max == null) max = anInt;
                if (anInt > max) {
                    max = anInt;
                }
            }
        }
        max = max != null ? max : Integer.MIN_VALUE;
        return max;

    }


    public int diagonalMax(int[][] matrix) {
            if (matrix.length == 0 || matrix.length == 1)
                return Integer.MIN_VALUE;
            else {
                int max = matrix[0][0];
                for (int i = 0; i < matrix.length; i++) {
                    if (max < matrix[i][i]) max = matrix[i][i];
                }
                return max;
            }
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
