package kz.aitu.checkout.context;

import java.math.BigDecimal;

/**
 * The result of a checkout: what the customer owed, what was taken off, and by which promotion.
 */
public record Receipt(String appliedDiscount, BigDecimal subtotal, BigDecimal discount, BigDecimal total) {

    public String format() {
        return String.format(
                "  Discount : %s%n  Subtotal : %10.2f KZT%n  Discount : %10.2f KZT%n  TOTAL    : %10.2f KZT",
                appliedDiscount, subtotal, discount.negate(), total);
    }
}
