package com.jibaolan.onlineexamsystem.Utils;

import java.sql.*;

public class DataBaseConnection {
    private static final String connStr="jdbc:mysql://localhost:3306/OES?useSSL=false&serverTimezone=UTC";
    private static final String UNAME="root";
    private static final String PASSWD="M+31415926pipiS1";
    private static Connection connection=null;
    private static Statement statement=null;

    public DataBaseConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(connStr,UNAME,PASSWD);
        statement  = connection.createStatement();
    }

    public ResultSet Query(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public boolean Exec(String sql) throws SQLException {
        return statement.execute(sql);
    }

    public int Update(String sql) throws SQLException {
        return statement.executeUpdate(sql);
    }

    public void close() throws SQLException {
        statement.close();
        connection.close();
    }


}
