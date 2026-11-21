package firstProd.dev.user;

import firstProd.dev.account.Account;
import firstProd.dev.exception.UserException;

import java.util.List;

public class User {

    private final int id;
    private final String login;
    private final List<Account> accounts;

    public User(String login, int id, List<Account> accounts) {
        this.login = login;
        this.id = id;
        this.accounts = accounts;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccounts() {
        return List.copyOf(accounts);
    }

    public int getId() {
        return id;
    }

    public int countAccounts() {
        return accounts.size();
    }

    public void addAccountUser(Account account) {
        int idAccount = account.getId();
        for (Account value : accounts) {
            if (value.getId() == idAccount) {
                throw new UserException("The account already exists for this user");
            }
        }

        accounts.add(account);
    }

    public void removeAccountFromUser(int id) {
        boolean result = accounts.removeIf(account -> account.getId() == id);
        if (!result) {
            throw new UserException("Account with id " + id + " not found");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    public int getIdAccountByIndex(int index) {
        try {
            return accounts.get(index).getId();
        } catch (IndexOutOfBoundsException e) {
            throw new UserException("Index out of bounds");
        }

    }

}
