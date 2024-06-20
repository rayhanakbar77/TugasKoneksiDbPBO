package javadb;

import java.sql.*;

public class DatabaseManager {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1/TugasPBO";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connectToDatabase();
            insertProductData();
            displayProductData();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private static void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    private static void insertProductData() throws SQLException {
        String productCode = "brg17";
        String productName = "Mie Goreng";
        String unit = "Bungkus";
        int stock = 40;
        int minimumStock = 1;

        String sql = "INSERT INTO barang (kd_brg, nm_brg, satuan, stok_brg, stok_min) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, productCode);
        statement.setString(2, productName);
        statement.setString(3, unit);
        statement.setInt(4, stock);
        statement.setInt(5, minimumStock);

        statement.execute();
    }

    private static void displayProductData() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM barang");

        int counter = 1;
        while (resultSet.next()) {
            System.out.println("Data ke-" + counter);
            System.out.println("Kode Barang: " + resultSet.getString("kd_brg"));
            System.out.println("Nama Barang: " + resultSet.getString("nm_brg"));
            System.out.println("Satuan: " + resultSet.getString("satuan"));
            System.out.println("Stok: " + resultSet.getInt("stok_brg"));
            System.out.println("Stok minimal: " + resultSet.getInt("stok_min"));
            counter++;
        }

        resultSet.close();
        statement.close();
    }

    private static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
