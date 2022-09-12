package net.thumbtack.school.ttschool;
import java.util.*;

public class Group {
    private String name;
    private String room;
    private List<Trainee> list;

    public Group(String name, String room) throws TrainingException{
        if(name == null || name.equals("")){
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
        if(room == null || room.equals("")){
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
        this.list = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void setName(String name) throws TrainingException{
        if(name == null || name.equals("")){
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
    }

    public String getRoom(){
        return room;
    }

    public void setRoom(String room) throws TrainingException{
        if(room == null || room.equals("")){
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
    }

    public List<Trainee> getTrainees(){
        return list;
    }

    public void addTrainee(Trainee trainee){
        list.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException{
        if(!list.contains(trainee)){
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        list.remove(trainee);
    }

    public void removeTrainee(int index) throws TrainingException{
        if(index >= list.size() || list.remove(index) == null)
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        for (Trainee trainee : list) {
            if (trainee.getFirstName().equals(firstName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException{
        for(Trainee trainee : list) {
            if (trainee.getFullName().equals(fullName)) {
                return trainee;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant(){
        list.sort(Comparator.comparing(Trainee::getFirstName));
    }

    public void sortTraineeListByRatingDescendant(){
        list.sort(Comparator.comparing(Trainee::getRating).reversed());
    }

    public void reverseTraineeList(){
        Collections.reverse(list);
    }

    public void rotateTraineeList(int index){
        Collections.rotate(list, index);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException{
        if (list.size() == 0) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }

        List<Trainee> list1 = new ArrayList<>();

        int max = 0;

        for(Trainee trainee : list) {
            if (trainee.getRating() > max) {
                max = trainee.getRating();
            }
        }

        for(Trainee trainee : list) {
            if (trainee.getRating() == max)
            {
                list1.add(trainee);
            }
        }

        return list1;
    }

    public boolean hasDuplicates(){
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < list.size(); j++){
                if(list.get(i).equals(list.get(j)) && i != j)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) && Objects.equals(room, group.room) && Objects.equals(list, group.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, list);
    }
}
