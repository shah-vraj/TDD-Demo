package repository;

import data.model.Expense;

import java.time.Month;
import java.util.List;

/**
 * Repository for viewing expense that deals with all operations related to ViewExpenses screen
 */
public interface ViewExpensesRepository {
    List<Expense> getCurrentMonthExpenses();

    List<Expense> getExpensesForMonth(Month month, int year);

    double getTotalExpenseCostForCurrentMonth();

    double getTotalExpenseCostForMonth(Month month, int year);
}
