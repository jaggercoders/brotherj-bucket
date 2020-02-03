package com.brotherj.brotherjtest;

import com.brotherj.brotherjtest.neo4j.dao.BaseNodeRepository;
import com.brotherj.brotherjtest.neo4j.dao.BaseRelationRepository;
import com.brotherj.brotherjtest.neo4j.dao.BillNodeRepository;
import com.brotherj.brotherjtest.neo4j.dao.TestBillRepository;
import com.brotherj.brotherjtest.neo4j.entity.BaseNode;
import com.brotherj.brotherjtest.neo4j.entity.BaseRelation;
import com.brotherj.brotherjtest.neo4j.entity.BillNode;
import com.brotherj.brotherjtest.neo4j.entity.BillRelation;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description：
 *
 * @author brotherJ  Wang
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestClient testClient;

    @Autowired
    private TestBillRepository billRelationRepository;

    @Autowired
    private BillNodeRepository billNodeRepository;

    @Autowired
    private BaseRelationRepository baseRelationRepository;

    @Autowired
    private BaseNodeRepository baseNodeRepository;

    @Autowired
    private Session session;

    @RequestMapping(value = "/tt",method = RequestMethod.GET)
    public String test(String name){
        return testClient.test();
    }


    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test2(String name){
//        BillNode billNode = billNodeRepository.findBillNodeByBillNo();
//        System.out.println(billNode);
        BaseNode baseNode = baseNodeRepository.findById(34L).orElse(null);
        System.out.println(baseNode);
//        List<BillRelation> all = billRelationRepository.findAll();
//        System.out.println(all);
        List<BaseRelation> all1 = baseRelationRepository.findAll();
        System.out.println(all1);
        return "success" ;
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public String test4(String name){
        Map<String,Long> ids=new HashMap<>();
        ids.put("id1",27L);
        ids.put("id2",34L);
        Result baseRelation = session.query( "OPTIONAL MATCH (p1:Bill),(p2:Bill),p=(p1)-[:MERGE*]->(p2) where id(p1)={id1} and id(p2)={id2} return p", ids);
        System.out.println(baseRelation);
        return "success" ;
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String test3(String name){
//        BillNode node8=new BillNode();
//        node8.setName("仓单8");
//        node8.setBillNo(9);
//        node8.setQuantity(new BigDecimal(15));
//        billNodeRepository.save(node8);
//        BillNode node9=new BillNode();
//        node9.setName("仓单9");
//        node9.setBillNo(10);
//        node9.setQuantity(new BigDecimal(7));
//        billNodeRepository.save(node9);

        BillNode node7 = billNodeRepository.findById(34L).orElse(null);
        BillNode node8 = billNodeRepository.findById(47L).orElse(null);
        BillNode node9 = billNodeRepository.findById(48L).orElse(null);

        BillRelation relation10=new BillRelation();
        relation10.setBillNo(10);
        relation10.setQuantity(new BigDecimal(15));
        relation10.setType(3);
        relation10.setStartNode(node7);
        relation10.setEndNode(node8);
        billRelationRepository.save(relation10);

        BillRelation relation11=new BillRelation();
        relation11.setBillNo(11);
        relation11.setQuantity(new BigDecimal(7));
        relation11.setType(3);
        relation11.setStartNode(node7);
        relation11.setEndNode(node9);
        billRelationRepository.save(relation11);

        List<BillRelation> all = billRelationRepository.findAll();
        System.out.println(all);
        return "success" ;
    }

//    @Autowired
//    private SpringApplicationRunListener eventPublishingRunListener;

//    @Autowired
//    private ApplicationContext applicationContext;
//
//
//    @RequestMapping(value = "/shutdown",method = RequestMethod.GET)
//    public String test5(String name){
//        applicationContext.publishEvent(new ApplicationFailedEvent());
//        return "success" ;
//    }
}
