package repository.impl;

import data.entity.ExpenseEntity;
import repository.DatabaseRepository;

import java.util.List;

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
    public boolean addExpense(ExpenseEntity expense) {
        return false;
    }

    @Override
    public boolean removeExpense(int id) {
        return false;
    }

    @Override
    public List<ExpenseEntity> getAllExpenses() {
        return List.of();
    }
}
