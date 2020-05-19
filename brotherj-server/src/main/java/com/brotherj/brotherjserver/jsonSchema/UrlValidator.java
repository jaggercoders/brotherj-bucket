package com.brotherj.brotherjserver.jsonSchema;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.AbstractJsonValidator;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationContext;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * description：
 *
 * @author jagger
 */
@Slf4j
public class UrlValidator extends AbstractJsonValidator {

    private static final String URL ="url";

    private static final String RETURN_OBJ_TYPE ="returnObjType";
    private String url;

    private final boolean returnObjType=false;

    UrlValidator(String schemaPath, JsonNode schemaNode, JsonSchema parentSchema, String keyWord, ValidationContext validationContext){
        super(keyWord);
       if (schemaNode.isObject()){
           url = schemaNode.get(URL).asText();

           JsonNode jsonNode = schemaNode.get(RETURN_OBJ_TYPE);

       }
    }



    @Override
    public Set<ValidationMessage> validate(JsonNode node, JsonNode rootNode, String at) {
        log.debug("validate( {}, {}, {})",node,rootNode,at);

        Set<ValidationMessage> errors = new LinkedHashSet<>();
//        if (!node.isObject()) {
//            // ignore no object
//            return errors;
//        }

        log.debug("url:{}",url);
//        Stream.generate(node.fieldNames()::next)
//              .filter(x->x.contains("byUrlCheck"))
//              .peek(x->{
//                  node.findPath("url").
//              })

//        buildValidationMessage()
        return Collections.emptySet();
    }
}
