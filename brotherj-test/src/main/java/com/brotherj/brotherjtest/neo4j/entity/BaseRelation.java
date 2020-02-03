package com.brotherj.brotherjtest.neo4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.HashMap;
import java.util.Map;

/**
 * description：
 *
 * @author wangjie
 */
@RelationshipEntity(type = "MERGE")
@Data
@NoArgsConstructor
public class BaseRelation {
    @Id @GeneratedValue
    private Long id;

    @Properties
    private Map<String,Object> properties=new HashMap<>();


    @StartNode
    private BaseNode startNode;

    @EndNode
    private BaseNode endNode;
}
