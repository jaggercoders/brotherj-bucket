package com.brotherj.brotherjtest.neo4j.dao;

import com.brotherj.brotherjtest.neo4j.entity.BaseRelation;
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
public interface BaseRelationRepository extends Neo4jRepository<BaseRelation,Long> {

    @Query("OPTIONAL MATCH (p1:Bill),(p2:Bill),p=(p1)-[:MERGE*]->(p2) where id(p1)=27 and id(p2)=34 return p")
    List<BaseRelation> findAll();


}
