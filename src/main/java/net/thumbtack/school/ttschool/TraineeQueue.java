package net.thumbtack.school.ttschool;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class TraineeQueue {

    private Queue<Trainee> queueTrainee;

    public TraineeQueue(){
        queueTrainee = new LinkedList<>();
    }

    public void addTrainee(Trainee trainee){
        queueTrainee.add(trainee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraineeQueue that = (TraineeQueue) o;
        return Objects.equals(queueTrainee, that.queueTrainee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queueTrainee);
    }

    public Trainee removeTrainee() throws TrainingException {
        if (queueTrainee.isEmpty()) {
            throw new TrainingException(TrainingErrorCode.EMPTY_TRAINEE_QUEUE);
        }
        return queueTrainee.poll();
    }

    public boolean isEmpty(){
        return queueTrainee.isEmpty();
    }
}