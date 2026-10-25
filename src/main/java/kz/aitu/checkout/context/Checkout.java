package kz.aitu.checkout.context;

import kz.aitu.checkout.model.Cart;
import kz.aitu.checkout.strategy.DiscountStrategy;
import kz.aitu.checkout.strategy.NoDiscountStrategy;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Context of the Strategy pattern.
 * <p>
 * Holds a reference to the current {@link DiscountStrategy} and delegates the discount calculation to it.
 * It contains no if/switch on the kind of discount and never refers to a concrete strategy class
 * (apart from the neutral default), so new promotions can be added without editing this class.
 */
public class Checkout {

    private DiscountStrategy discountStrategy;

    public Checkout() {
        this(new NoDiscountStrategy());
    }

    public Checkout(DiscountStrategy discountStrategy) {
        this.discountStrategy = Objects.requireNonNull(discountStrategy, "discountStrategy must not be null");
    }

    /** Swaps the algorithm at runtime. */
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = Objects.requireNonNull(discountStrategy, "discountStrategy must not be null");
    }

    public DiscountStrategy discountStrategy() {
        return discountStrategy;
    }

    public Receipt checkout(Cart cart) {
        BigDecimal subtotal = cart.subtotal();
        BigDecimal discount = discountStrategy.calculateDiscount(cart);
        BigDecimal total = subtotal.subtract(discount);
        return new Receipt(discountStrategy.displayName(), subtotal, discount, total);
    }
}
