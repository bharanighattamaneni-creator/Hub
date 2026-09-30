package com.library.util;

import java.sql.*;

public final class DBConnection {
    private DBConnection() {}

    private static String env(String key, String fallback) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? fallback : value;
    }

    public static Connection getConnection() throws SQLException {
        String host = env("DB_HOST", "localhost");
        String port = env("DB_PORT", "3306");
        String db = env("DB_NAME", "library_db");
        String user = env("DB_USER", "root");
        String password = env("DB_PASSWORD", "root");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata";
        return DriverManager.getConnection(url, user, password);
    }
}
