package pl.oczadly.spring.topics.domain.seminar.boundary;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.domain.seminar.entity.Seminar;
import pl.oczadly.spring.topics.domain.seminar.entity.dto.SeminarDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SeminarResourceAssembler implements ResourceAssembler<Seminar, Resource<Seminar>> {

    @Override
    public Resource<Seminar> toResource(Seminar seminar) {
        Long seminarId = seminar.getId();

        Link selfLink = linkTo(methodOn(SeminarRestController.class).getSeminarById(seminarId))
                .withSelfRel();
        Link updateLink = linkTo(methodOn(SeminarRestController.class).updateSeminar(new SeminarDTO(), seminarId))
                .withRel("update");
        Link deleteLink = linkTo(methodOn(SeminarRestController.class).deleteSeminar(seminarId))
                .withRel("delete");

        return new Resource<>(seminar, selfLink, updateLink, deleteLink);
    }
}
