package us.jameschan.supervisor.constant;

/**
 * An enumeration that represents the stages of a task.
 */
public enum TaskStage {
    // The stage that represents a task that hasn't been started yet.
    PENDING(0),

    // The stage that represents a task that is ongoing and duration is counting.
    // Users cannot start another task while the task is ongoing.
    ONGOING(1),

    // The stage that represents a task that has been paused and duration is not counting.
    // Users can start another task if the task is paused.
    PAUSED(2),

    // The stage that represents a task that has been ended.
    ENDED(3);

    private final int number;

    TaskStage(int number) {
        this.number = number;
    }

    /**
     * Returns the corresponding TaskStage for a given number.
     * @param number the number corresponding to the TaskStage.
     * @return the TaskStage corresponding to the given number; null if no TaskStage matches.
     */
    public static TaskStage fromNumber(int number) {
        for (TaskStage taskStage : TaskStage.values()) {
            if (taskStage.getNumber() == number) {
                return taskStage;
            }
        }

        return null;
    }

    /**
     * Returns the corresponding number of this task stage.
     * @return the corresponding number of this task stage.
     */
    public int getNumber() {
        return number;
    }
}
