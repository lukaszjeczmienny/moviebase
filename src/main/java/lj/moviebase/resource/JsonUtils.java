package lj.moviebase.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;

public class JsonUtils {

    private static volatile ObjectMapper objectMapper;

    private JsonUtils() {
    }

    public static ObjectWriter jsonWriterFor(Class<?> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule(PROPERTIES));
        mapper.configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return jsonMapper().writerFor(clazz);
    }

    public static ObjectMapper jsonMapper() {
        if (objectMapper == null) {
            synchronized (JsonUtils.class) {
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule(PROPERTIES));
                    objectMapper.configure(ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                }
            }
        }
        return objectMapper;
    }
}