package lj.moviebase.resource.parameter.conventer;

import javax.ws.rs.ext.ParamConverter;
import java.time.Year;

class YearParamConverter implements ParamConverter<Year> {

    @Override
    public Year fromString(String year) {
        return Year.parse(year);
    }

    @Override
    public String toString(Year year) {
        return year.toString();
    }
}
