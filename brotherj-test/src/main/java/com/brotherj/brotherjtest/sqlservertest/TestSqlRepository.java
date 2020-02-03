//package com.brotherj.brotherjtest.sqlservertest;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * description：
// *
// * @author wangjie
// */
//@Repository
//public interface TestSqlRepository extends JpaRepository {
//
//    @Query("SELECT A.BILLNO as billno FROM zhzh.dbo.VIEW_OUTHOUSE_LIST_BYBILL A WHERE ( A.FIRSTOWNERID LIKE '%' ) AND ( A.RECEIPTOWNER LIKE '000183' ) AND ( A.GOODSID LIKE '%' ) AND ( CONVERT ( VARCHAR, A.OUTHOUSEDATE, 120 ) >= '2018-07-17 17:21:00' ) AND ( CONVERT ( VARCHAR, A.OUTHOUSEDATE, 120 ) <= '2019-01-16 17:21:59' ) AND ( A.ISRETURN = 0 ) AND ( A.OWNERBILLNO LIKE '%'+''+'%' OR A.OUTOWNERBILLNO LIKE '%'+''+'%' OR A.SHIP LIKE '%'+''+'%' ) ORDER BY A.OUTHOUSEDATE DESC,A.GOODSID")
//    List<String> queryAll();
//}
