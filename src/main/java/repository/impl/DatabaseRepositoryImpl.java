package repository.impl;

import data.model.Expense;
import repository.DatabaseRepository;

/**
 * Concrete implementation class for DatabaseRepository
 */
public class DatabaseRepositoryImpl implements DatabaseRepository {

    private DatabaseRepositoryImpl() { }

    public static DatabaseRepository getInstance() {
        return new DatabaseRepositoryImpl();
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
    public boolean addExpense(Expense expense) {
        return false;
    }

    @Override
    public boolean removeExpense(Expense expense) {
        return false;
    }
}
