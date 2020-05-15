package com.brotherj.brotherjserver.jsonSchema;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.AbstractJsonValidator;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Set;

/**
 * description：
 *
 * @author jagger
 */
@Slf4j
public class UrlValidator extends AbstractJsonValidator {

    protected UrlValidator(String keyword) {
        super(keyword);
    }

    @Override
    public Set<ValidationMessage> validate(JsonNode node, JsonNode rootNode, String at) {
        log.debug("validate( {}, {}, {})",node,rootNode,at);



//        buildValidationMessage()
        return Collections.emptySet();
    }
}
