import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BankSystem {
    LinkedList<BankAccount> accounts = new LinkedList<>();
    Stack<String> transactions = new Stack<>();

    Queue<String> billsQueue = new LinkedList<>();
    Queue<String> accountRequests = new LinkedList<>();

    BankAccount[] demoArray = new BankAccount[3];

    int nextAccountNumber = 1001;

    public BankSystem() {
        // массив из 3 аккаунтов
        demoArray[0] = new BankAccount(1, "demo1", 100);
        demoArray[1] = new BankAccount(2, "demo2", 200);
        demoArray[2] = new BankAccount(3, "demo3", 300);

        System.out.println("Demo array accounts:");
        for (int i = 0; i < demoArray.length; i++) {
            System.out.println(demoArray[i]);
        }
        System.out.println();

        // добавим один аккаунт в LinkedList чтобы было с чем работать
        addAccount(new BankAccount(nextAccountNumber++, "user", 500));
    }

    public void addAccount(BankAccount acc) {
        // добавляем аккаунт
        accounts.add(acc);
    }

    public void showAllAccounts() {
        // показываем все аккаунты
        if (accounts.size() == 0) {
            System.out.println("No accounts");
            return;
        }
        for (BankAccount a : accounts) {
            System.out.println(a);
        }
    }

    public BankAccount findByUsername(String username) {
        // поиск по username
        for (BankAccount a : accounts) {
            if (a.username.equalsIgnoreCase(username)) {
                return a;
            }
        }
        return null;
    }

    public void depositToUser(String username, double amount) {
        BankAccount acc = findByUsername(username);
        if (acc == null) {
            System.out.println("User not found");
            return;
        }
        double old = acc.balance;
        acc.deposit(amount);
        if (acc.balance != old) {
            transactions.push("deposit: " + username + " +" + amount);
            System.out.println("Done. New balance: " + acc.balance);
        }
    }

    public void withdrawFromUser(String username, double amount) {
        BankAccount acc = findByUsername(username);
        if (acc == null) {
            System.out.println("User not found");
            return;
        }
        boolean ok = acc.withdraw(amount);
        if (ok) {
            transactions.push("withdraw: " + username + " -" + amount);
            System.out.println("Done. New balance: " + acc.balance);
        }
    }

    public void addBill(String username, double amount, String text) {
        // добавляем счет
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Wrong username");
            return;
        }
        if (amount <= 0) {
            System.out.println("Wrong amount");
            return;
        }
        if (text == null) {
            text = "";
        }

        // формат: username;amount;text
        String bill = username.trim() + ";" + amount + ";" + text.trim();
        billsQueue.add(bill);
        transactions.push("bill added: " + username.trim() + " -" + amount);
    }

    public void processNextBill() {
        // обработать следующий
        if (billsQueue.isEmpty()) {
            System.out.println("No bills");
            return;
        }

        String bill = billsQueue.poll();
        String[] parts = bill.split(";", 3);
        if (parts.length < 2) {
            System.out.println("Wrong bill format");
            return;
        }

        String username = parts[0];
        double amount;
        try {
            amount = Double.parseDouble(parts[1]);
        } catch (Exception e) {
            System.out.println("Wrong bill amount");
            return;
        }

        String text = "";
        if (parts.length == 3) {
            text = parts[2];
        }

        BankAccount acc = findByUsername(username);
        if (acc == null) {
            System.out.println("User not found for bill: " + username);
            return;
        }

        boolean ok = acc.withdraw(amount);
        if (!ok) {
            System.out.println("Bill not paid: " + username + " -" + amount);
            return;
        }

        System.out.println("Bill paid: " + username + " -" + amount + " (" + text + ")");
        System.out.println("New balance: " + acc.balance);
        transactions.push("bill paid: " + username + " -" + amount);
    }

    public void showBillsQueue() {
        // показать очередь
        if (billsQueue.isEmpty()) {
            System.out.println("No bills");
            return;
        }
        System.out.println("Bills queue:");
        for (String b : billsQueue) {
            String[] parts = b.split(";", 3);
            if (parts.length >= 2) {
                String username = parts[0];
                String amount = parts[1];
                String text = "";
                if (parts.length == 3) {
                    text = parts[2];
                }
                System.out.println("- " + username + " | " + amount + " | " + text);
            } else {
                System.out.println("- " + b);
            }
        }
    }

    public void submitAccountRequest(String username) {
        // пользователь подает заявку
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Wrong username");
            return;
        }
        accountRequests.add(username.trim());
        System.out.println("Request added");
    }

    public void showAccountRequests() {
        // показать заявки
        if (accountRequests.isEmpty()) {
            System.out.println("No requests");
            return;
        }
        System.out.println("Requests:");
        for (String r : accountRequests) {
            System.out.println("- " + r);
        }
    }

    public void processNextRequest() {
        // админ обрабатывает
        if (accountRequests.isEmpty()) {
            System.out.println("No requests");
            return;
        }
        String username = accountRequests.poll();

        if (findByUsername(username) != null) {
            System.out.println("This user already exists");
            return;
        }

        BankAccount acc = new BankAccount(nextAccountNumber++, username, 0);
        addAccount(acc);
        System.out.println("Account created: " + acc);
        transactions.push("create account: " + username);
    }

    public void showLastTransaction() {
        // показать последнюю
        if (transactions.isEmpty()) {
            System.out.println("No transactions");
            return;
        }
        System.out.println("Last: " + transactions.peek());
    }

    public void undoTransaction() {
        // undo
        if (transactions.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }
        String t = transactions.pop();
        System.out.println("Undo: " + t);
        System.out.println("(real balance not changed, only history)");
    }
}
