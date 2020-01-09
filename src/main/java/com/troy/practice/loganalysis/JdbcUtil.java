package com.troy.practice.loganalysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtil {

    private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    /** 数据库链接地址 */
    private static final String url =  "jdbc:Oracle:thin:@172.16.1.51:1521:yljc";
    /** 用户名 */
    private static final String userName = "pims";
    /** 密码 */
    private static final String password = "Oracle1234!";

    /** 定义连接 */
    private static Connection conn = null;
    /** 定义STMT */
    private static PreparedStatement stmt = null;
    /** 定义结果集 */
    private static ResultSet rs;

    /** 初始化加载链接 */
    static {
        try {
            Class.forName(DRIVER_NAME);
            conn = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            System.err.println("驱动加载失败");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("数据库链接异常");
            e.printStackTrace();
        }
    }

    /** 获取链接 */
    public static Connection getConn() {
        return conn;
    }

    public static Boolean executeSql(String sql){
        Boolean flag = false;
        try {
            stmt = conn.prepareStatement(sql);
            flag = stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /** 执行SQL返回ResultSet */
    public static ResultSet executeSql(String sql, Object... args) {
        try {
            System.out.println("准备执行SQL : \n" + sql);
            stmt = conn.prepareStatement(sql);
            if (null != args && args.length != 0) {
                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i + 1, args[i]);
                }
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("数据查询异常");
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * @title 查询数据结果 , 并封装为对象
     * @author Xingbz
     */
    private static <T> T excuteQuery(Class<T> klass, String sql, Object... args) {
        try {
            rs = executeSql(sql, args);
            ResultSetMetaData metaData = rs.getMetaData();

            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (rs.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnname = metaData.getColumnLabel(i);
                    Object obj = rs.getObject(i);
                    resultMap.put(columnname, obj);
                }
            }

            return JSON.parseObject(JSON.toJSONString(resultMap), klass);
        } catch (Exception e) {
            System.err.println("数据查询异常");
            e.printStackTrace();
        } finally {
            close();
        }
        return JSON.toJavaObject(new JSONObject(), klass);
    }

    /**
     * @title 查询数据结果 , 并封装为List
     * @author Xingbz
     */
    private static <T> List<T> excuteQueryToList(Class<T> klass, String sql, Object... args) {
        try {
            rs = executeSql(sql, args);
            List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
            Map<String, String> resultMap = new HashMap<String, String>();
            while (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    resultMap.put(metaData.getColumnName(i), rs.getString(i));
                }
                resultList.add(resultMap);
            }

            return JSON.parseArray(JSON.toJSONString(resultList), klass);
        } catch (Exception e) {
            System.err.println("数据查询异常");
            e.printStackTrace();
        } finally {
            close();
        }
        return JSON.parseArray("[]", klass);
    }

    /** 关闭链接,释放资源 */
    public static void close() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }

            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.err.println("资源释放发生异常");
        }
    }

}
