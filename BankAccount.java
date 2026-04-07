public class BankAccount {
    int accountNumber;
    String username;
    double balance;

    public BankAccount(int accountNumber, String username, double balance) {
        this.accountNumber = accountNumber;
        this.username = username;
        this.balance = balance;
    }

    public void deposit(double amount) {
        // пополнение
        if (amount <= 0) {
            System.out.println("Wrong amount");
            return;
        }
        balance += amount;
    }

    public boolean withdraw(double amount) {
        // снятие
        if (amount <= 0) {
            System.out.println("Wrong amount");
            return false;
        }
        if (amount > balance) {
            System.out.println("Not enough money");
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return "#" + accountNumber + " | " + username + " | balance: " + balance;
    }
}
