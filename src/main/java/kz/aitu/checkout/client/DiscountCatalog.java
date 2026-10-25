package kz.aitu.checkout.client;

import kz.aitu.checkout.strategy.BuyThreeGetCheapestFreeStrategy;
import kz.aitu.checkout.strategy.DiscountStrategy;
import kz.aitu.checkout.strategy.FixedAmountCouponStrategy;
import kz.aitu.checkout.strategy.NoDiscountStrategy;
import kz.aitu.checkout.strategy.PercentageDiscountStrategy;
import kz.aitu.checkout.strategy.StudentDiscountStrategy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Client-side registry that maps a menu code to a strategy factory.
 * <p>
 * Choosing a strategy is a map lookup, not an if/switch chain.
 * Adding a promotion means adding one {@code register(...)} line here.
 */
public class DiscountCatalog {

    private final Map<String, Supplier<DiscountStrategy>> strategiesByCode = new LinkedHashMap<>();

    public static DiscountCatalog withDefaultPromotions() {
        return new DiscountCatalog()
                .register("1", NoDiscountStrategy::new)
                .register("2", () -> new PercentageDiscountStrategy(20))
                .register("3", () -> new FixedAmountCouponStrategy("5000"))
                .register("4", BuyThreeGetCheapestFreeStrategy::new)
                .register("5", StudentDiscountStrategy::new);
    }

    public DiscountCatalog register(String code, Supplier<DiscountStrategy> factory) {
        strategiesByCode.put(code, factory);
        return this;
    }

    public Optional<DiscountStrategy> find(String code) {
        return Optional.ofNullable(strategiesByCode.get(code)).map(Supplier::get);
    }

    public Map<String, Supplier<DiscountStrategy>> entries() {
        return Collections.unmodifiableMap(strategiesByCode);
    }
}
