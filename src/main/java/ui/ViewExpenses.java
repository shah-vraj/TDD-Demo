package ui;

import model.Expense;
import repository.DatabaseRepository;
import repository.ViewExpensesRepository;
import repository.impl.ViewExpensesRepositoryImpl;

import java.time.Month;
import java.util.List;
import java.util.Scanner;

/**
 * UI layer for viewing expenses.
 * This class is responsible for handling user interaction for viewing expenses.
 */
public class ViewExpenses {

    private final ViewExpensesRepository viewExpensesRepository;

    private final Scanner scanner = new Scanner(System.in);

    public ViewExpenses() {
        viewExpensesRepository = new ViewExpensesRepositoryImpl(DatabaseRepository.getInstance());
    }

    public void showCurrentMonthExpenses() {
        List<Expense> currentMonthExpenses = viewExpensesRepository.getCurrentMonthExpenses();
        if (currentMonthExpenses.isEmpty()) {
            System.out.println("No expense added in current month.\n");
            return;
        }
        printExpenses(currentMonthExpenses);
        System.out.println();
    }

    public void showSpecificMonthExpenses() {
        System.out.println("To view all expenses for specific month, please enter required fields");

        // Month
        System.out.print("Month (1-12): ");
        String monthString = scanner.nextLine();
        if (isMonthInvalid(monthString)) {
            System.out.println("Failed to get expenses due to invalid month provided\n");
            return;
        }

        // Year
        System.out.print("Year: ");
        String yearString = scanner.nextLine();
        if (isYearInvalid(yearString)) {
            System.out.println("Failed to get expenses due to invalid year provided\n");
            return;
        }

        // Fetch and print
        Month month = Month.of(Integer.parseInt(monthString));
        int year = Integer.parseInt(yearString);
        List<Expense> specificMonthExpenses = viewExpensesRepository.getExpensesForMonth(month, year);
        if (specificMonthExpenses.isEmpty()) {
            System.out.printf("No expense added in %s, %d.%n", month.name(), year);
            return;
        }
        System.out.println();
        printExpenses(specificMonthExpenses);
        System.out.println();
    }

    public void showCurrentMonthExpensesCost() {
        double currentMonthExpensesCost = viewExpensesRepository.getTotalExpenseCostForCurrentMonth();
        System.out.printf("Current month expenses cost is %.3f %n", currentMonthExpensesCost);
        System.out.println();
    }

    public void showSpecificMonthExpensesCost() {
        System.out.println("To view all expenses cost for specific month, please enter required fields");

        // Month
        System.out.print("Month (1-12): ");
        String monthString = scanner.nextLine();
        if (isMonthInvalid(monthString)) {
            System.out.println("Failed to get expenses due to invalid month provided\n");
            return;
        }

        // Year
        System.out.print("Year: ");
        String yearString = scanner.nextLine();
        if (isYearInvalid(yearString)) {
            System.out.println("Failed to get expenses due to invalid year provided\n");
            return;
        }

        // Fetch and print
        Month month = Month.of(Integer.parseInt(monthString));
        int year = Integer.parseInt(yearString);
        double specificMonthExpensesCost = viewExpensesRepository.getTotalExpenseCostForMonth(month, year);
        System.out.println();
        System.out.printf("Expenses cost for %s, %d is: %.3f %n", month.name(), year, specificMonthExpensesCost);
        System.out.println();
    }

    private void printExpenses(List<Expense> expenses) {
        for (int index = 1; index <= expenses.size(); index++) {
            Expense expense = expenses.get(index - 1);
            System.out.printf("%3s. %s, %s, %s %n", index, expense.name(), expense.cost(), expense.date());
        }
    }

    private boolean isMonthInvalid(String monthString) {
        if (monthString == null || monthString.isBlank())
            return true;
        try {
            double month = Integer.parseInt(monthString);
            return month < 1 || month > 12;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean isYearInvalid(String yearString) {
        if (yearString == null || yearString.isBlank())
            return true;
        try {
            double year = Integer.parseInt(yearString);
            return year < 0;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
