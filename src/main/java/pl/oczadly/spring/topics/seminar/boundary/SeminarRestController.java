package pl.oczadly.spring.topics.seminar.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.oczadly.spring.topics.seminar.control.SeminarService;
import pl.oczadly.spring.topics.seminar.entity.Seminar;
import pl.oczadly.spring.topics.seminar.entity.SeminarDTO;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/seminar", produces = { "application/hal+json" })
public class SeminarRestController {

    private SeminarService seminarService;

    private SeminarResourceAssembler seminarResourceAssembler;

    @GetMapping("/{id}")
    public Resource<Seminar> getSeminarById(@PathVariable Long id) {
        Seminar seminar = seminarService.getSeminarById(id);
        return seminarResourceAssembler.toResource(seminar);
    }

    @GetMapping("/topic/{topicId}")
    public Resources<Resource<Seminar>> getSeminarByTopicId(@PathVariable Long topicId) {
        List<Seminar> seminars = seminarService.getSeminarsByTopicId(topicId);
        List<Resource<Seminar>> seminarsResponse = seminars.stream()
                .map(seminarResourceAssembler::toResource)
                .collect(Collectors.toList());

        Link selfLink = linkTo(SeminarRestController.class).withSelfRel();
        return new Resources<>(seminarsResponse, selfLink);
    }

    @PostMapping("/topic/{topicId}")
    @PreAuthorize("hasAuthority('CRUD_ALL_SEMINARS')")
    public Resource<Seminar> createSeminar(@RequestBody SeminarDTO seminarDTO, @PathVariable Long topicId) {
        Seminar persistedSeminar = seminarService.createSeminar(seminarDTO, topicId);
        return seminarResourceAssembler.toResource(persistedSeminar);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_SEMINARS')")
    public Resource<Seminar> updateSeminar(@RequestBody SeminarDTO seminarDTO, @PathVariable Long id) {
        Seminar updatedSeminar = seminarService.updateSeminar(seminarDTO, id);
        return seminarResourceAssembler.toResource(updatedSeminar);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CRUD_ALL_SEMINARS')")
    public ResponseEntity<Seminar> deleteSeminar(@PathVariable Long id) {
        seminarService.deleteSeminar(id);
        return ResponseEntity.noContent()
                .build();
    }

    @Autowired
    public void setSeminarService(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    @Autowired
    public void setSeminarResourceAssembler(SeminarResourceAssembler seminarResourceAssembler) {
        this.seminarResourceAssembler = seminarResourceAssembler;
    }
}
