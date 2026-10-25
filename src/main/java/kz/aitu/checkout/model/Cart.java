package kz.aitu.checkout.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A shopping cart. It only stores items and knows its subtotal; it knows nothing about discounts.
 */
public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public Cart add(String productName, String unitPrice, int quantity) {
        items.add(new CartItem(productName, new BigDecimal(unitPrice), quantity));
        return this;
    }

    public List<CartItem> items() {
        return Collections.unmodifiableList(items);
    }

    public int totalUnits() {
        return items.stream().mapToInt(CartItem::quantity).sum();
    }

    public BigDecimal subtotal() {
        return items.stream()
                .map(CartItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
