package com.brotherj.brotherjserver.jsonSchema;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.AbstractKeyword;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaException;
import com.networknt.schema.JsonValidator;
import com.networknt.schema.ValidationContext;

/**
 * description：
 *
 * @author jagger
 */
public class UrlKeyWord extends AbstractKeyword {

    public UrlKeyWord(String value) {
        super("byUrlCheck");
    }

    @Override
    public JsonValidator newValidator(String schemaPath, JsonNode schemaNode, JsonSchema parentSchema, ValidationContext validationContext) throws JsonSchemaException, Exception {
        return null;
    }
}
