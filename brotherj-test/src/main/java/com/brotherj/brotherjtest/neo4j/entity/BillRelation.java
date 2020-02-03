package com.brotherj.brotherjtest.neo4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.math.BigDecimal;

/**
 * description：
 *
 * @author wangjie
 */
//@RelationshipEntity(type = "MERGE")
@RelationshipEntity
@Data
@NoArgsConstructor
public class BillRelation {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private Integer billNo;
    @Property
    private Integer type;
    @Property
    private BigDecimal quantity;

    @StartNode
    private BillNode startNode;

    @EndNode
    private BillNode endNode;
}
