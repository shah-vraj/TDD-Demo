package ui;

import model.Expense;
import repository.DatabaseRepository;
import repository.ViewRecentExpensesRepository;
import repository.impl.ViewRecentExpensesRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static repository.ViewRecentExpensesRepository.VIEW_RECENT_EXPENSES_LIMIT;

/**
 * UI layer for viewing recent expenses.
 * This class is responsible for handling user interaction for viewing recent expenses.
 */
public class ViewRecentExpenses {

    private final ViewRecentExpensesRepository viewRecentExpensesRepository;

    private final Scanner scanner = new Scanner(System.in);
    private List<Expense> recentExpenses = new ArrayList<>();
    private int offset = 0;

    public ViewRecentExpenses() {
        viewRecentExpensesRepository = new ViewRecentExpensesRepositoryImpl(DatabaseRepository.getInstance());
    }

    public void showRecentExpenses() {
        recentExpenses = viewRecentExpensesRepository.getRecentExpenses(VIEW_RECENT_EXPENSES_LIMIT, offset);
        printLoadedRecentExpenses();
        checkIfMoreRecentExpensesAreAvailable();
        System.out.println();
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
