package repository.impl;

import data.model.Expense;
import repository.DatabaseRepository;
import repository.ViewRecentExpensesRepository;

import java.util.List;

/**
 * Concrete implementation class for ViewRecentExpensesRepository
 */
public class ViewRecentExpensesRepositoryImpl implements ViewRecentExpensesRepository {

    private final DatabaseRepository databaseRepository;

    public ViewRecentExpensesRepositoryImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
        databaseRepository.openConnection();
    }

    @Override
    public List<Expense> getRecentExpenses(int limit, int offset) {
        return List.of();
    }
}
