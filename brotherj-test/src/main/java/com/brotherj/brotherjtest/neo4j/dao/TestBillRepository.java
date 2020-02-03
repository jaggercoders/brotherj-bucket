package com.brotherj.brotherjtest.neo4j.dao;

import com.brotherj.brotherjtest.neo4j.entity.BillRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description：
 *
 * @author wangjie
 */
@Repository
public interface TestBillRepository extends Neo4jRepository<BillRelation,Long> {

    @Query("OPTIONAL MATCH (p1:Bill {billNo:1}),(p2:Bill {billNo:8}),p=(p1)-[:MERGE*..]->(p2) return p")
    List<BillRelation> findAll();

}
