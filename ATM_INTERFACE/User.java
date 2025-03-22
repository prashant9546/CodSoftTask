import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte[] pinHash;
    private List<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<>();
        this.pinHash = hashPin(pin);
        System.out.println("New User: " + firstName + " " + lastName + " with ID: " + this.uuid + " created");
    }

    private byte[] hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found.");
        }
    }

    public void addAccount(Account anAct) {
        this.accounts.add(anAct);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String pin) {
        return Arrays.equals(hashPin(pin), this.pinHash);
    }

    public void printAccountSummary() {
        System.out.println(this.firstName + "'s Account Summary:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + " " + accounts.get(i).getSummaryLine());
        }
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printActTransHistory(int actIdx) {
        this.accounts.get(actIdx).printTransHistory();
    }

    public double getAccountBalance(int actIdx) {
        return this.accounts.get(actIdx).getBalance();
    }

    public String getActUUID(int actIdx) {
        return this.accounts.get(actIdx).getUUID();
    }

    public void addActTransaction(int actIdx, double amount, String memo) {
        this.accounts.get(actIdx).addTransaction(amount, memo);
    }
}
