package data.entity;

import data.model.Expense;

import java.time.LocalDate;

/**
 * Entity class of Expense that is added to the database
 *
 * @param id ID of the expense
 * @param name Name of the expense
 * @param cost Total cost of the expense made
 * @param date Date at which the expense was added
 */
public record ExpenseEntity(int id, String name, double cost, LocalDate date) {

    /**
     * Helper method to get Expense instance from ExpenseEntity
     *
     * @return Expense instance with ExpenseEntity data
     */
    public Expense toExpense() {
        return new Expense(name, cost, date);
    }
}
