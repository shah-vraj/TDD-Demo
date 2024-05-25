package repository.impl;

import data.model.Expense;
import repository.DatabaseRepository;
import repository.ViewExpensesRepository;

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
        return List.of();
    }

    @Override
    public List<Expense> getExpensesForMonth(Month month, int year) {
        return List.of();
    }

    @Override
    public double getTotalExpenseCostForCurrentMonth() {
        return 0.0;
    }

    @Override
    public double getTotalExpenseCostForMonth(Month month, int year) {
        return 0.0;
    }
}
