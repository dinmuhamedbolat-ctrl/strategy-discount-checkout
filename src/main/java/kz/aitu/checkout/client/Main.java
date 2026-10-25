package kz.aitu.checkout.client;

import kz.aitu.checkout.context.Checkout;
import kz.aitu.checkout.model.Cart;
import kz.aitu.checkout.model.CartItem;
import kz.aitu.checkout.strategy.DiscountStrategy;

import java.util.Scanner;

/**
 * Client of the Strategy pattern.
 * <p>
 * Runs a scripted demo that switches the discount strategy of one {@link Checkout} at runtime,
 * then opens an interactive menu so the strategy can be switched live during the defense.
 * Pass {@code --demo-only} to skip the interactive part.
 */
public class Main {

    private static final String QUIT = "q";

    public static void main(String[] args) {
        Cart cart = sampleCart();
        Checkout checkout = new Checkout();
        DiscountCatalog catalog = DiscountCatalog.withDefaultPromotions();

        printCart(cart);
        runScriptedDemo(cart, checkout, catalog);

        boolean demoOnly = args.length > 0 && args[0].equals("--demo-only");
        if (!demoOnly) {
            runInteractiveMenu(cart, checkout, catalog);
        }
    }

    private static Cart sampleCart() {
        return new Cart()
                .add("Mechanical keyboard", "32000", 1)
                .add("Wireless mouse", "12500", 1)
                .add("USB-C cable", "2500", 2);
    }

    private static void runScriptedDemo(Cart cart, Checkout checkout, DiscountCatalog catalog) {
        System.out.println("=== Scripted demo: same Checkout object, strategy swapped at runtime ===");
        catalog.entries().values().forEach(factory -> {
            checkout.setDiscountStrategy(factory.get());
            System.out.println(checkout.checkout(cart).format());
            System.out.println();
        });
    }

    private static void runInteractiveMenu(Cart cart, Checkout checkout, DiscountCatalog catalog) {
        Scanner input = new Scanner(System.in);
        while (true) {
            printMenu(catalog);
            if (!input.hasNextLine()) {
                return;
            }
            String code = input.nextLine().trim();
            if (code.equalsIgnoreCase(QUIT)) {
                return;
            }
            catalog.find(code).ifPresentOrElse(
                    strategy -> applyAndPrint(cart, checkout, strategy),
                    () -> System.out.println("Unknown option: " + code));
        }
    }

    private static void applyAndPrint(Cart cart, Checkout checkout, DiscountStrategy strategy) {
        checkout.setDiscountStrategy(strategy);
        System.out.println(checkout.checkout(cart).format());
    }

    private static void printMenu(DiscountCatalog catalog) {
        System.out.println();
        System.out.println("Choose a discount strategy (" + QUIT + " to quit):");
        catalog.entries().forEach((code, factory) ->
                System.out.println("  " + code + ") " + factory.get().displayName()));
        System.out.print("> ");
    }

    private static void printCart(Cart cart) {
        System.out.println("Cart:");
        for (CartItem item : cart.items()) {
            System.out.printf("  %-20s %2d x %9.2f KZT%n", item.productName(), item.quantity(), item.unitPrice());
        }
        System.out.println();
    }
}
