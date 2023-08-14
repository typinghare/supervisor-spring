package us.jameschan.supervisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.jameschan.supervisor.annotation.Message;
import us.jameschan.supervisor.constant.TaskStage;
import us.jameschan.supervisor.dto.*;
import us.jameschan.supervisor.service.SubjectService;
import us.jameschan.supervisor.service.TaskService;
import us.jameschan.supervisor.service.UserService;
import us.jameschan.supervisor.util.Pagination;
import us.jameschan.supervisor.util.TimestampRange;

import java.util.Arrays;
import java.util.List;

import static us.jameschan.neater.StaticFunctions.createBean;

@Controller()
@RequestMapping("/api/supervisor/users")
public class UserController {
    private final UserService userService;
    private final SubjectService subjectService;
    private final TaskService taskService;

    @Autowired
    public UserController(UserService userService, SubjectService subjectService, TaskService taskService) {
        this.userService = userService;
        this.subjectService = subjectService;
        this.taskService = taskService;
    }

    @PostMapping("/")
    @Message("User signed up successfully")
    @ResponseBody
    public UserTokenDto signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return userService.signUp(userSignUpDto);
    }

    @PostMapping("/auth/")
    @Message("User signed in successfully.")
    @ResponseBody
    public UserTokenDto signIn(@RequestBody UserSignInDto userSignInDto) {
        return userService.signIn(userSignInDto);
    }

    @GetMapping("/{userId}/")
    @Message("Got user's information successfully.")
    @ResponseBody
    public UserDto getUserInfo(@PathVariable Long userId) {
        return userService.toUserDto(userService.getUserById(userId));
    }

    @GetMapping("/")
    @Message("Got current user's information successfully.")
    @ResponseBody
    public UserDto getUserInfo() {
        return getUserInfo(userService.getUserIdByToken());
    }

    @GetMapping("/{userId}/subjects/")
    @Message("Got all subjects successfully.")
    @ResponseBody
    public List<SubjectDto> getSubjectsForUser(@PathVariable Long userId) {
        return subjectService.getSubjectsByUserId(userId).stream()
            .map(subjectService::toSubjectDto).toList();
    }

    @GetMapping("/{userId}/tasks/")
    @ResponseBody
    @Message("Got all tasks successfully.")
    public List<TaskDto> getTasksForUser(
        @PathVariable Long userId,
        @RequestParam Long fromTimestamp,
        @RequestParam Long toTimestamp,
        @RequestParam(required = false) Integer limit,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String taskStageString
    ) {
        final TaskDto taskDto = createBean(TaskDto.class, it -> {
            it.setCategoryId(categoryId);
            it.setUserId(userId);
        });

        // Create timestamp range.
        final TimestampRange timestampRange = new TimestampRange(fromTimestamp, toTimestamp);

        // Create pagination.
        final Pagination pagination = new Pagination(limit, page);

        // Task stage list.
        List<TaskStage> taskStageList = taskStageString == null ? null :
            Arrays.stream(taskStageString.split(" ")).map(str -> {
                try {
                    final int number = Integer.parseInt(str);
                    return TaskStage.fromNumber(number);
                } catch (NumberFormatException e) {
                    throw new RuntimeException();
                }
            }).toList();


        return taskService.getTasks(taskDto, timestampRange, pagination, taskStageList)
            .stream().map(taskService::toTaskDto).toList();
    }

    @GetMapping("/{userId}/durations/")
    @ResponseBody
    @Message("Got the duration successfully.")
    public Integer getTotalDuration(
        @PathVariable Long userId,
        @RequestParam Long fromTimestamp,
        @RequestParam Long toTimestamp,
        @RequestParam(required = false) Long subjectId
    ) {
        final TaskDto taskDto = createBean(TaskDto.class, it -> {
            it.setSubjectId(subjectId);
            it.setUserId(userId);
        });

        // Create timestamp range.
        final TimestampRange timestampRange = new TimestampRange(fromTimestamp, toTimestamp);

        return taskService.getTotalDuration(taskDto, timestampRange);
    }
}
