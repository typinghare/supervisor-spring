package us.jameschan.supervisor.constant;

/**
 * Enumerates the actions that can be performed on a task.
 * @author James Chan
 */
public enum TaskAction {
    // Starts a new task, transitioning it to the PENDING stage.
    START(0),

    // Pauses an ongoing task, transitioning it to the PAUSED stage.
    PAUSE(1),

    // Resumes a paused task, transitioning it back to the ONGOING stage.
    RESUME(2),

    // Finishes a task, transitioning it to the ENDED stage.
    FINISH(3);

    private final int number;

    TaskAction(int number) {
        this.number = number;
    }

    public static TaskAction fromNumber(Integer number) {
        for (TaskAction taskAction : TaskAction.values()) {
            if (taskAction.getNumber() == number) {
                return taskAction;
            }
        }

        return null;
    }

    /**
     * Returns the corresponding number of this task action.
     * @return the corresponding number of this task action.
     */
    public int getNumber() {
        return number;
    }
}
