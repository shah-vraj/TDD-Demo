package ui;

import data.model.Expense;
import repository.DatabaseRepository;
import repository.ManageExpenseRepository;
import repository.ViewRecentExpensesRepository;
import repository.impl.ManageExpenseRepositoryImpl;
import repository.impl.ViewRecentExpensesRepositoryImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static repository.ViewRecentExpensesRepository.VIEW_RECENT_EXPENSES_LIMIT;

/**
 * UI layer for managing expense.
 * This class is responsible for handling user interaction for managing expense to the system.
 */
public class ManageExpense {

    private final ManageExpenseRepository manageExpenseRepository;
    private final ViewRecentExpensesRepository viewRecentExpensesRepository;

    private final Scanner scanner = new Scanner(System.in);
    private List<Expense> recentExpenses = new ArrayList<>();
    private int offset = 0;

    public ManageExpense() {
        manageExpenseRepository = new ManageExpenseRepositoryImpl(DatabaseRepository.getInstance());
        viewRecentExpensesRepository = new ViewRecentExpensesRepositoryImpl(DatabaseRepository.getInstance());
    }

    public void showAddExpense() {
        System.out.println("To add an expense, please enter required fields");

        // Name
        System.out.print("Name: ");
        String name = scanner.nextLine();
        if (name == null || name.isBlank()) {
            System.out.println("Failed to add expense due to invalid name provided\n");
            return;
        }

        // Cost
        System.out.print("Cost: ");
        String costString = scanner.nextLine();
        if (isCostInvalid(costString)) {
            System.out.println("Failed to add expense due to invalid cost provided\n");
            return;
        }

        // Add expense
        Expense expenseToAdd = new Expense(name, Double.parseDouble(costString), LocalDate.now());
        boolean isExpenseAdded = manageExpenseRepository.addExpense(expenseToAdd);
        String successMessage = "Expense added successfully\n";
        String failureMessage = "Failed to add expense for some reason\n";
        System.out.println(isExpenseAdded ? successMessage : failureMessage);
    }

    public void showRemoveExpense() {
        recentExpenses = viewRecentExpensesRepository.getRecentExpenses(VIEW_RECENT_EXPENSES_LIMIT, offset);
        printLoadedRecentExpenses();
        checkIfMoreRecentExpensesAreAvailable();

        // ID to remove
        System.out.print("\nEnter the id of the expense to remove (-1 to cancel): ");
        String idStringOfExpenseToRemove = scanner.nextLine();
        if (isIdInvalid(idStringOfExpenseToRemove)) {
            System.out.println("Failed to remove expense due to invalid id provided\n");
            return;
        }

        // Remove expense
        int indexOfExpenseToRemove = Integer.parseInt(idStringOfExpenseToRemove) - 1;
        Expense expenseToRemove = recentExpenses.get(indexOfExpenseToRemove);
        boolean isExpenseRemoved = manageExpenseRepository.removeExpense(expenseToRemove);
        String successMessage = "Expense removed successfully\n";
        String failureMessage = "Failed to remove expense for some reason\n";
        System.out.println(isExpenseRemoved ? successMessage : failureMessage);
    }

    public void showUpdateExpense() {
        recentExpenses = viewRecentExpensesRepository.getRecentExpenses(VIEW_RECENT_EXPENSES_LIMIT, offset);
        printLoadedRecentExpenses();
        checkIfMoreRecentExpensesAreAvailable();

        // ID to update
        System.out.print("\nEnter the id of the expense to update (-1 to cancel): ");
        String idStringOfExpenseToUpdate = scanner.nextLine();
        if (isIdInvalid(idStringOfExpenseToUpdate)) {
            System.out.println("Failed to update expense due to invalid id provided\n");
            return;
        }

        // Name
        System.out.print("Name: ");
        String name = scanner.nextLine();
        if (name == null || name.isBlank()) {
            System.out.println("Failed to update expense due to invalid name provided\n");
            return;
        }

        // Cost
        System.out.print("Cost: ");
        String costString = scanner.nextLine();
        if (isCostInvalid(costString)) {
            System.out.println("Failed to update expense due to invalid cost provided\n");
            return;
        }

        // Update expense
        int indexOfExpenseToUpdate = Integer.parseInt(idStringOfExpenseToUpdate) - 1;
        Expense oldExpense = recentExpenses.get(indexOfExpenseToUpdate);
        Expense newExpense = new Expense(name, Double.parseDouble(costString), oldExpense.date());
        manageExpenseRepository.updateExpense(oldExpense, newExpense);
        System.out.println("Expense updated successfully\n");
    }

    private boolean isCostInvalid(String costString) {
        if (costString == null || costString.isBlank())
            return true;
        try {
            double cost = Double.parseDouble(costString);
            return cost < 0;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean isIdInvalid(String idString) {
        if (idString == null || idString.isBlank())
            return true;
        try {
            double id = Integer.parseInt(idString);
            return id <= 0 || id > recentExpenses.size();
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private void printLoadedRecentExpenses() {
        List<Expense> expensesToLoad = recentExpenses.subList(offset, recentExpenses.size());
        for (int index = 1; index <= expensesToLoad.size(); index++) {
            Expense expense = expensesToLoad.get(index - 1);
            System.out.printf("%3s. %s, %s, %s %n", (index + offset), expense.name(), expense.cost(), expense.date());
        }
    }

    private void checkIfMoreRecentExpensesAreAvailable() {
        offset = recentExpenses.size();
        List<Expense> nextExpenses = viewRecentExpensesRepository.getRecentExpenses(VIEW_RECENT_EXPENSES_LIMIT, offset);
        if (!nextExpenses.isEmpty()) {
            boolean shouldLoadMoreExpenses = askToLoadMoreExpenses();
            if (shouldLoadMoreExpenses) {
                recentExpenses.addAll(nextExpenses);
                printLoadedRecentExpenses();
                checkIfMoreRecentExpensesAreAvailable();
            }
        }
    }

    private boolean askToLoadMoreExpenses() {
        System.out.print("\nDo you wish to load more expenses (Y/N)? ");
        String shouldLoadMoreExpenses = scanner.nextLine().toUpperCase();
        if (!shouldLoadMoreExpenses.equals("Y") && !shouldLoadMoreExpenses.equals("N")) {
            System.out.println("Invalid input please try again!");
            return askToLoadMoreExpenses();
        }
        return shouldLoadMoreExpenses.equals("Y");
    }
}
