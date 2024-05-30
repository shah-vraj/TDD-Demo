package data.entity;

import model.Expense;

import java.time.LocalDate;

/**
 * Entity class of Expense that is added to the database
 */
public final class ExpenseEntity {
    private final int id;
    private String name;
    private double cost;
    private final LocalDate date;

    /**
     * @param id   ID of the expense
     * @param name Name of the expense
     * @param cost Total cost of the expense made
     * @param date Date at which the expense was added
     */
    public ExpenseEntity(int id, String name, double cost, LocalDate date) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.date = date;
    }

    /**
     * Helper method to get Expense instance from ExpenseEntity
     *
     * @return Expense instance with ExpenseEntity data
     */
    public Expense toExpense() {
        return new Expense(name, cost, date);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }
}
