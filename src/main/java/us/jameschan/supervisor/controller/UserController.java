package us.jameschan.supervisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import us.jameschan.supervisor.annotation.Message;
import us.jameschan.supervisor.dto.*;
import us.jameschan.supervisor.service.SubjectService;
import us.jameschan.supervisor.service.UserService;

import java.util.List;

@Controller()
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    private final SubjectService subjectService;

    @Autowired
    public UserController(
        UserService userService,
        SubjectService subjectService
    ) {
        this.userService = userService;
        this.subjectService = subjectService;
    }

    @PostMapping("/")
    @Message("Successfully signed up.")
    @ResponseBody
    public UserDto signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return userService.signUp(userSignUpDto);
    }

    @GetMapping("/{userId}")
    @Message("Successfully get user info.")
    @ResponseBody
    public UserDto getUserInfo(@PathVariable Long userId) {
        return userService.toUserDto(userService.getUserById(userId));
    }

    @PutMapping("/")
    @Message("Successfully sign in.")
    @ResponseBody
    public UserSignInResponseDto signIn(@RequestBody UserSignInDto userSignInDto) {
        return userService.signIn(userSignInDto);
    }

    @GetMapping("/")
    @Message("Successfully get user info of the current user.")
    @ResponseBody
    public UserDto getUserInfo() {
        return getUserInfo(userService.getUserIdByToken());
    }

    @GetMapping("/{userId}/subjects")
    @Message("Successfully get all subjects.")
    @ResponseBody
    public List<SubjectDto> getSubjectsForUser(@PathVariable("userId") Long userId) {
        return subjectService.getSubjectsByUserId(userId).stream()
            .map(subjectService::toSubjectDto).toList();
    }
}
