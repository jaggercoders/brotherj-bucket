package com.brotherj.brotherjserver.jsonSchema;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description：
 *
 * @author jagger
 */
public class TestJsonSchema2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JSONObject jsonSchema = new JSONObject(new JSONTokener(IOUtils.toString(
                new InputStreamReader(Class.forName(TestJsonSchema.class.getName()).getResourceAsStream("/additionalProperties.json")))));

//        SchemaLoader loader = SchemaLoader.builder()
//                                          .schemaJson(yourSchemaJSON)
//                                          .draftV7Support() // or draftV7Support()
//                                          .build();
//        Schema schema = loader.load().build();

        JSONObject jsonSubject = new JSONObject("7");

        Schema schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonSubject);
    }
}
