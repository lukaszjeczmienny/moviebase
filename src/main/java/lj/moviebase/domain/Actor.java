package lj.moviebase.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.SPACE;

public class Actor {
    private final String firstName;
    private final String lastName;

    @JsonCreator
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
        String[] actor2 = actor.split(SPACE);
        return new Actor(actor2[0], actor2[1]);
    }
}
