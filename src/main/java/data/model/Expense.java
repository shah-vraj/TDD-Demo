package data.model;

import java.time.LocalDate;

/**
 * Data class of Expense for holding data at UI layer
 * @param name Name of the expense
 * @param cost Total cost of the expense made
 * @param date Date at which the expense was added
 */
public record Expense(
        String name,
        double cost,
        LocalDate date
) { }
