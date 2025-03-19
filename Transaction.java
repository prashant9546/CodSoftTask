import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private double amount;
    private LocalDateTime timestamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount, Account inAccount) {
        this(amount, "", inAccount);
    }

    public Transaction(double amount, String memo, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = LocalDateTime.now();
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s : %s : %s", timestamp.format(formatter), formatCurrency(amount), memo);
    }

    private String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }
}
