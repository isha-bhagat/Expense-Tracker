import java.io.*;
import java.util.*;

public class ExpenseManager {
    private List<User> users;
    private List<Expense> expenses;
    private User currentUser;

    public ExpenseManager() {
        users = new ArrayList<>();
        expenses = new ArrayList<>();
        loadUsers();
        loadExpenses();
    }

    public void registerUser(String username, String password) {
        users.add(new User(username, password));
        saveUsers();
    }

    public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                loadExpenses();
                return true;
            }
        }
        return false;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public double getTotalExpensesByCategory(String category) {
        return expenses.stream()
                .filter(expense -> expense.getCategory().equals(category))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exception or create new list if file doesn't exist
            users = new ArrayList<>();
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExpenses() {
        if (currentUser != null) {
            String filename = currentUser.getUsername() + "_expenses.dat";
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                expenses = (List<Expense>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                // Handle exception or create new list if file doesn't exist
                expenses = new ArrayList<>();
            }
        }
    }

    private void saveExpenses() {
        if (currentUser != null) {
            String filename = currentUser.getUsername() + "_expenses.dat";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
                oos.writeObject(expenses);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
