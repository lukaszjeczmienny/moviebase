package lj.moviebase.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@SuppressWarnings("unused")
public class Character {
    @ApiModelProperty(value = "Actor who played this character")
    @Valid
    private final Actor actor;
    @ApiModelProperty(example = "Simply character name")
    @NotBlank
    private final String characterName;

    @JsonCreator(mode = PROPERTIES)
    public Character(Actor actor, String characterName) {
        this.actor = actor;
        this.characterName = characterName;
    }

    public Actor getActor() {
        return actor;
    }

    public String getCharacterName() {
        return characterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(actor, character.actor) &&
                Objects.equals(characterName, character.characterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actor, characterName);
    }
}
