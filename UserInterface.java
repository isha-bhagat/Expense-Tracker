import java.util.Scanner;

public class UserInterface {
    private ExpenseManager expenseManager;
    private Scanner scanner;

    public UserInterface() {
        expenseManager = new ExpenseManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the Expense Tracker!");
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                register();
            } else if (choice == 2) {
                login();
            } else {
                break;
            }
        }
    }

    private void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        expenseManager.registerUser(username, password);
    }

    private void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        if (expenseManager.loginUser(username, password)) {
            userMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total Expenses by Category");
            System.out.println("4. Logout");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                addExpense();
            } else if (choice == 2) {
                viewExpenses();
            } else if (choice == 3) {
                viewExpensesByCategory();
            } else {
                break;
            }
        }
    }

    private void addExpense() {
        System.out.println("Enter date (yyyy-mm-dd):");
        String date = scanner.nextLine();
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        System.out.println("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        expenseManager.addExpense(new Expense(date, category, amount));
    }

    private void viewExpenses() {
        for (Expense expense : expenseManager.getExpenses()) {
            System.out.println(expense);
        }
    }

    private void viewExpensesByCategory() {
        System.out.println("Enter category:");
        String category = scanner.nextLine();
        double total = expenseManager.getTotalExpensesByCategory(category);
        System.out.println("Total expenses for " + category + ": " + total);
    }
}
