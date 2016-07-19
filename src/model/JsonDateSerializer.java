package model;
// Same as JTableSpring Demo.

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Component
public class JsonDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString("/Date(" + date.getTime() + ")/");
    }
}