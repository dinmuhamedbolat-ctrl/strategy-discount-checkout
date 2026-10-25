# Assignment #5 — Strategy Pattern: Discount Checkout

ShP-2216 Software Design Patterns · Astana IT University · Option C (free topic: pricing / discount strategies)

An online-store `Checkout` calculates the order total using an interchangeable `DiscountStrategy`.
The strategy can be swapped at runtime on the same `Checkout` object, and there is no `if`/`switch`
on the discount type inside the Context.

## Structure

```
src/main/java/kz/aitu/checkout/
├── strategy/   DiscountStrategy (interface) + 5 concrete strategies
│               NoDiscountStrategy, PercentageDiscountStrategy, FixedAmountCouponStrategy,
│               BuyThreeGetCheapestFreeStrategy, StudentDiscountStrategy
├── context/    Checkout (Context), Receipt
├── model/      Cart, CartItem
├── client/     Main (Client), DiscountCatalog (menu code → strategy factory)
└── legacy/     LegacyCheckout — the "before" switch-based version, for comparison only
```

## Run

**IntelliJ IDEA:** open the folder (it is a Maven project), set the SDK to JDK 17+, run `kz.aitu.checkout.client.Main`.

**Command line (JDK 17+):**

```bash
javac -d out $(find src -name "*.java")
java -cp out kz.aitu.checkout.client.Main
```

The program first runs a scripted demo that applies every strategy to the same cart, then shows a menu
where you pick a strategy by number to switch it live. Use `--demo-only` to skip the menu.

## Adding a new strategy

1. Create a class that implements `DiscountStrategy`.
2. Register it in `DiscountCatalog.withDefaultPromotions()` with one `register(...)` line.

`Checkout` and the existing strategies do not change. `StudentDiscountStrategy` was added this way
(see the commit history).
