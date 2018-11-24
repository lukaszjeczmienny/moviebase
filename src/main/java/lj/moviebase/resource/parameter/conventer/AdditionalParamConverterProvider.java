package lj.moviebase.resource.parameter.conventer;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Year;

public class AdditionalParamConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (Year.class.isAssignableFrom(rawType)) {
            return (ParamConverter<T>) new YearParamConverter();
        }
        return null;
    }
}