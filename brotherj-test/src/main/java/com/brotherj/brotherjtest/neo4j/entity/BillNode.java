package com.brotherj.brotherjtest.neo4j.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Labels;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;

/**
 * description：
 *
 * @author wangjie
 */
@Data
@NoArgsConstructor
@NodeEntity
//@NodeEntity("Bill")
public class BillNode {

    @Id @GeneratedValue
    private Long id;

    @Property(name = "billNo")
    private Integer billNo;
    @Property(name = "name")
    private String name;
    @Property(name = "ownerId")
    private String ownerId;
    @Property(name = "siteId")
    private String siteId;
    @Property(name = "quantity")
    private BigDecimal quantity;

}
