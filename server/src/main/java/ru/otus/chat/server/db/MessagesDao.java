package ru.otus.chat.server.db;

import java.sql.*;
import java.util.*;

public class MessagesDao {
    private final Db db;
    public MessagesDao(Db db){ this.db = db; }

    public void savePublic(String sender, String room, String body){
        String sql = "INSERT INTO messages(sender, room, is_private, body) VALUES(?,?,FALSE,?)";
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, sender);
            ps.setString(2, room);
            ps.setString(3, body);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public List<String> lastRoomMessages(String room, int limit){
        String sql = "SELECT sender, body, ts FROM messages WHERE room=? AND is_private=FALSE ORDER BY id DESC LIMIT ?";
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, room);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()){
                List<String> out = new ArrayList<>();
                while (rs.next()){
                    out.add("[" + rs.getTimestamp("ts") + "] " + rs.getString("sender") + ": " + rs.getString("body"));
                }
                Collections.reverse(out);
                return out;
            }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }
}
