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
    private String table;

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
            table = ConfigManager.getInstance().getTable();
            connection = handler.getConnection(ConfigManager.getInstance().getName(), GlobalPrefix.getInstance());
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + table + "` (" +
                    "`name`  varchar(50) NOT NULL ," +
                    "`prefix`  varchar(50) ," +
                    "`suffix`  varchar(50) ," +
                    "PRIMARY KEY (`name`)," +
                    "UNIQUE INDEX `name` (`name`) USING BTREE " +
                    ");");
            statement.close();
            QUERY = "SELECT * FROM `" + table + "` WHERE `name` = ?;";
            UPDATE = "UPDATE `" + table + "` SET `prefix` = ?,`suffix` = ? WHERE `name` = ?;";
            INSERT = "INSERT INTO `" + table + "` VALUES(?,?,?);";
            DELETE = "DELETE FROM `" + table + "` WHERE `name` = ?;";
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

    private String QUERY;
    private String UPDATE;
    private String INSERT;
    private String DELETE;

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

    public void deleteData(String name) {
        checkConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
