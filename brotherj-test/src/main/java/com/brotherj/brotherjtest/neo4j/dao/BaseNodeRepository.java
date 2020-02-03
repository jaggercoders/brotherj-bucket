package com.brotherj.brotherjtest.neo4j.dao;

import com.brotherj.brotherjtest.neo4j.entity.BaseNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * description：
 *
 * @author wangjie
 */
@Repository
public interface BaseNodeRepository extends Neo4jRepository<BaseNode,Long> {


}
