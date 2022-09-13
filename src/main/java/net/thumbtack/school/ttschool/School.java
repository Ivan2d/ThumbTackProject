package net.thumbtack.school.ttschool;
import java.util.*;

public class School {
    private String name;
    private int year;
    private Set<Group> groupSet;

    public School(String name, int year) throws TrainingException {
        if (name == null || name.equals("")) {
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        }
        this.name = name;
        this.year = year;
        groupSet = new TreeSet<>(Comparator.comparing(Group::getName));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("")) {
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        }
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Group> getGroups() {
        return groupSet;
    }

    public void addGroup(Group group) throws TrainingException{
        for(Group group1 : groupSet) {
            if (group.getName().equals(group1.getName())) {
                throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
            }
        }
        this.groupSet.add(group);
    }

    public void removeGroup(Group group) throws TrainingException {
        int i = 0;
        for (Group gr : groupSet) {
            if (gr.equals(group)) {
                i++;
            }
        }
        if(i > 0){
            groupSet.remove(group);
        }
        else {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    public void removeGroup(String name) throws TrainingException {
        Group group = null;
        for (Group gr : groupSet) {
            if (gr.getName().equals(name)) {
                group = gr;
            }
        }
        if(group == null){
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
        else{
            groupSet.remove(group);
        }

    }


    public boolean containsGroup(Group group){
        return groupSet.contains(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year && Objects.equals(name, school.name) && Objects.equals(groupSet, school.groupSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, groupSet);
    }
}
