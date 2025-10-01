package ru.otus.chat.server.db;

import java.sql.*;
import java.time.*;

public class UsersDao {
    private final Db db;
    public UsersDao(Db db){this.db = db;}

    public static record User(String login, String username, String role, Timestamp bannedUntil){}

    public User auth(String login, String pass){
        String sql = "SELECT username, role, banned_until, pass_hash FROM users WHERE login=?";
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()){
                if (!rs.next()) return null;
                String ph = rs.getString("pass_hash");
                if (!ph.equals("{PLAIN}"+pass)) return null;
                Timestamp banned = rs.getTimestamp("banned_until");
                if (banned != null && banned.toInstant().isAfter(Instant.now())) {
                    throw new IllegalStateException("BANNED_UNTIL:"+banned.toString());
                }
                return new User(login, rs.getString("username"), rs.getString("role"), banned);
            }
        } catch (IllegalStateException ise){
            throw ise;
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public boolean loginExists(String login){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT 1 FROM users WHERE login=?")){
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()){ return rs.next(); }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public boolean usernameExists(String username){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT 1 FROM users WHERE username=?")){
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()){ return rs.next(); }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void create(String login, String pass, String username){
        String sql = "INSERT INTO users(login, pass_hash, username, role) VALUES(?,?,?, 'user')";
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, login);
            ps.setString(2, "{PLAIN}"+pass);
            ps.setString(3, username);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void touchActivity(String username){
        if (username==null) return;
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("UPDATE users SET last_activity=CURRENT_TIMESTAMP WHERE username=?")){
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void ban(String username, Integer minutes, boolean perm){
        String sql = "UPDATE users SET banned_until=? WHERE username=?";
        Timestamp ts = perm? Timestamp.from(Instant.now().plusSeconds(100L*365*24*3600))
                : Timestamp.from(Instant.now().plusSeconds(minutes*60L));
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setTimestamp(1, ts);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void changeNick(String oldU, String newU){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("UPDATE users SET username=? WHERE username=?")){
            ps.setString(1, newU);
            ps.setString(2, oldU);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public Timestamp lastActivity(String username){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT last_activity FROM users WHERE username=?")){
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()){ return rs.next()? rs.getTimestamp(1) : null; }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    // утилита для простых операций
    public Connection getConnection() throws SQLException { return db.get(); }
}