package us.jameschan.supervisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.dto.SubjectDto;
import us.jameschan.supervisor.exception.SubjectException;
import us.jameschan.supervisor.model.Subject;
import us.jameschan.supervisor.repository.SubjectRepository;

import java.util.List;
import java.util.Objects;

import static us.jameschan.neater.StaticFunctions.createBean;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    private final UserService userService;

    @Autowired
    private SubjectService(SubjectRepository subjectRepository, UserService userService) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
    }

    /**
     * Converts a subject to subject DTO.
     */
    public SubjectDto toSubjectDto(Subject subject) {
        if (subject == null) return null;

        return createBean(SubjectDto.class, it -> {
            it.setId(subject.getId());
            it.setUserId(subject.getUserId());
            it.setName(subject.getName());
        });
    }

    /**
     * Retrieves a subject by its id.
     */
    public Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
            .orElseThrow(() -> SubjectException.SUBJECT_NOT_FOUND);
    }

    /**
     * Retrieves all of a user's subjects.
     */
    public List<Subject> getSubjectsByUserId(Long userId) {
        return subjectRepository.findByUserId(userId);
    }

    /**
     * Creates a subject.
     */
    public Subject createSubject(String name) {
        final Long userId = userService.getUserIdByToken();
        if (subjectRepository.findFirstByUserIdAndName(userId, name).isPresent()) {
            throw SubjectException.SUBJECT_ALREADY_EXIST;
        }

        return subjectRepository.save(createBean(Subject.class, it -> {
            it.setUserId(userId);
            it.setName(name);
        }));
    }

    /**
     * Deletes a subject.
     */
    public void deleteSubject(Long subjectId) {
        final Subject subject = getSubjectById(subjectId);

        userService.checkUserToBe(subject.getUserId());
        subjectRepository.deleteById(subjectId);
    }

    /**
     * Updates a subject.
     */
    public Subject updateSubject(Long subjectId, SubjectDto subjectDto) {
        final Subject subject = getSubjectById(subjectId);
        final Long userId = userService.getUserIdByToken();
        if (!Objects.equals(userId, subject.getUserId())) {
            throw SubjectException.NO_PERMISSION_UPDATE_SUBJECT;
        }

        subject.setName(subjectDto.getName());

        return subjectRepository.save(subject);
    }
}
