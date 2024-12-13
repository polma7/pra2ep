package data;

import data.exceptions.useracc.InvalidEmailFormatException;
import data.exceptions.useracc.NullOrEmptyUserAccountException;
import data.exceptions.useracc.WeakPasswordException;

public final class UserAccount {
    private final String username;
    private final String mail;
    private final String password;

    public UserAccount(String username, String mail, String password) throws NullOrEmptyUserAccountException, InvalidEmailFormatException, WeakPasswordException {
        if (username == null || username.isEmpty()) {
            throw new NullOrEmptyUserAccountException("Username cannot be null or empty");
        }
        if (mail == null || mail.isEmpty()) {
            throw new NullOrEmptyUserAccountException("Mail cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new NullOrEmptyUserAccountException("Password cannot be null or empty");
        }

        if (!mail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { // Simple email validation regex
            throw new InvalidEmailFormatException("Invalid email format");
        }

        if (password.length() < 8 || !password.matches(".*[!@#$%^&*].*")) { // Simple password validation
            throw new WeakPasswordException("Password is too weak. It must be at least 8 characters and contain a special character.");
        }
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
