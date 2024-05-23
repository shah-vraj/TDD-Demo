package data.entity;

import java.time.LocalDate;

/**
 * Entity class of Expense that is added to the database
 * @property id - ID of the expense
 * @property name - Name of the expense
 * @property cost - Total cost of the expense made
 * @property date - Date at which the expense was added
 */
public class ExpenseEntity {
    private int id;
    private String name;
    private double cost;
    private LocalDate date;

    public ExpenseEntity(int id, String name, double cost, LocalDate date) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
