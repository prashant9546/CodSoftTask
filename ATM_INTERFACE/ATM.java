import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("State Bank Of India");

        // Add a user and their accounts
        User aUser = theBank.addUser("PRASHANT", "KUMAR", "1234");
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        while (true) {
            User curUser = mainMenuPrompt(theBank, sc);
            printUserMenu(curUser, sc);
        }
    }

    private static User mainMenuPrompt(Bank theBank, Scanner sc) {
        User authUser;
        do {
            System.out.println("\n\nWelcome to " + theBank.getName() + "\n");
            System.out.print("Enter User ID: ");
            String userID = sc.nextLine();
            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            authUser = theBank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("❌ Incorrect User ID/PIN. Please try again.\n");
            }
        } while (authUser == null);
        return authUser;
    }

    private static void printUserMenu(User theUser, Scanner sc) {
        int choice;
        do {
            theUser.printAccountSummary();
            System.out.println("\nWelcome, " +  "! What would you like to do?");
            System.out.println("1️⃣ Show Transaction History");
            System.out.println("2️⃣ Withdraw");
            System.out.println("3️⃣ Deposit");
            System.out.println("4️⃣ Transfer");
            System.out.println("5️⃣ Quit");
            System.out.print("Enter Your Choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("⚠ Invalid input. Enter a number (1-5): ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> showTransactionHistory(theUser, sc);
                case 2 -> withdrawFunds(theUser, sc);
                case 3 -> depositFunds(theUser, sc);
                case 4 -> transferFunds(theUser, sc);
                case 5 -> System.out.println("👋 Logging out...\n");
                default -> System.out.println("⚠ Invalid choice. Please choose between 1-5.");
            }
        } while (choice != 5);
    }

    private static void showTransactionHistory(User theUser, Scanner sc) {
        int accountIndex = getAccountChoice(theUser, sc, "whose transactions you want to see");
        theUser.printActTransHistory(accountIndex);
    }

    private static void transferFunds(User theUser, Scanner sc) {
        int fromAccount = getAccountChoice(theUser, sc, "to transfer from");
        double accountBalance = theUser.getAccountBalance(fromAccount);
        int toAccount = getAccountChoice(theUser, sc, "to transfer to");

        double amount = getAmount(sc, "Enter the amount to transfer", 0, accountBalance);
        theUser.addActTransaction(fromAccount, -amount, "Transfer to account " + theUser.getActUUID(toAccount));
        theUser.addActTransaction(toAccount, amount, "Transfer from account " + theUser.getActUUID(fromAccount));
        System.out.println("✅ Transfer successful!");
    }

    private static void withdrawFunds(User theUser, Scanner sc) {
        int fromAccount = getAccountChoice(theUser, sc, "to withdraw from");
        double accountBalance = theUser.getAccountBalance(fromAccount);

        double amount = getAmount(sc, "Enter the amount to withdraw", 0, accountBalance);
        System.out.print("Enter a memo: ");
        String memo = sc.nextLine();

        theUser.addActTransaction(fromAccount, -amount, memo);
        System.out.println("✅ Withdrawal successful!");
    }

    private static void depositFunds(User theUser, Scanner sc) {
        int toAccount = getAccountChoice(theUser, sc, "to deposit into");

        double amount = getAmount(sc, "Enter the amount to deposit", 0, Double.MAX_VALUE);
        System.out.print("Enter a memo: ");
        String memo = sc.nextLine();

        theUser.addActTransaction(toAccount, amount, memo);
        System.out.println("✅ Deposit successful!");
    }

    private static int getAccountChoice(User theUser, Scanner sc, String action) {
        int accountIndex;
        do {
            System.out.printf("Enter the number (1-%d) of the account %s: ", theUser.numAccounts(), action);
            while (!sc.hasNextInt()) {
                System.out.print("⚠ Invalid input. Enter a valid account number: ");
                sc.next();
            }
            accountIndex = sc.nextInt() - 1;
            sc.nextLine(); // Consume newline

            if (accountIndex < 0 || accountIndex >= theUser.numAccounts()) {
                System.out.println("⚠ Invalid account selection. Please try again.");
            }
        } while (accountIndex < 0 || accountIndex >= theUser.numAccounts());
        return accountIndex;
    }

    private static double getAmount(Scanner sc, String prompt, double min, double max) {
        double amount;
        do {
            System.out.print(prompt + " (max $" + max + "): $");
            while (!sc.hasNextDouble()) {
                System.out.print("⚠ Invalid input. Enter a valid amount: ");
                sc.next();
            }
            amount = sc.nextDouble();
            sc.nextLine(); // Consume newline

            if (amount < min || amount > max) {
                System.out.println("⚠ Amount must be between $" + min + " and $" + max + ".");
            }
        } while (amount < min || amount > max);
        return amount;
    }
}
