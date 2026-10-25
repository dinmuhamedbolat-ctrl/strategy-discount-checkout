package kz.aitu.checkout.strategy;

import kz.aitu.checkout.model.Cart;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Takes a fixed percentage off the whole cart, e.g. a 20% seasonal sale.
 */
public class PercentageDiscountStrategy implements DiscountStrategy {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final BigDecimal percent;

    public PercentageDiscountStrategy(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        this.percent = BigDecimal.valueOf(percent);
    }

    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        return cart.subtotal()
                .multiply(percent)
                .divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    @Override
    public String displayName() {
        return "Seasonal sale " + percent + "%";
    }
}
