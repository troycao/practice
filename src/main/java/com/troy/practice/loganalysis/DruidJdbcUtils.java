package com.troy.practice.loganalysis;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DruidJdbcUtils {

    private static DataSource source=null;//注意这里是私有，静态变量。

    static {//这里还是之前的加载驱动
        Properties p = new Properties();
        try {
            //加载文件  得到一个  druid.properties 的文件。
            p.load(DruidJdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //获取数据源
            source= DruidDataSourceFactory.createDataSource(p);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //建立连接
    public static Connection getConnetion() {
        try {
            //利用连接池连接对象
            Connection con = source.getConnection();
            return con;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //    DML 增删改的通用
    public static int exectueUpdate(String sql,Object...args) {
        Connection con=null;
        PreparedStatement ps=null;

        try {
            con = DruidJdbcUtils.getConnetion();
            ps=con.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            int i = ps.executeUpdate();

            return i;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
                if (con!=null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return 0;
    }


    //DQL 查询的通用
    public static List<Map<String, Object>> executeQuery(String sql, Object...args) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con = DruidJdbcUtils.getConnetion();  //注意这里是用source 来调用 getConnetion ,不再是通过
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }

            rs = ps.executeQuery();

            int count = rs.getMetaData().getColumnCount();

            List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object>map=new HashMap<String, Object>();

                for (int i = 0; i < count; i++) {
                    Object values = rs.getObject(i+1);
                    String countName = rs.getMetaData().getColumnLabel(i+1);
                    map.put(countName, values);
                }

                list.add(map);

                //    System.out.println(list);
            }
            /*for (Map<String, Object> map : list) {
                System.out.println(map);
            }*/

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
                if (con!=null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    //DQL 查询的通用
    public static List<Map<String, Object>> executeQuery(String sql) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            con = DruidJdbcUtils.getConnetion();  //注意这里是用source 来调用 getConnetion ,不再是通过
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int count = rs.getMetaData().getColumnCount();

            List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
            while (rs.next()) {
                Map<String, Object>map=new HashMap<String, Object>();

                for (int i = 0; i < count; i++) {
                    Object values = rs.getObject(i+1);
                    String countName = rs.getMetaData().getColumnLabel(i+1);
                    map.put(countName, values);
                }

                list.add(map);

                //    System.out.println(list);
            }
            /*for (Map<String, Object> map : list) {
                System.out.println(map);
            }*/

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (ps!=null) {
                    ps.close();
                }
                if (con!=null) {
                    con.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> maps = DruidJdbcUtils.executeQuery("select * from pims_order where rownum<10 ");
        for (Map<String, Object> map:maps) {
            System.out.println(map.get("PLATENUM"));
        }
        System.out.println(maps.size());
    }
}
