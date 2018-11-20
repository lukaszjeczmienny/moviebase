package lj.moviebase.domain;

import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.NoPublicFieldsRule;
import com.openpojo.validation.test.impl.GetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static com.openpojo.reflection.impl.PojoClassFactory.getPojoClass;

public class MovieTest {

    @Test
    public void shouldNotHavePublicFields() {
        Validator validator = ValidatorBuilder.create()
                .with(new NoPublicFieldsRule())
                .build();

        validator.validate(getPojoClass(Movie.class));
    }

    @Test
    public void shouldHaveProperGetter() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterTester())
                .build();

        validator.validate(getPojoClass(Movie.class));
    }

    @Test
    public void shouldFulfillHashcodeEqualsContract() {
        EqualsVerifier.forClass(Movie.class)
                .usingGetClass()
                .verify();
    }

}
