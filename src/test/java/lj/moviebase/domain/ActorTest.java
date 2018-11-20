package lj.moviebase.domain;

import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.GetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;

public class ActorTest {

    @Test
    public void shouldNotHavePublicFields() {
        Validator validator = ValidatorBuilder.create()
                .with(new NoPublicFieldsRule())
                .build();

        validator.validate(getPojoClass(Actor.class));
    }

    @Test
    public void shouldHaveGetterForEveryField() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterTester())
                .build();

        validator.validate(getPojoClass(Actor.class));
    }

    @Test
    public void shouldFulfillHashcodeEqualsContract() {
        EqualsVerifier.forClass(Actor.class)
                .usingGetClass()
                .verify();
    }
}
