package lj.moviebase.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModelProperty;
import lj.moviebase.resource.exception.EncodingException;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static org.apache.commons.lang3.StringUtils.SPACE;

@SuppressWarnings("unused")
public class Actor {
    @ApiModelProperty(value = "Simply first name")
    @NotBlank
    private final String firstName;
    @ApiModelProperty(value = "Simply last name")
    @NotBlank
    private final String lastName;

    @JsonCreator(mode = PROPERTIES)
    public Actor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(firstName, actor.firstName) &&
                Objects.equals(lastName, actor.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public static Actor fromString(String actor) {
        String[] initials = actor.split(SPACE);
        if (!(initials.length == 2)) {
            throw new EncodingException("Could not decode actor's initials");
        }
        return new Actor(initials[0], initials[1]);
    }
}
