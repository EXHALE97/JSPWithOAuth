package com.javaeehandbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

class Database {
    private String url = "";
    private String user = "";
    private String password = "";

    Database() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();

        url = ResourceBundle.getBundle("config").getString("db.url");
        user = ResourceBundle.getBundle("config").getString("db.user");
        password = ResourceBundle.getBundle("config").getString("db.password");

    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
