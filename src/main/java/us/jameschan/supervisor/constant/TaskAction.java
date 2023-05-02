package us.jameschan.supervisor.constant;

/**
 * Enumerates the actions that can be performed on a task.
 * @author James Chan
 */
public enum TaskAction {
    // Starts a new task, transitioning it to the PENDING stage.
    START,

    // Pauses an ongoing task, transitioning it to the PAUSED stage.
    PAUSE,

    // Resumes a paused task, transitioning it back to the ONGOING stage.
    RESUME,

    // Finishes a task, transitioning it to the ENDED stage.
    FINISH,

    // Removes a task, causing it to no longer appear in the task list.
    REMOVE;
}
