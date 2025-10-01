package ru.otus.chat.server.db;

import java.sql.*;
import java.time.*;
import java.util.*;

public class RoomsDao {
    private final Db db;
    public RoomsDao(Db db){ this.db = db; }

    public boolean exists(String name){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT 1 FROM rooms WHERE name=?")){
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()){ return rs.next(); }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void create(String name, String pass, String owner){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("INSERT INTO rooms(name, pass, owner) VALUES(?,?,?)")){
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, owner);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public boolean checkPass(String name, String pass){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT pass FROM rooms WHERE name=?")){
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()){
                if (!rs.next()) return false;
                String p = rs.getString(1);
                return Objects.equals(p, pass);
            }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public List<String> list(){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT name FROM rooms ORDER BY name")){
            try (ResultSet rs = ps.executeQuery()){
                List<String> out = new ArrayList<>();
                while (rs.next()) out.add(rs.getString(1));
                if (!out.contains("general")) out.add(0, "general");
                return out;
            }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void touch(String name){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("UPDATE rooms SET last_active=CURRENT_TIMESTAMP WHERE name=?")){
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public void cleanupOldEmptyRooms(Set<String> activeRooms){
        String base = "DELETE FROM rooms WHERE last_active < ? AND name NOT IN (%s)";
        Instant limit = Instant.now().minusSeconds(7L*24*3600);
        String inClause = activeRooms.isEmpty()? "''" : String.join(",", activeRooms.stream().map(s->"'"+s+"'").toList());
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement(String.format(base, inClause))){
            ps.setTimestamp(1, Timestamp.from(limit));
            ps.executeUpdate();
        } catch (SQLException e){ throw new RuntimeException(e); }
    }

    public boolean canOwnMore(String owner){
        try (Connection c = db.get(); PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM rooms WHERE owner=?")){
            ps.setString(1, owner);
            try (ResultSet rs = ps.executeQuery()){ rs.next(); return rs.getInt(1) < 5; }
        } catch (SQLException e){ throw new RuntimeException(e); }
    }
}
