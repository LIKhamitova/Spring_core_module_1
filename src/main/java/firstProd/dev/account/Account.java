package firstProd.dev.account;

import firstProd.dev.exception.AccountException;

public class Account {
    private final int id;
    private final int userId;
    private int moneyAmount;

    public Account(int id, int userId ,int moneyAmount ) {
        this.moneyAmount = moneyAmount;
        this.id = id;
        this.userId = userId;
    }

    public void addMoney(int amount) {
        if( amount <= 0) {
            throw new AccountException("Error: The amount must be positive");
        }
        this.moneyAmount  = this.moneyAmount + amount;
    }

    public void subMoney(int amount) {
        if( amount <= 0) {
            throw new AccountException("Error: The amount must be positive");
        }

        if ( this.moneyAmount < amount) {
            throw new AccountException("Error: Insufficient funds in the account");
        }
        this.moneyAmount  = this.moneyAmount - amount;
    }


    public int balance() {
        return moneyAmount;
    }

    public int userId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }

}
