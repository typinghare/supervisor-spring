package us.jameschan.supervisor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.jameschan.supervisor.dto.SubjectDto;
import us.jameschan.supervisor.exception.SubjectException;
import us.jameschan.supervisor.model.Subject;
import us.jameschan.supervisor.repository.SubjectRepository;

import java.util.List;

import static us.jameschan.supervisor.common.HelperFunctions.apply;

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

        return apply(new SubjectDto(), it -> {
            it.setId(subject.getId());
            it.setUserId(subject.getUserId());
            it.setName(subject.getName());
        });
    }

    /**
     * Retrieves a subject by its id.
     */
    public Subject getSubject(Long subjectId) {
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

        return subjectRepository.save(apply(new Subject(), it -> {
            it.setUserId(userId);
            it.setName(name);
        }));
    }

    /**
     * Deletes a subject.
     */
    public void deleteSubject(Long subjectId) {
        final Subject subject = getSubject(subjectId);

        userService.checkUserToBe(subject.getUserId());
        subjectRepository.deleteById(subjectId);
    }

    /**
     * Updates a subject.
     */
    public Subject updateSubject(Long subjectId, SubjectDto subjectDto) {
        if (subjectRepository.findById(subjectId).isEmpty()) {
            throw SubjectException.SUBJECT_NOT_FOUND;
        }

        final Subject subject = apply(new Subject(), it -> {
            it.setId(subjectId);
            it.setName(subjectDto.getName());
        });

        return subjectRepository.save(subject);
    }
}
