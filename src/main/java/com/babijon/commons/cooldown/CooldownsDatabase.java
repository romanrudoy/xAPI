package com.babijon.commons.cooldown;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CooldownsDatabase {

    private final String url;

    public CooldownsDatabase() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

        url = "jdbc:sqlite:plugins/xAPI/cooldowns.db";
        Class.forName("org.sqlite.JDBC").newInstance();

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS cooldowns (key TEXT NOT NULL PRIMARY KEY, time INTEGER);");

        statement.execute();

        statement.close();
        connection.close();

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void saveAllCooldowns(Map<String, Cooldown> cooldownMap) throws SQLException {

        Connection connection = getConnection();
        PreparedStatement statement;

        for (String key : cooldownMap.keySet()) {

            statement = connection.prepareStatement("REPLACE INTO cooldowns(name, time) VALUES (?,?)");
            statement.setString(1, key);
            statement.setInt(2, (int) cooldownMap.get(key).secondsLeft());

            statement.execute();
            statement.close();

        }

        connection.close();

    }

    public Map<String, Cooldown> loadCooldowns() throws SQLException {

        Connection connection = getConnection();
        Map<String, Cooldown> cooldownMap = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM cooldowns")) {
            try(ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    cooldownMap.put(resultSet.getString(1), new Cooldown(resultSet.getInt(1)));
                }

            }

            statement.close();
            connection.close();
        }

        return cooldownMap;

    }

}
