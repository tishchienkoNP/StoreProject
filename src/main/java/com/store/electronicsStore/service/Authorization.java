package com.store.electronicsStore.service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorization extends dbConnection{

    public void createNewAccount(String login, String password) throws SQLException,ClassNotFoundException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.users;");
        while (resultSet.next()) {
            String loginDB = resultSet.getString("login");
            if (login.equals(loginDB)) {
                break;
            }
        }
        statement.executeUpdate("INSERT INTO store.users(login, password) " +
                "VALUES ('" + login + "', '" + password + "');");
    }

    public boolean loginUser(String login, String password) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.users;");
        while(resultSet.next()) {
            if (login.equals(resultSet.getString("login"))
                    && password.equals(resultSet.getString("password"))) {
                statement.executeUpdate("INSERT INTO store.temp(tempId,tempLogin) VALUES (default,'"+login+"');");
                return true;
            }
        }
        return false;
    }

    public void setLogin(String login) throws SQLException {
        statement.executeUpdate("INSERT INTO store.temp(tempLogin) VALUES ('"+login+"');");
    }

    public String getLogin() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT tempLogin FROM store.temp");
        String k = "";
        while(resultSet.next()){
            k = resultSet.getString("tempLogin");
        }
        return k;
    }
    public void exitAccount(String login) throws SQLException{
        statement.executeUpdate("UPDATE store.temp SET tempLogin=null WHERE login='"+login+"';");
    }
}
