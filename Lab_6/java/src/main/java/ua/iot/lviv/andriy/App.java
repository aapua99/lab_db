package ua.iot.lviv.andriy;

import java.sql.*;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    private static final String url = "jdbc:mysql://localhost:3306/popov15?serverTimezone=UTC&useSSL=false";
    private static final String user = "root";
    private static final String password = "123456";

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();



            updateDataCity();
            readData();
            insertDataCity();
            readData();
            DeleteDataCity();
            readData();


        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver is not loaded");

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        } finally {
            //close connection ,statement and resultset
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            } // ignore
            if (statement != null) try {
                statement.close();
            } catch (SQLException e) {
            }
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    private static void readData() throws SQLException {

        rs = statement.executeQuery("SELECT * FROM  city");

        System.out.format("\nTable City --------------------\n");
        System.out.printf("%-18s\n", "Name");
        while (rs.next()) {

            String name = rs.getString("Name");

            // Simply Print the results
            System.out.format("%-18s\n", name);
        }


        rs = statement.executeQuery("SELECT * FROM fruits");

        System.out.format("\nTable Fruits ------------------------------\n");
        System.out.printf("%-18s %-10s %-10s \n", "Name", "Price", "Name of shop");
        while (rs.next()) {
            double price = rs.getDouble("price");
            String name = rs.getString("name");
            String nameShop = rs.getString("name_shop");
            // Simply Print the results
            System.out.format("%-18s %-10.2f %-18s \n", name, price, nameShop);
        }

        rs = statement.executeQuery("SELECT * FROM suppliers");
        System.out.println("\nTable Suppliers --------------------------------\n");
        System.out.format("%-15s %-10s %-10s %-15s\n", "Name", "Price", "City", "Fruit");
        while (rs.next()) {
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            String city = rs.getString("city");
            String fruit = rs.getString("fruit");
            System.out.format("%-15s %-10.2f %-10s %-15s\n", name, price, city, fruit);
        }

        rs = statement.executeQuery("SELECT * FROM shop");
        System.out.println("\nTable Shop --------------------------------------------\n");
        System.out.printf("%-12s %-20s %-18s\n", "Name", "Address", "Manager");
        while (rs.next()) {
            String name = rs.getString("name");
            String address = rs.getString("address");
            String manager = rs.getString("manager");
            System.out.printf("%-12s %-20s %-18s\n", name, address, manager);
        }

    }
    private static void updateDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name city what you want to update: ");
        String city = input.next();
        System.out.println("Input new name city for %s: " + city);
        String citynew = input.next();
        statement.execute("UPDATE city SET name='" + citynew + "' WHERE name='" + city + "';");
    }
    private static void insertDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name city: ");
        String newcity = input.next();


        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT city VALUES (?)");
        preparedStatement.setString(1, newcity);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);
    }

    private static void DeleteDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name city for delete: ");
        String city = input.next();

        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("DELETE FROM city WHERE name=?");
        preparedStatement.setString(1, city);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: "+n);
    }
}
