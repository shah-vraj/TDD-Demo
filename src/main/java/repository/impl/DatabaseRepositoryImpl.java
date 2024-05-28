package repository.impl;

import data.entity.ExpenseEntity;
import repository.DatabaseRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation class for DatabaseRepository
 */
public class DatabaseRepositoryImpl implements DatabaseRepository {

    private static DatabaseRepositoryImpl instance;
    private final List<ExpenseEntity> allExpenses = new ArrayList<>();

    private DatabaseRepositoryImpl() { }

    /**
     * Singleton object approach to have single object throughout application
     *
     * @return DatabaseRepository instance
     */
    public static DatabaseRepository getInstance() {
        if (instance == null)
            instance = new DatabaseRepositoryImpl();
        return instance;
    }

    @Override
    public boolean openConnection() {
        // Assume open database connection successful
        return true;
    }

    @Override
    public boolean closeConnection() {
        // Assume close database connection successful
        return true;
    }

    @Override
    public boolean addExpense(ExpenseEntity expense) {
        return allExpenses.add(expense);
    }

    @Override
    public boolean removeExpense(int id) {
        return allExpenses.removeIf(expenseEntity -> expenseEntity.id() == id);
    }

    @Override
    public List<ExpenseEntity> getAllExpenses() {
        return allExpenses;
    }
}
