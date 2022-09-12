package net.thumbtack.school.ttschool;
import java.util.LinkedList;
import java.util.Queue;

public class TraineeQueue {

    private Queue<Trainee> queueTrainee;

    public TraineeQueue(){
        queueTrainee = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee){
        queueTrainee.add(trainee);
    }

    public Trainee removeTrainee() throws TrainingException{
        if(queueTrainee.peek() == null)
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        return queueTrainee.poll();
    }

    public boolean isEmpty(){
        return queueTrainee.isEmpty();
    }
}