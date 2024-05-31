package data.entity;

import model.Expense;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseEntity that = (ExpenseEntity) o;
        return id == that.id && Double.compare(cost, that.cost) == 0 && Objects.equals(name, that.name) && Objects.equals(date, that.date);
    }
}
