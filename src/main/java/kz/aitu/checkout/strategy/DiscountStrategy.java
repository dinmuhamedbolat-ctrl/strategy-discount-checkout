package kz.aitu.checkout.strategy;

import kz.aitu.checkout.model.Cart;

import java.math.BigDecimal;

/**
 * Strategy interface: the common contract for every discount algorithm.
 * <p>
 * An implementation receives the cart and returns how much money to take off.
 * The returned amount must be between zero and the cart subtotal.
 */
public interface DiscountStrategy {

    BigDecimal calculateDiscount(Cart cart);

    /** Human-readable name printed on the receipt. */
    String displayName();
}
