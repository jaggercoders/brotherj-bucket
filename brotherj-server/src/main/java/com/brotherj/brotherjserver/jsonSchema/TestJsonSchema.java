package com.brotherj.brotherjserver.jsonSchema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.Format;
import com.networknt.schema.JsonMetaSchema;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.NonValidationKeyword;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.ValidatorTypeCode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.networknt.schema.SpecVersion.VersionFlag.V7;
import static sun.nio.cs.Surrogate.is;

/**
 * description：
 *
 * @author jagger
 */
public class TestJsonSchema {



    public static JsonSchemaFactory jsonSchemaFactory(){
    // base on JsonMetaSchema.V201909 copy code below
    String URI = "http://json-schema.org/draft-07/schema#";
    String ID = "$id";
    List<Format> BUILTIN_FORMATS = new ArrayList<Format>(JsonMetaSchema.COMMON_BUILTIN_FORMATS);

    JsonMetaSchema myJsonMetaSchema = new JsonMetaSchema.Builder(URI)
            .idKeyword(ID)
            .addFormats(BUILTIN_FORMATS)
            .addKeywords(ValidatorTypeCode.getNonFormatKeywords(SpecVersion.VersionFlag.V7))
            // keywords that may validly exist, but have no validation aspect to them
            .addKeywords(Arrays.asList(
                    new NonValidationKeyword("$schema"),
                    new NonValidationKeyword("$id"),
                    new NonValidationKeyword("title"),
                    new NonValidationKeyword("description"),
                    new NonValidationKeyword("default"),
                    new NonValidationKeyword("definitions")
            ))
            // add your custom keyword
            .addKeyword(new UrlKeyWord())
            .build();
    return new JsonSchemaFactory.Builder().defaultMetaSchemaURI(myJsonMetaSchema.getUri())
                                          .addMetaSchema(myJsonMetaSchema)
                                          .build();
}


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
        return jsonSchemaFactory().getSchema(schemaContent);
    }

    public static void main(String[] args) throws Exception {
//        JsonSchema jsonSchemaFromClasspath = getJsonSchemaFromClasspath("/additionalProperties.json");
//        JsonSchema schema = getJsonSchemaFromStringContent("{\n" +
//                "    \"definitions\": {},\n" +
//                "    \"$schema\": \"http://json-schema.org/draft-07/schema#\",\n" +
//                "    \"$id\": \"http://example.com/root.json\",\n" +
//                "    \"type\": \"object\",\n" +
//                "    \"title\": \"The Root Schema\",\n" +
//                "    \"required\": [\n" +
//                "        \"checked\",\n" +
//                "        \"dimensions\",\n" +
//                "        \"id\",\n" +
//                "        \"name\",\n" +
//                "        \"price\",\n" +
//                "        \"tags\"\n" +
//                "    ],\n" +
//                "    \"properties\": {\n" +
//                "        \"checked\": {\n" +
//                "            \"type\": \"boolean\"\n" +
//                "        },\n" +
//                "        \"dimensions\": {\n" +
//                "            \"type\": \"object\",\n" +
//                "            \"required\": [\n" +
//                "                \"width\",\n" +
//                "                \"height\"\n" +
//                "            ],\n" +
//                "            \"properties\": {\n" +
//                "                \"width\": {\n" +
//                "                    \"type\": \"integer\"\n" +
//                "                },\n" +
//                "                \"height\": {\n" +
//                "                    \"type\": \"integer\"\n" +
//                "                }\n" +
//                "            }\n" +
//                "        },\n" +
//                "        \"id\": {\n" +
//                "            \"type\": \"integer\"\n" +
//                "        },\n" +
//                "        \"name\": {\n" +
//                "            \"type\": \"string\"\n" +
//                "        },\n" +
//                "        \"price\": {\n" +
//                "            \"type\": \"number\"\n" +
//                "        },\n" +
//                "        \"tags\": {\n" +
//                "            \"type\": \"array\",\n" +
//                "            \"items\": {\n" +
//                "                \"type\": \"string\"\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}");
        JsonSchema schema = getJsonSchemaFromStringContent("{\n" +
                "    \"properties\": {\n" +
                "        \"id\": {\n" +
                "            \"byUrlCheck\": {\n" +
                "                \"url\": \"http://localhost:8080/query\",\n" +
                "                \"returnObjType\": \"boolean\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"name\": {\n" +
                "            \"type\": \"string\"\n" +
                "        },\n" +
                "        \"price\": {\n" +
                "            \"type\": \"number\"\n" +
                "        }\n" +
                "    }\n" +
                "}");
//        JsonNode node = getJsonNodeFromStringContent("{\n" +
//                "    \"checked\": false,\n" +
//                "    \"dimensions\": {\n" +
//                "        \"width\": 5,\n" +
//                "        \"height\": 10\n" +
//                "    },\n" +
//                "    \"id\": 1,\n" +
//                "    \"name\": \"A green door\",\n" +
//                "    \"price\": 12.5,\n" +
//                "    \"tags\": [\n" +
//                "        \"home\",\n" +
//                "        \"green\"\n" +
//                "    ]\n" +
//                "}");

        JsonNode node = getJsonNodeFromStringContent("{\n" +
                "    \"id\": \"001001\",\n" +
                "    \"name\": \"test\",\n" +
                "    \"price\": 144\n" +
                "}");
        Set<ValidationMessage> errors = schema.validate(node);
        System.out.println(errors);
    }



}
