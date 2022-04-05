package com.store.electronicsStore.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkWithProduct extends dbConnection {

    public boolean storeProduct(String login, int idProduct) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.sessions WHERE idProduct="+idProduct+";");
        int maxSeats = 0;
        while(resultSet.next()) {
            maxSeats = resultSet.getInt("maxSeats");
        }
        if (maxSeats > storeUsers() && compareProduct(login,idProduct)){
            statement.executeUpdate("INSERT INTO product.storeproduct(idusers,idProduct) VALUES ('"+login+"',"+idProduct+")");
            return true;
        }
        return false;
    }

    public int getIdProduct(String nameProduct, String date, String time) throws SQLException{
        System.out.println(nameProduct +" "+ date +" "+ time);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.sessions WHERE nameProduct='"+nameProduct+"' " +
                "and date='"+date+"' and time='"+time+"';");
        int k = 0;
        k = resultSet.getInt("idProduct");
        System.out.println(k);

        return k;
    }

    public boolean deleteProduct(String login, int idProduct) throws SQLException {
        if (!compareProduct(login,idProduct)){
            statement.executeUpdate("DELETE FROM store.storeproduct WHERE idusers='"+login+"' and idProduct="+idProduct+";");
            return true;
        }
        return false;
    }

    public List<String> allProduct() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT nameProduct FROM store.sessions");
        List<String> all = new ArrayList<>();
        while(resultSet.next()){
            String temp = resultSet.getString("nameProduct");
            all.add(temp);
        }
        return all;
    }
    public List<String> allDate(String nameProduct) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT date FROM store.sessions WHERE nameProduct='"+nameProduct+"';");
        List<String> allDate = new ArrayList<>();
        while(resultSet.next()){
            String temp = resultSet.getString("date");
            allDate.add(temp);
        }
        return allDate;
    }
    public List<String> allTime(String nameProduct,String date) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT time FROM store.sessions WHERE nameProduct='"+nameProduct+"' and date='"+date+ "';");
        List<String> allTime = new ArrayList<>();
        while(resultSet.next()){
            String temp = resultSet.getString("time");
            allTime.add(temp);
        }
        return allTime;
    }

    public int storeUsers() throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.storeproduct");
        int count = 0;
        while(resultSet.next()){
            count += 1;
        }
        return count;
    }

    public boolean compareProduct (String login, int idProduct) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.storeproduct WHERE idusers='"+login+"' and idProduct="+idProduct+";");
        while(resultSet.next()){
            String loginDB = resultSet.getString("idusers");
            int idProductDB = resultSet.getInt("idProduct");
            if (login.equals(loginDB) && idProduct == idProductDB){
                return false;
            }
        }
        return true;
    }

    public void checkHall(int idProduct, int hallName) throws SQLException {
        String request = "SELECT * FROM storeproduct WHERE idProduct = "+idProduct+" and hallName="+hallName+";";
        ResultSet resultSet = statement.executeQuery(request);
        List<Integer> row = new ArrayList<>();
        List<Integer> col = new ArrayList<>();
        while (resultSet.next()){
            int row1 = resultSet.getInt("row");
            int col1 = resultSet.getInt("col");
            row.add(row1);
            col.add(col1);
        }
    }

    public void increaseBalance(String login, int sum) throws SQLException {
        statement.executeUpdate("UPDATE store.users SET balance=balance+"+sum+" WHERE login='"+login+"';");
    }
    public void buyProductBalance(String login, int idProduct) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.sessions WHERE idProduct="+idProduct+";");
        while (resultSet.next()){
            int cost = resultSet.getInt("balance");
            statement.executeUpdate("UPDATE store.users SET balance=balance-"+cost+" WHERE login='"+login+"';");
        }
    }

    public boolean checkBalance(String login, int idProduct) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM store.users WHERE login='"+login+"'");
        ResultSet resultSet1 = statement.executeQuery("SELECT * FROM store.sessions WHERE idProduct="+idProduct+";");
        while(resultSet.next() && resultSet1.next()){
            int balance = resultSet.getInt("balance");
            int cost = resultSet1.getInt("cost");
            if (balance <= cost){
                return false;
            }
        }
        return true;
    }
}

