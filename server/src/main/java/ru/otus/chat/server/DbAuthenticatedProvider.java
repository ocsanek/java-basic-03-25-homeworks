package ru.otus.chat.server;

import ru.otus.chat.server.db.UsersDao;

public class DbAuthenticatedProvider implements AuthenticatedProvider {
    private final Server server;
    private final UsersDao users;

    public DbAuthenticatedProvider(Server server, UsersDao users){
        this.server = server; this.users = users;
    }

    @Override public void initialize(){ System.out.println("AuthenticatedProvider: H2 mode"); }

    @Override
    public boolean authenticate(ClientHandler ch, String login, String password){
        try {
            var u = users.auth(login, password);
            if (u == null){ ch.sendMsg("/error AUTH_FAIL Недействительные_учётные_данные"); return false; }
            if (server.isUsernameBusy(u.username())){ ch.sendMsg("/error AUTH_BUSY Аккаунт_уже_в_чате"); return false; }
            ch.setUsername(u.username());
            ch.setRole(u.role());
            server.subscribe(ch);
            users.touchActivity(u.username());
            ch.sendMsg("/authok " + u.username());
            return true;
        } catch (IllegalStateException ise){
            if (ise.getMessage()!=null && ise.getMessage().startsWith("BANNED_UNTIL:")){
                ch.sendMsg("/error BANNED " + ise.getMessage().substring("BANNED_UNTIL:".length()));
                return false;
            }
            throw ise;
        }
    }

    @Override
    public boolean registration(ClientHandler ch, String login, String password, String username){
        if (login.length()<3){ ch.sendMsg("/error REG_LOGIN_TOO_SHORT min3"); return false; }
        if (username.length()<3){ ch.sendMsg("/error REG_USERNAME_TOO_SHORT min3"); return false; }
        if (users.loginExists(login)){ ch.sendMsg("/error REG_LOGIN_TAKEN "); return false; }
        if (users.usernameExists(username)){ ch.sendMsg("/error REG_USERNAME_TAKEN "); return false; }
        users.create(login, password, username);
        ch.setUsername(username);
        ch.setRole("user");
        server.subscribe(ch);
        users.touchActivity(username);
        ch.sendMsg("/regok " + username);
        return true;
    }
}