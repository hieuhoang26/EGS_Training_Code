package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    private  static final String URL = "jdbc:postgresql://localhost:5432/user_post";
    private static final String USER = "postgres";
    private static final String PASS = "12345678";

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e){
//            throw new RuntimeException("PostgreSQL Driver not found", e);
//        }
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASS);

        // pool
//        config.setMaximumPoolSize(10);
//        config.setMinimumIdle(2);
//        config.setIdleTimeout(30000);
//        config.setConnectionTimeout(30000);

        config.setMaximumPoolSize(3);
        config.setMinimumIdle(1);
        config.setIdleTimeout(10000);
        config.setConnectionTimeout(5000);
        config.setPoolName("JDBC-Hikari");


        dataSource = new HikariDataSource(config);
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }

    // close data source
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
