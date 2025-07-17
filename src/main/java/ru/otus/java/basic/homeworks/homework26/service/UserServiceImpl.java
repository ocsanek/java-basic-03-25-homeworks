package ru.otus.java.basic.homeworks.homework26.service;

import ru.otus.java.basic.homeworks.homework26.entity.Role;
import ru.otus.java.basic.homeworks.homework26.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "postgres";

    private static final String USERS_QUERY = "select * from users;";
    private static final String USER_ROLES_QUERY = """
            select r.id, r."name" from  roles r
            join users_to_roles utr on r.id = utr.role_id
            where  utr.user_id = ?;
            """;
    private static final String IS_ADMIN_QUERY = """
            select count(1) from roles r
            join users_to_roles utr on r.id = utr.role_id
            where  utr.user_id = ? and r.name = 'admin';
            """;
    private static final String AUTH_QUERY = """
            select * from users where email = ? and password = ?;
            """;

    private final Connection connection;

    public UserServiceImpl() throws SQLException {
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(USERS_QUERY)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    User currentUser = new User(id, password, email);
                    result.add(currentUser);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (PreparedStatement ps = connection.prepareStatement(USER_ROLES_QUERY)) {
            for (User user : result) {
                ps.setInt(1, user.getId());
                List<Role> currentRoles = new ArrayList<>();
                try (ResultSet resultSet = ps.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        Role currentCole = new Role(id, name);
                        currentRoles.add(currentCole);
                    }
                }
                user.setRoles(currentRoles);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean isAdmin(int userId) {
        int flag = 0;
        try (PreparedStatement ps = connection.prepareStatement(IS_ADMIN_QUERY)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    flag = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flag == 1;
    }
    @Override
    public Optional<User> authenticate(String email, String password) {
        try (PreparedStatement ps = connection.prepareStatement(AUTH_QUERY)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    User user = new User(id, password, email);

                    try (PreparedStatement rolesStmt = connection.prepareStatement(USER_ROLES_QUERY)) {
                        rolesStmt.setInt(1, id);
                        try (ResultSet roleSet = rolesStmt.executeQuery()) {
                            List<Role> roles = new ArrayList<>();
                            while (roleSet.next()) {
                                int roleId = roleSet.getInt(1);
                                String roleName = roleSet.getString(2);
                                roles.add(new Role(roleId, roleName));
                            }
                            user.setRoles(roles);
                        }
                    }
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка аутентификации", e);
        }
        return Optional.empty();
    }
}
