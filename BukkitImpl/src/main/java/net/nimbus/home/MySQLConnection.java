package net.nimbus.home;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MySQLConnection {
    private static Connection connection;

    public static boolean initiate(String address, String name, String user, String password){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + address + "/" + name + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false", user, password);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean createIfNotExists(String table, String primary_key, String primary_value_type, String... args) {
        try {
            NHome.a.getLogger().info("Trying to create " + table + " mysql table...");
            String cmd = "";
            for (int i = 0; i < args.length; i+=2) {
                cmd = cmd + ", " + args[i] + " " + args[i + 1].toUpperCase(Locale.ROOT);
            }
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS " + table + " (" + primary_key + " " + primary_value_type + cmd + ", PRIMARY KEY (" + primary_key + "))");
            statement.execute();
            statement.close();
            NHome.a.getLogger().info("Success!");
            return true;
        } catch (SQLException e) {
            NHome.a.getLogger().severe("Failed!");
            e.printStackTrace();
            return false;
        }
    }
    public static String getString(String table, String key_column, String key_value, String column) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + key_column + " = '" + key_value + "'");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(column);
            }
            return "";
        } catch (SQLException e) {
            return "";
        }
    }
    public static List<String> getColumnAsList(String table, String key_column, String key_value, String column) {
        List<String> keys = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + key_column + " = '" + key_value + "'");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                keys.add(rs.getString(column));
            }
            return keys;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    public static List<String> getColumnAsList(String table, String column) {
        ArrayList<String> keys = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                keys.add(rs.getString(column));
            }
            return keys;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
    public static boolean set(String table, String key_column, String key_value, String column, String value) {
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + key_column + " = '" + key_value + "'");
            st.execute("INSERT INTO " + table + "(" + key_column + ", " + column + ") VALUES ('" + key_value + "', '" + value + "') ON DUPLICATE KEY UPDATE " + column + " = '" + value + "'");
            st.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean exists(String table, String key_column, String key_value) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + key_column + " = '" + key_value + "'");
            return statement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
    public static void remove(String table, String key_column, String key_value) {
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM " + table);
            st.execute("DELETE FROM " + table + " WHERE " + key_column + " = '" + key_value + "'");
            st.close();
        } catch (SQLException ignored) {}
    }
}
