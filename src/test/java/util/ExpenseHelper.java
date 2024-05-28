package util;

import data.entity.ExpenseEntity;
import data.model.Expense;

import java.util.Objects;

public class ExpenseHelper {

    private ExpenseHelper() { }

    /**
     * Helper method to get ExpenseEntity instance with the requested id and expense data.
     * @param expense Expense to take data from
     * @param id ID of the expense to provide
     * @return ExpenseEntity class with Expense data and provided ID
     */
    public static ExpenseEntity getExpenseEntityFromExpense(Expense expense, int id) {
        return new ExpenseEntity(id, expense.name(), expense.cost(), expense.date());
    }

    /**
     * Compares the expense entity with the expense
     * @param expenseEntity ExpenseEntity class to compare with
     * @param expense Expense class to compare with
     * @return True if both expenses have same value, otherwise False
     */
    public static boolean areTheSame(ExpenseEntity expenseEntity, Expense expense) {
        return Objects.equals(expenseEntity.name(), expense.name()) &&
                Double.compare(expenseEntity.cost(), expense.cost()) == 0 &&
                Objects.equals(expenseEntity.date(), expense.date());
    }
}
