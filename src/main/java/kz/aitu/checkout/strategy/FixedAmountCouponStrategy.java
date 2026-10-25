package kz.aitu.checkout.strategy;

import kz.aitu.checkout.model.Cart;

import java.math.BigDecimal;

/**
 * A coupon worth a fixed amount of money. The discount is capped at the subtotal,
 * so a 5000 KZT coupon on a 3000 KZT cart makes the order free, never negative.
 */
public class FixedAmountCouponStrategy implements DiscountStrategy {

    private final BigDecimal couponValue;

    public FixedAmountCouponStrategy(String couponValue) {
        this.couponValue = new BigDecimal(couponValue);
        if (this.couponValue.signum() < 0) {
            throw new IllegalArgumentException("couponValue must not be negative");
        }
    }

    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        return couponValue.min(cart.subtotal());
    }

    @Override
    public String displayName() {
        return "Coupon -" + couponValue.toPlainString() + " KZT";
    }
}
