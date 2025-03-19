import java.util.*;

public class Account {
    private String name;
    private String uuid;
    private User holder;
    private List<Transaction> transactions;
    private double balance;

    public Account(String name, User holder, Bank theBank) {
        this(name, holder, theBank, 0);
    }

    public Account(String name, User holder, Bank theBank, double initialDeposit) {
        this.name = name;
        this.holder = holder;
        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<>();
        this.balance = initialDeposit;
        if (initialDeposit > 0) {
            addTransaction(initialDeposit, "Initial deposit");
        }
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        return String.format("%s : %s : %s", this.uuid, formatCurrency(this.balance), this.name);
    }

    public double getBalance() {
        return this.balance;
    }

    public void printTransHistory() {
        System.out.println("Transaction history for account: " + this.uuid);
        transactions.forEach(transaction -> System.out.println(transaction.getSummaryLine()));
    }

    public void addTransaction(double amount, String memo) {
        transactions.add(new Transaction(amount, memo, this));
        this.balance += amount;
    }

    private String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }
}
