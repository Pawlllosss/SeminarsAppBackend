package pl.oczadly.spring.topics.user.boundary;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.oczadly.spring.topics.user.entity.User;
import pl.oczadly.spring.topics.user.entity.dto.UserResponseDTO;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<UserResponseDTO>> {

    private ModelMapper mapper;

    @Override
    public Resource<UserResponseDTO> toResource(User user) {
        Long userId = user.getId();

        Link selfLink = linkTo(methodOn(UserRestController.class).getUserById(userId))
                .withSelfRel();
        UserResponseDTO userResponseDTO = mapper.map(user, UserResponseDTO.class);

        return new Resource<>(userResponseDTO, selfLink);
    }

    @Autowired
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}
