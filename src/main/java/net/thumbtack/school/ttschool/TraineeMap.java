package net.thumbtack.school.ttschool;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TraineeMap {
    private Map<Trainee, String> traineeStringMap;

    public TraineeMap(){
        traineeStringMap = new HashMap<>();
    }

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if(traineeStringMap.containsKey(trainee)){
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
        traineeStringMap.put(trainee, institute);
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if(traineeStringMap.containsKey(trainee)){
            traineeStringMap.put(trainee, institute);
        }
        else{
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        int i = 0;
        for (Trainee tr : traineeStringMap.keySet()) {
            if (tr.equals(trainee)) {
                i++;
            }
        }

        if (i == 0) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        } else {
            traineeStringMap.remove(trainee);
        }
    }

    public int getTraineesCount(){
       return traineeStringMap.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        if(!traineeStringMap.containsKey(trainee)){
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return traineeStringMap.get(trainee);
    }

    public Set<Trainee> getAllTrainees(){
        return traineeStringMap.keySet();
    }

    public Set<String> getAllInstitutes(){
        Set<String> set = new HashSet<>();
        for(Map.Entry<Trainee, String> entry: traineeStringMap.entrySet()){
            set.add(entry.getValue());
        }
        return set;
    }

    public boolean isAnyFromInstitute(String institute){
       return traineeStringMap.containsValue(institute);
    }
}
