import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) throws IllegalArgumentException
    {
        if(name.length() == 0 || password.length() == 0 || login.length() == 0) throw new IllegalArgumentException("Неверные значения пользователя");
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public boolean enter(String login, String password)
    {
        return this.login.equals(login) && this.password.equals(password);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public String getLogin() { return login; }

    public String getPassword() { return  password; }
}
