package com.brotherj.brotherjtest.neo4j.dao;

import com.brotherj.brotherjtest.neo4j.entity.BillNode;
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
public interface BillNodeRepository extends Neo4jRepository<BillNode,Long> {

    @Query("MATCH (p1:Bill ) where p1.billNo=1 return p1")
    BillNode findBillNodeByBillNo();


//    BillNode findBillNodeById(Long id);
}
