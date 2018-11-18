package ua.iot.lviv.andriy;

import java.sql.*;
import java.util.Scanner;

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
            while(true){
                System.out.println("1. Read data");
                System.out.println("2. Update city");
                System.out.println("3. Delate suppliers");
                System.out.println("4. Insert suppliers");
                Scanner input = new Scanner(System.in);
                int k=input.nextInt();
                switch (k){
                    case 1: readData();
                    break;
                    case 2: updateDataCity();
                    break;
                    case 3: deleteDataSuppliers();
                    break;
                    case 4: insertDataCity();
                    break;
                }
            }





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
        System.out.format("%-15s %-15s\n", "Name", "City" );
        while (rs.next()) {
            String name = rs.getString("name");
            String city = rs.getString("city");
            System.out.format("%-15s %-15s\n", name, city);
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

        rs = statement.executeQuery("SELECT * FROM  suppliers_fruits");

        System.out.format("\nTable Suppliers_Fruit --------------------\n");
        System.out.printf("%-15s %-15s %-10s\n", "Name","Fruit","Price");
        while (rs.next()) {

            String supplier = rs.getString("supplier");
            String fruit=rs.getString("fruit");
            double price=rs.getDouble("price");

            // Simply Print the results
            System.out.format("%-15s %-15s %-10.2f\n", supplier, fruit,price);
        }


    }
    private static void updateDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input name city what you want to update: ");
        String city = input.next();
        System.out.println("Input new name city for %s: " + city);
        String citynew = input.next();
        statement.execute("UPDATE suppliers SET city='" + citynew + "' WHERE city='" + city + "';");
    }
    private static void insertDataCity() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a new name suppliers: ");
        String newname = input.next();
        System.out.println("Input a new suppliers city: ");
        String city=input.next();


        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement("INSERT suppliers VALUES (?,?)");
        preparedStatement.setString(1, newname);
        preparedStatement.setString(2, city);
        int n = preparedStatement.executeUpdate();
        System.out.println("Count rows that inserted: " + n);
    }

    private static void deleteDataSuppliers() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("Input a name suppliers for delete: ");
        String name = input.next();


        // 3. executing SELECT query
        //   PreparedStatements can use variables and are more efficient
        PreparedStatement preparedStatement;
        preparedStatement=connection.prepareStatement("DELETE FROM suppliers WHERE name=?");
        preparedStatement.setString(1, name);
        int n=preparedStatement.executeUpdate();
        System.out.println("Count rows that deleted: "+n);
    }
}
