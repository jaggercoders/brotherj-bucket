package com.brotherj.brotherjtest.sqlservertest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * description：
 *
 * @author wangjie
 */
public class TestJdbc {
    //这里可以设置数据库名称
    private final static String URL = "jdbc:sqlserver://10.144.154.35:1433;DatabaseName=orderbank";
    private static final String USER="wmsapp_RW_tst";
    private static final String PASSWORD="Vf79hmrBrjdtMh8aBxXK";
    private static Connection conn=null;
    //静态代码块（将加载驱动、连接数据库放入静态块中）
    static{
        try {

            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接
            conn= DriverManager.getConnection(URL,USER,PASSWORD);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对外提供一个方法来获取数据库连接
    public static Connection getConnection(){
        return conn;
    }
    //查询
    public void select(Statement stmt) throws SQLException {
        long start=System.currentTimeMillis();
        System.out.println("start:"+start);
        ResultSet rs = stmt.executeQuery("SELECT A.BILLNO as billno FROM zhzh.dbo.VIEW_OUTHOUSE_LIST_BYBILL A WHERE ( A.FIRSTOWNERID LIKE '%' ) AND ( A.RECEIPTOWNER LIKE '000183' ) AND ( A.GOODSID LIKE '%' ) AND ( CONVERT ( VARCHAR, A.OUTHOUSEDATE, 120 ) >= '2018-07-17 17:21:00' ) AND ( CONVERT ( VARCHAR, A.OUTHOUSEDATE, 120 ) <= '2019-01-16 17:21:59' ) AND ( A.ISRETURN = 0 ) AND ( A.OWNERBILLNO LIKE '%'+''+'%' OR A.OUTOWNERBILLNO LIKE '%'+''+'%' OR A.SHIP LIKE '%'+''+'%' ) ORDER BY A.OUTHOUSEDATE DESC,A.GOODSID");
        long end=System.currentTimeMillis();
        System.out.println("end:"+end);
        System.out.println("end-start:"+(end-start));
        while(rs.next()){
            //如果对象中有数据，就会循环打印出来
            System.out.println("billno="+rs.getString("billNo"));

            //System.out.println(rs.getInt("教师编号")+","+rs.getString("姓名")+","+rs.getInt("专业"));
        }
    }
    //测试用例
    public static void main(String[] args) throws Exception{
        TestJdbc d = new TestJdbc();
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        d.select(stmt);
        //ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句 ，返回一个结果集（ResultSet）对象。
        //d.insert(stmt);
        //d.update(stmt);


    }
}
