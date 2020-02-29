package com.lzf.applianceafter_salesservicesystem.util;


import android.util.Log;

import java.sql.*;

public class DBUtil {
    // SQL Server
    // private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // JDBC 驱动名
    // private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=police"; // 数据库 URL
    // private static final String USER = "sa"; // 数据库的用户名
    // private static final String PASS = "6003"; // 数据库的密码


    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.1.157:3306/applianceafter-salesservsys?useUnicode=true&characterEncoding=UTF-8";
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://192.168.1.157:3306/sys?useSSL=false&serverTimezone=UTC";
    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "ApplianceAfter-salesServSys";
    private static final String PASSWORD = "ApplianceAfter-salesServSys";

    /**
     *
     */
    public DBUtil() {
        // TODO Auto-generated constructor stub
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Log.v("DBUtil", "==============================================================================");
            Log.v("DBUtil", "**********************************连接数据库***********************************");
            Log.v("DBUtil", "==============================================================================");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeDB(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Log.v("DBUtil", "==============================================================================");
        Log.v("DBUtil", "**********************************关闭数据库连接*******************************");
        Log.v("DBUtil", "==============================================================================");
    }
}
