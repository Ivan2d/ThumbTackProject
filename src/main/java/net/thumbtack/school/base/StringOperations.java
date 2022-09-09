package net.thumbtack.school.base;

public class StringOperations
{
    public static int getSummaryLength(String[] strings){
        int sum = 0;
        for(String str: strings){
            sum+=str.length();
        }
        return sum;
    }

    public static String getFirstAndLastLetterString(String string){
        String str = String.valueOf(string.charAt(0));
        str+=string.charAt(string.length()-1);
        return str;
    }

    public static boolean isSameCharAtPosition(String string1, String string2, int index){
        return string1.charAt(index) == string2.charAt(index);
    }

    public static boolean isSameFirstCharPosition(String string1, String string2, char character){
        return string1.indexOf(character) == string2.indexOf(character);
    }

    public static boolean isSameLastCharPosition(String string1, String string2, char character){
        return string1.lastIndexOf(character) == string2.lastIndexOf(character);
    }

    public static boolean isSameFirstStringPosition(String string1, String string2, String str){
        return string1.indexOf(str) == string2.indexOf(str);
    }

    public static boolean isSameLastStringPosition(String string1, String string2, String str){
        return string1.lastIndexOf(str) == string2.lastIndexOf(str);
    }

    public static boolean isEqual(String string1, String string2){
        return string1.equals(string2);
    }

    public static boolean isEqualIgnoreCase(String string1, String string2){
        return string1.equalsIgnoreCase(string2);
    }

    public static boolean isLess(String string1, String string2){
        return string1.compareTo(string2) < 0;
    }

    public static boolean isLessIgnoreCase(String string1, String string2){
        return string1.compareToIgnoreCase(string2) < 0;
    }

    public static String concat(String string1, String string2){
        return string1.concat(string2);
    }

    public static boolean isSamePrefix(String string1, String string2, String prefix){
        return string1.startsWith(prefix) && string2.startsWith(prefix);
    }

    public static boolean isSameSuffix(String string1, String string2, String suffix){
        return string1.endsWith(suffix) && string2.endsWith(suffix);
    }

    public static String getCommonPrefix(String string1, String string2) {
        // REVU StringBuilder и цеплять сиволы не нужно
        // просто считайте их, пока ==, а потом substring
        StringBuilder result = new StringBuilder();
        int min = string1.length();
        if (min >= string2.length()) {
            min = string2.length();
        }

        for (int i = 0; i < min; i++) {
            if (string1.charAt(i) == string2.charAt(i)) {
                result.append(string1.charAt(i));
            } else {
                break;
            }
        }
        return result.toString();
    }

    public static String reverse(String string){
       return new StringBuilder(string).reverse().toString();
    }

    public static boolean isPalindrome(String string) {
        // REVU сделайте без reverse
                 return string.replaceAll("\\W","")
                        .equals(new StringBuilder(string.replaceAll("\\W",""))
                        .reverse().toString());
    }

    public static boolean isPalindromeIgnoreCase(String string){
                return string.equalsIgnoreCase(new StringBuilder(string).reverse().toString());
    }

    public static String getLongestPalindromeIgnoreCase(String[] strings) {
        String max = "";
        for (String str : strings) {
            if (isPalindromeIgnoreCase(str) && str.length() >= max.length()) {
                    max = str;
            }
        }
        return max;

    }

    public static boolean hasSameSubstring(String string1, String string2, int index, int length) {
        int str1_len = string1.length();
        int str2_len = string2.length();
        // REVU просто сравните 2 substring

        if (str1_len < index+length || str2_len < index+length){
            return false;
        }

            for (int i = index; i < length; i++) {
                if (string1.charAt(i) != string2.charAt(i)) {
                    return false;
                }
            }
        return true;
    }

    public static boolean isEqualAfterReplaceCharacters(String string1, char replaceInStr1, char replaceByInStr1, String string2, char replaceInStr2, char replaceByInStr2)
    {
        return string1.replace(replaceInStr1, replaceByInStr1).equals(string2.replace(replaceInStr2, replaceByInStr2));
    }

    public static boolean isEqualAfterReplaceStrings(String string1, String replaceInStr1, String replaceByInStr1, String string2, String replaceInStr2, String replaceByInStr2){
        return string1.replaceAll(replaceInStr1, replaceByInStr1).equals(string2.replaceAll(replaceInStr2, replaceByInStr2));
    }

    public static boolean isPalindromeAfterRemovingSpacesIgnoreCase(String string){
        String res = string.replaceAll(" ", "");
        return isPalindromeIgnoreCase(res);
    }

    public static boolean isEqualAfterTrimming(String string1, String string2){
        return string1.trim().equals(string2.trim());
    }

    public static String makeCsvStringFromInts(int[] array) {
// REVU Вызовите аналогичный метод, возвращающий StringBuilder и toString
        if(array == null || array.length == 0)
        {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(array[0]);
        for(int i = 1; i < array.length; i++)
        {
            stringBuilder.append(',');
            stringBuilder.append(array[i]);
        }
        return stringBuilder.toString();
    }

    public static String makeCsvStringFromDoubles(double[] array){
// REVU Вызовите аналогичный метод, возвращающий StringBuilder и toString
        if(array == null || array.length == 0)
        {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%.2f",array[0]));
        for(int i = 1; i < array.length; i++)
        {
            stringBuilder.append(',');
            stringBuilder.append(String.format("%.2f",array[i]));
        }
        return stringBuilder.toString();
    }

    public static StringBuilder makeCsvStringBuilderFromInts(int[] array){
        if(array == null || array.length == 0)
        {
            return new StringBuilder();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(array[0]);
        for(int i = 1; i < array.length; i++)
        {
            stringBuilder.append(',');
            stringBuilder.append(array[i]);
        }
        return stringBuilder;

    }

    public static StringBuilder makeCsvStringBuilderFromDoubles(double[] array)
    {
        if(array == null || array.length == 0)
        {
            return new StringBuilder();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%.2f",array[0]));
        for(int i = 1; i < array.length; i++)
        {
            stringBuilder.append(',');
            stringBuilder.append(String.format("%.2f",array[i]));
        }
        return stringBuilder;
    }

    public static StringBuilder removeCharacters(String string, int[] positions) {
        StringBuilder sb = new StringBuilder(string);
        for (int i = positions.length-1; i >= 0; i--) {
            sb.deleteCharAt(positions[i]);
        }
        return sb;
    }

    public static StringBuilder insertCharacters(String string, int[] positions, char[] characters){
        StringBuilder stringBuilder = new StringBuilder(string);
        for(int i = positions.length-1; i >= 0; i--){
            stringBuilder.insert(positions[i], characters[i]);
        }
        return stringBuilder;
    }













}

