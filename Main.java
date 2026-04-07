import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankSystem bank = new BankSystem();

        while (true) {
            System.out.println("Main menu:");
            System.out.println("1 - Bank");
            System.out.println("2 - ATM");
            System.out.println("3 - Admin");
            System.out.println("4 - Exit");
            System.out.print("> ");

            int choice = readInt(sc);
            if (choice == -1) {
                System.out.println("Wrong input");
                continue;
            }

            if (choice == 1) {
                bankMenu(sc, bank);
            } else if (choice == 2) {
                atmMenu(sc, bank);
            } else if (choice == 3) {
                adminMenu(sc, bank);
            } else if (choice == 4) {
                System.out.println("Bye");
                break;
            } else {
                System.out.println("Wrong menu number");
            }

            System.out.println();
        }

        sc.close();
    }

    static void bankMenu(Scanner sc, BankSystem bank) {
        while (true) {
            System.out.println("Bank menu:");
            System.out.println("1 - Submit account request");
            System.out.println("2 - Deposit");
            System.out.println("3 - Withdraw");
            System.out.println("4 - Add bill to queue");
            System.out.println("5 - Back");
            System.out.print("> ");

            int c = readInt(sc);
            if (c == -1) {
                System.out.println("Wrong input");
                continue;
            }

            if (c == 1) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                bank.submitAccountRequest(username);
            } else if (c == 2) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Amount: ");
                double amount = readDouble(sc);
                if (amount == -1) {
                    System.out.println("Wrong amount");
                } else {
                    bank.depositToUser(username, amount);
                }
            } else if (c == 3) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Amount: ");
                double amount = readDouble(sc);
                if (amount == -1) {
                    System.out.println("Wrong amount");
                } else {
                    bank.withdrawFromUser(username, amount);
                }
            } else if (c == 4) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Amount: ");
                double amount = readDouble(sc);
                if (amount == -1) {
                    System.out.println("Wrong amount");
                } else {
                    System.out.print("Bill text: ");
                    String billText = sc.nextLine();
                    bank.addBill(username, amount, billText);
                    System.out.println("Bill added");
                }
            } else if (c == 5) {
                break;
            } else {
                System.out.println("Wrong menu number");
            }

            System.out.println();
        }
    }

    static void atmMenu(Scanner sc, BankSystem bank) {
        while (true) {
            System.out.println("ATM menu:");
            System.out.println("1 - Show balance");
            System.out.println("2 - Withdraw");
            System.out.println("3 - Back");
            System.out.print("> ");

            int c = readInt(sc);
            if (c == -1) {
                System.out.println("Wrong input");
                continue;
            }

            if (c == 1) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                BankAccount acc = bank.findByUsername(username);
                if (acc == null) {
                    System.out.println("User not found");
                } else {
                    System.out.println("Balance: " + acc.balance);
                }
            } else if (c == 2) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Amount: ");
                double amount = readDouble(sc);
                if (amount == -1) {
                    System.out.println("Wrong amount");
                } else {
                    bank.withdrawFromUser(username, amount);
                }
            } else if (c == 3) {
                break;
            } else {
                System.out.println("Wrong menu number");
            }

            System.out.println();
        }
    }

    static void adminMenu(Scanner sc, BankSystem bank) {
        while (true) {
            System.out.println("Admin menu:");
            System.out.println("1 - Show account requests");
            System.out.println("2 - Process next request");
            System.out.println("3 - Show bills queue");
            System.out.println("4 - Process next bill");
            System.out.println("5 - Show all accounts");
            System.out.println("6 - Last transaction");
            System.out.println("7 - Undo (history)");
            System.out.println("8 - Back");
            System.out.print("> ");

            int c = readInt(sc);
            if (c == -1) {
                System.out.println("Wrong input");
                continue;
            }

            if (c == 1) {
                bank.showAccountRequests();
            } else if (c == 2) {
                bank.processNextRequest();
            } else if (c == 3) {
                bank.showBillsQueue();
            } else if (c == 4) {
                bank.processNextBill();
            } else if (c == 5) {
                bank.showAllAccounts();
            } else if (c == 6) {
                bank.showLastTransaction();
            } else if (c == 7) {
                bank.undoTransaction();
            } else if (c == 8) {
                break;
            } else {
                System.out.println("Wrong menu number");
            }

            System.out.println();
        }
    }

    static int readInt(Scanner sc) {
        if (!sc.hasNextInt()) {
            sc.nextLine();
            return -1;
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    static double readDouble(Scanner sc) {
        if (!sc.hasNextDouble()) {
            sc.nextLine();
            return -1;
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }
}
