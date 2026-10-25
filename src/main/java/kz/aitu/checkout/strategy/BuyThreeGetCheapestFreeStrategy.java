package kz.aitu.checkout.strategy;

import kz.aitu.checkout.model.Cart;
import kz.aitu.checkout.model.CartItem;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * "3 for 2" promotion: for every three units in the cart, the cheapest of them is free.
 */
public class BuyThreeGetCheapestFreeStrategy implements DiscountStrategy {

    private static final int GROUP_SIZE = 3;

    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        int freeUnits = cart.totalUnits() / GROUP_SIZE;
        return unitPricesCheapestFirst(cart).stream()
                .limit(freeUnits)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private List<BigDecimal> unitPricesCheapestFirst(Cart cart) {
        return cart.items().stream()
                .flatMap(BuyThreeGetCheapestFreeStrategy::oneEntryPerUnit)
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    private static Stream<BigDecimal> oneEntryPerUnit(CartItem item) {
        return Stream.generate(item::unitPrice).limit(item.quantity());
    }

    @Override
    public String displayName() {
        return "3 for 2 (cheapest item free)";
    }
}
