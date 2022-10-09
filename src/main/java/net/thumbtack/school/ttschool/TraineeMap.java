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

    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException
    {
        if(traineeStringMap.putIfAbsent(trainee, institute) != null){
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
    }

    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if(!(traineeStringMap.replace(trainee, traineeStringMap.get(trainee), institute)))
        {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        if(traineeStringMap.remove(trainee) == null)
        {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    public int getTraineesCount(){
       return traineeStringMap.size();
    }

    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        if(traineeStringMap.get(trainee) == null){
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return traineeStringMap.get(trainee);
    }

    public Set<Trainee> getAllTrainees(){
        return traineeStringMap.keySet();
    }

    public Set<String> getAllInstitutes(){
        return new HashSet<>(traineeStringMap.values());
    }

    public boolean isAnyFromInstitute(String institute){
       return traineeStringMap.containsValue(institute);
    }
}
