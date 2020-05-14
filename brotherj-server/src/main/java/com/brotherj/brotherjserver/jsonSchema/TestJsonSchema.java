package com.brotherj.brotherjserver.jsonSchema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.InputStream;
import java.util.Set;

import static com.networknt.schema.SpecVersion.VersionFlag.V7;
import static sun.nio.cs.Surrogate.is;

/**
 * description：
 *
 * @author jagger
 */
public class TestJsonSchema {

    public static JsonNode getJsonNodeFromStringContent(String content) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(content);
    }

    public static JsonSchema getJsonSchemaFromClasspath(String name) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(V7);
        InputStream is = Thread.currentThread().getContextClassLoader()
                               .getResourceAsStream(name);
        return factory.getSchema(is);
    }


    public static JsonSchema getJsonSchemaFromStringContent(String schemaContent) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(V7);
        return factory.getSchema(schemaContent);
    }

    public static void main(String[] args) throws Exception {
        JsonSchema schema = getJsonSchemaFromStringContent("{\"enum\":[1, 2, 3, 4],\"enumErrorCode\":\"Not in the list\"}");
        JsonNode node = getJsonNodeFromStringContent("7");
        Set<ValidationMessage> errors = schema.validate(node);
        System.out.println(errors);
    }


}
