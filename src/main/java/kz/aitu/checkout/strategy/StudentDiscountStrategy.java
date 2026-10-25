package kz.aitu.checkout.strategy;

import kz.aitu.checkout.model.Cart;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 15% off for students, limited to 10 000 KZT per order.
 * <p>
 * Added after the other strategies to show the Open/Closed Principle:
 * creating this class and registering it in the client's catalog was the whole change.
 * {@code Checkout} and the other strategies were not edited.
 */
public class StudentDiscountStrategy implements DiscountStrategy {

    private static final BigDecimal RATE = new BigDecimal("0.15");
    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("10000");

    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        BigDecimal fifteenPercent = cart.subtotal().multiply(RATE).setScale(2, RoundingMode.HALF_UP);
        return fifteenPercent.min(MAX_DISCOUNT);
    }

    @Override
    public String displayName() {
        return "Student 15% (max 10000 KZT)";
    }
}
