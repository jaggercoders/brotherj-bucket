package com.brotherj.brotherjtest.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Properties;

import java.util.HashMap;
import java.util.Map;

/**
 * description：
 *
 * @author wangjie
 */
@NodeEntity("Bill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseNode {

    @Id
    @GeneratedValue
    private Long id;

    @Properties
    private Map<String,Object> properties=new HashMap<>();
}
