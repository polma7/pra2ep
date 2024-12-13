package data;

public final class UserAccount {
    private final String username;
    private final String mail;
    private final String password;

    public UserAccount(String username, String mail, String password){
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public String getMail(){
        return this.mail;
    }

    @Override
    public boolean equals(Object o){
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount acc = (UserAccount) o;
        eq = (acc.getMail().equals(this.mail) && acc.getUsername().equals(this.username)
                && acc.password.equals(this.password));
        return eq;
    }

    @Override
    public String toString(){
        return "User Account { Username = " + username + ", Mail = " + mail + " }";
    }
}
