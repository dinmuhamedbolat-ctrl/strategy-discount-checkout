package kz.aitu.checkout.legacy;

import kz.aitu.checkout.model.Cart;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BEFORE refactoring — kept only for comparison in the report. Not used by the application.
 * <p>
 * Every discount lives in one method behind a switch on a string type.
 * Adding a promotion means editing this class and re-testing every branch.
 */
@Deprecated
public class LegacyCheckout {

    public BigDecimal total(Cart cart, String discountType) {
        BigDecimal subtotal = cart.subtotal();
        BigDecimal discount;
        switch (discountType) {
            case "NONE":
                discount = BigDecimal.ZERO;
                break;
            case "PERCENT":
                discount = subtotal.multiply(BigDecimal.valueOf(20)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                break;
            case "COUPON":
                discount = new BigDecimal("5000").min(subtotal);
                break;
            case "THREE_FOR_TWO":
                // cheapest-item logic would be inlined here, making the method even longer
                discount = BigDecimal.ZERO;
                break;
            default:
                throw new IllegalArgumentException("Unknown discount type: " + discountType);
        }
        return subtotal.subtract(discount);
    }
}
