package net.thumbtack.school.database.functional;

import java.util.List;

public class MyStringService{
    public static List<String> split (String str){
        return List.of(str.split(" "));
    }
}