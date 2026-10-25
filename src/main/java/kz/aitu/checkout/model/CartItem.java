package kz.aitu.checkout.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * One product line in a shopping cart: a product name, its unit price and how many units were added.
 */
public record CartItem(String productName, BigDecimal unitPrice, int quantity) {

    public CartItem {
        Objects.requireNonNull(productName, "productName must not be null");
        Objects.requireNonNull(unitPrice, "unitPrice must not be null");
        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("unitPrice must not be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }

    public BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
