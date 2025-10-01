package ru.otus.chat.server.db;

import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Db {
    private final String url;
    public Db(String url) { this.url = url; }

    public Connection get() throws SQLException {
        return DriverManager.getConnection(url);
    }

    public void init() {
        try (Connection c = get()) {
            InputStream is = Db.class.getClassLoader().getResourceAsStream("schema.sql");
            if (is == null) throw new IllegalStateException("schema.sql not found in resources");
            String schema = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
            for (String stmt : schema.split(";\\s*\\n")) {
                if (!stmt.isBlank()) try (Statement s = c.createStatement()) { s.execute(stmt); }
            }
        } catch (Exception e) {
            throw new RuntimeException("DB init failed", e);
        }
    }
}