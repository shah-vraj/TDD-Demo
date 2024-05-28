package repository.impl;

import data.entity.ExpenseEntity;
import data.model.Expense;
import repository.DatabaseRepository;
import repository.ViewExpensesRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * Concrete implementation class for ViewExpensesRepository
 */
public class ViewExpensesRepositoryImpl implements ViewExpensesRepository {

    private final DatabaseRepository databaseRepository;

    public ViewExpensesRepositoryImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
        databaseRepository.openConnection();
    }

    @Override
    public List<Expense> getCurrentMonthExpenses() {
        return getExpensesForMonth(LocalDate.now().getMonth(), LocalDate.now().getYear());
    }

    @Override
    public List<Expense> getExpensesForMonth(Month month, int year) {
        if (month == null || year < 0)
            return List.of();
        return databaseRepository.getAllExpenses()
                .stream()
                .filter(expenseEntity -> areMonthAndYearSimilarToDate(expenseEntity.date(), month, year))
                .map(ExpenseEntity::toExpense)
                .toList();
    }

    @Override
    public double getTotalExpenseCostForCurrentMonth() {
        return getTotalExpenseCostForMonth(LocalDate.now().getMonth(), LocalDate.now().getYear());
    }

    @Override
    public double getTotalExpenseCostForMonth(Month month, int year) {
        if (month == null || year < 0)
            return 0.0;
        return getExpensesForMonth(month, year)
                .stream()
                .mapToDouble(Expense::cost)
                .sum();
    }

    /**
     * Helper method to check if provided LocalDate matches month and year or not
     *
     * @param localDate LocalDate instance to check for
     * @param month     Month that is to be compared
     * @param year      Year that is to be compared
     * @return True if provided LocalDate has similar month and year as provided, Otherwise false
     */
    private boolean areMonthAndYearSimilarToDate(LocalDate localDate, Month month, int year) {
        return localDate.getMonth() == month && localDate.getYear() == year;
    }
}
