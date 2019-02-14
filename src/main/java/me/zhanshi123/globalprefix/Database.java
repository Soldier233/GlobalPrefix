package me.zhanshi123.globalprefix;

import me.zhanshi123.globalprefix.cacher.PlayerData;
import me.zhanshi123.jdbcconnectionbridge.ConnectionPoolHandler;
import me.zhanshi123.jdbcconnectionbridge.JdbcConnectionBridge;

import java.sql.*;

public class Database {
    private static Database instance;

    public static Database getInstance() {
        return instance;
    }

    private ConnectionPoolHandler handler;

    public void initialize() {
        handler = JdbcConnectionBridge.getConnectionPoolHandler();
        connect();
    }

    public Database() {
        initialize();
        instance = this;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        try {
            connection = handler.getConnection("default");
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `globalprefix` (" +
                    "`name`  varchar(255) NOT NULL ," +
                    "`prefix`  varchar(255) ," +
                    "`suffix`  varchar(255) ," +
                    "PRIMARY KEY (`name`)," +
                    "UNIQUE INDEX `name` (`name`) USING BTREE " +
                    ");");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;

    private void checkConnection() {
        try {
            if (connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final String QUERY = "SELECT * FROM `globalprefix` WHERE `name` = ?;";
    private final String UPDATE = "UPDATE `globalprefix` SET `prefix` = ?,`suffix` = ? WHERE `name` = ?;";
    private final String INSERT = "INSERT INTO `globalprefix` VALUES(?,?,?);";

    public PlayerData getData(String name) {
        checkConnection();
        PlayerData data = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                data = new PlayerData(name, rs.getString("prefix"), rs.getString("suffix"));
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void updateData(PlayerData data) {
        checkConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, data.getPrefix());
            preparedStatement.setString(2, data.getSuffix());
            preparedStatement.setString(3, data.getName());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertData(PlayerData data) {
        checkConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getPrefix());
            preparedStatement.setString(3, data.getSuffix());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
