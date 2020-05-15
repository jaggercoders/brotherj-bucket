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
        InputStream is = Class.forName(TestJsonSchema.class.getName())
                               .getResourceAsStream(name);
        return factory.getSchema(is);
    }


    public static JsonSchema getJsonSchemaFromStringContent(String schemaContent) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(V7);
        return factory.getSchema(schemaContent);
    }

    public static void main(String[] args) throws Exception {
        JsonSchema jsonSchemaFromClasspath = getJsonSchemaFromClasspath("/additionalProperties.json");
        JsonSchema schema = getJsonSchemaFromStringContent("{\n" +
                "    \"definitions\": {},\n" +
                "    \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
                "    \"$id\": \"http://example.com/root.json\",\n" +
                "    \"type\": \"object\",\n" +
                "    \"title\": \"The Root Schema\",\n" +
                "    \"required\": [\n" +
                "        \"checked\",\n" +
                "        \"dimensions\",\n" +
                "        \"id\",\n" +
                "        \"name\",\n" +
                "        \"price\",\n" +
                "        \"tags\"\n" +
                "    ],\n" +
                "    \"properties\": {\n" +
                "        \"checked\": {\n" +
                "            \"type\": \"boolean\"\n" +
                "        },\n" +
                "        \"dimensions\": {\n" +
                "            \"type\": \"object\",\n" +
                "            \"required\": [\n" +
                "                \"width\",\n" +
                "                \"height\"\n" +
                "            ],\n" +
                "            \"properties\": {\n" +
                "                \"width\": {\n" +
                "                    \"type\": \"integer\"\n" +
                "                },\n" +
                "                \"height\": {\n" +
                "                    \"type\": \"integer\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"id\": {\n" +
                "            \"type\": \"integer\"\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "            \"type\": \"string\"\n" +
                "        },\n" +
                "        \"price\": {\n" +
                "            \"type\": \"number\"\n" +
                "        },\n" +
                "        \"tags\": {\n" +
                "            \"type\": \"array\",\n" +
                "            \"items\": {\n" +
                "                \"type\": \"string\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}");
        JsonNode node = getJsonNodeFromStringContent("{\n" +
                "    \"checked\": false,\n" +
                "    \"dimensions\": {\n" +
                "        \"width\": 5,\n" +
                "        \"height\": 10\n" +
                "    },\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"A green door\",\n" +
                "    \"price\": 12.5,\n" +
                "    \"tags\": [\n" +
                "        \"home\",\n" +
                "        \"green\"\n" +
                "    ]\n" +
                "}");
        Set<ValidationMessage> errors = schema.validate(node);
        System.out.println(errors);
    }


}
