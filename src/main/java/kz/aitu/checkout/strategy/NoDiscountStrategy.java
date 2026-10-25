package kz.aitu.checkout.strategy;

import kz.aitu.checkout.model.Cart;

import java.math.BigDecimal;

/**
 * Regular price. Used as the default so the Checkout never has to check for a missing strategy (Null Object).
 */
public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        return BigDecimal.ZERO;
    }

    @Override
    public String displayName() {
        return "Regular price";
    }
}
