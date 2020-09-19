package de.uniba.dsg.jaxrs.db;

import de.uniba.dsg.jaxrs.models.logic.*;

import java.util.*;
import java.util.stream.Collectors;

public class DB {

    private final List<Bottle> bottles;
    private final List<Crate> crates;
    private final List<Order> orders;

    public DB() {
        this.bottles = initBottles();
        this.crates = initCases();
        this.orders = initOrder();
    }

    private List<Bottle> initBottles() {
        return new ArrayList<>(
                Arrays.asList(
                        new Bottle(1, "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34),
                        new Bottle(2, "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17),
                        new Bottle(3, "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11),
                        new Bottle(4, "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5),
                        new Bottle(5, "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3),
                        new Bottle(6, "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66),
                        new Bottle(
                                7,
                                "Premium Tafelwasser",
                                0.7,
                                false,
                                0.0,
                                4.29,
                                "Franken Brunnen",
                                12),
                        new Bottle(8, "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57),
                        new Bottle(9, "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42),
                        new Bottle(10, "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12),
                        new Bottle(11, "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34),
                        new Bottle(12, "Cola", 1.5, false, 0.0, 1.79, "CCC", 69),
                        new Bottle(13, "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12),
                        new Bottle(14, "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25),
                        new Bottle(15, "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55),
                        new Bottle(16, "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8)));
    }

    private List<Crate> initCases() {
        return new ArrayList<>(
                Arrays.asList(
                        new Crate(1, this.bottles.get(0), 20, 14.99, 3),
                        new Crate(2, this.bottles.get(1), 20, 15.99, 5),
                        new Crate(3, this.bottles.get(2), 6, 30.00, 7),
                        new Crate(4, this.bottles.get(7), 12, 1.99, 11),
                        new Crate(5, this.bottles.get(8), 20, 11.99, 13),
                        new Crate(6, this.bottles.get(11), 6, 10.99, 4),
                        new Crate(7, this.bottles.get(12), 6, 11.99, 5),
                        new Crate(8, this.bottles.get(13), 20, 35.00, 7),
                        new Crate(9, this.bottles.get(14), 12, 20.00, 9)));
    }

    private List<Order> initOrder() {
        return new ArrayList<>(
                Arrays.asList(
                        new Order(
                                1,
                                new ArrayList<>(
                                        Arrays.asList(
                                                new OrderItem(10, this.bottles.get(3), 2),
                                                new OrderItem(20, this.crates.get(3), 1),
                                                new OrderItem(30, this.bottles.get(15), 1))),
                                32.56,
                                OrderStatus.SUBMITTED),
                        new Order(
                                2,
                                new ArrayList<>(
                                        Arrays.asList(
                                                new OrderItem(10, this.bottles.get(13), 2),
                                                new OrderItem(20, this.bottles.get(14), 2),
                                                new OrderItem(30, this.crates.get(0), 1))),
                                22.95,
                                OrderStatus.PROCESSED),
                        new Order(
                                3,
                                new ArrayList<>(
                                        Collections.singletonList(
                                                new OrderItem(10, this.bottles.get(2), 1))),
                                5.79,
                                OrderStatus.SUBMITTED)));
    }

    public List<Bottle> getAllBottles() {
        return this.bottles;
    }

    public List<Bottle> getAllBottlesFilter(double minPrice, double maxPrice, String mode) {
        List<Bottle> tmp = new ArrayList<>();

        for (Bottle bottle : this.bottles) {
            if ((minPrice == -1 || (minPrice >= 0 && bottle.getPrice() >= minPrice))
                    && (maxPrice == -1 || (maxPrice >= 0 && bottle.getPrice() <= maxPrice))) {
                tmp.add(bottle);
            }
        }

        tmp.sort(Comparator.comparing(Bottle::getPrice));

        if (mode.equals("desc")) {
            Collections.reverse(tmp);
        }
        return tmp;
    }

    public List<Crate> getAllCratesFilter(double minPrice, double maxPrice, String mode) {
        List<Crate> tmp = new ArrayList<>();

        for (Crate crate : this.crates) {
            if ((minPrice == -1 || (minPrice >= 0 && crate.getPrice() >= minPrice))
                    && (maxPrice == -1 || (maxPrice >= 0 && crate.getPrice() <= maxPrice))) {
                tmp.add(crate);
            }
        }

        tmp.sort(Comparator.comparing(Crate::getPrice));

        if (mode.equals("desc")) {
            Collections.reverse(tmp);
        }
        return tmp;
    }

    public List<Crate> getAllCrates() {
        return this.crates;
    }

    public List<Order> getAllOrders() {
        return this.orders;
    }

    public Bottle getBottle(final int id) {
        return this.bottles.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public boolean deleteBottle(int id) {
        final Bottle bottle = this.getBottle(id);
        return this.bottles.remove(bottle);
    }

    public Crate getCrate(final int id) {
        return this.crates.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public boolean deleteCrate(int id) {
        final Crate crate = this.getCrate(id);
        return this.crates.remove(crate);
    }

    public Order getOrder(final int id) {
        return this.orders.stream().filter(c -> c.getOrderId() == id).findFirst().orElse(null);
    }

    public boolean deleteOrder(int id) {

        final Order order = this.getOrder(id);

        System.out.println(order);

        if (order == null) {
            return false;
        }

        for (OrderItem oi : order.getPositions()) {
            if (oi.getBeverage() instanceof Bottle) {
                if (this.bottles.get(((Bottle) oi.getBeverage()).getId() - 1) != null)
                    this.bottles
                            .get(((Bottle) oi.getBeverage()).getId() - 1)
                            .setInStock(
                                    this.bottles
                                                    .get(((Bottle) oi.getBeverage()).getId() - 1)
                                                    .getInStock()
                                            + oi.getQuantity());
            } else if (oi.getBeverage() instanceof Crate) {
                if (this.crates.get(((Crate) oi.getBeverage()).getId() - 1) != null)
                    this.crates
                            .get(((Crate) oi.getBeverage()).getId() - 1)
                            .setInStock(
                                    this.crates
                                                    .get(((Crate) oi.getBeverage()).getId() - 1)
                                                    .getInStock()
                                            + oi.getQuantity());
            }
        }

        return this.orders.remove(order);
    }

    public Object add(final Object o) {
        if (o instanceof Bottle) {
            ((Bottle) o)
                    .setId(
                            this.bottles.stream()
                                            .map(Bottle::getId)
                                            .max(Comparator.naturalOrder())
                                            .orElse(0)
                                    + 1);
            bottles.add((Bottle) o);
        } else if (o instanceof Crate) {
            ((Crate) o)
                    .setId(
                            this.crates.stream()
                                            .map(Crate::getId)
                                            .max(Comparator.naturalOrder())
                                            .orElse(0)
                                    + 1);

            if (this.bottles.get(((Crate) o).getBottle().getId() - 1) == null) {
                Bottle newBottle = (Bottle) add(((Crate) o).getBottle());
                ((Crate) o).setBottle(newBottle);
            } else {
                ((Crate) o).setBottle(this.bottles.get(((Crate) o).getBottle().getId() - 1));
            }

            crates.add((Crate) o);
        } else if (o instanceof Order) {
            for (OrderItem oi : ((Order) o).getPositions()) {
                if (oi.getBeverage() == null) {
                    return null;
                }

                if (oi.getBeverage() instanceof Bottle) {
                    if (bottles.get(oi.getNumber()) == null
                            || bottles.get(oi.getNumber()).getInStock() < oi.getQuantity()) {
                        return null;
                    }

                    bottles.get(oi.getNumber())
                            .setInStock(
                                    bottles.get(oi.getNumber()).getInStock() - oi.getQuantity());
                } else if (oi.getBeverage() instanceof Crate) {
                    if (crates.get(oi.getNumber()) == null
                            || crates.get(oi.getNumber()).getInStock() < oi.getQuantity()) {
                        return null;
                    }

                    crates.get(oi.getNumber())
                            .setInStock(
                                    bottles.get(oi.getNumber()).getInStock() - oi.getQuantity());
                } else {
                    return null;
                }
            }
            ((Order) o)
                    .setOrderId(
                            this.orders.stream()
                                            .map(Order::getOrderId)
                                            .max(Comparator.naturalOrder())
                                            .orElse(0)
                                    + 1);
            orders.add((Order) o);
        } else {
            return null;
        }
        return o;
    }

    public List<Bottle> searchBottles(final String search, final SearchMode mode) {
        switch (mode) {
            case NAME:
                return this.bottles.stream()
                        .filter(c -> c.getName().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());

            case SUPPLIER:
                return this.bottles.stream()
                        .filter(c -> c.getSupplier().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());

            case INSTOCK:
                return this.bottles.stream()
                        .filter(c -> c.getInStock() > 0)
                        .collect(Collectors.toList());
        }
        return null;
    }

    public List<Crate> searchCrates(final String search, final SearchMode mode) {
        switch (mode) {
            case NAME:
                return this.crates.stream()
                        .filter(
                                c ->
                                        c.getBottle()
                                                .getName()
                                                .toLowerCase()
                                                .contains(search.toLowerCase()))
                        .collect(Collectors.toList());

            case SUPPLIER:
                return this.crates.stream()
                        .filter(
                                c ->
                                        c.getBottle()
                                                .getSupplier()
                                                .toLowerCase()
                                                .contains(search.toLowerCase()))
                        .collect(Collectors.toList());

            case INSTOCK:
                return this.crates.stream()
                        .filter(c -> c.getInStock() > 0)
                        .collect(Collectors.toList());
        }
        return null;
    }

    public void updateOrder(final String mode, final Order order) {
        switch (mode) {
            case "add":
                for (OrderItem oi : order.getPositions()) {
                    if (oi.getBeverage() instanceof Bottle) {

                        if (this.bottles.get(((Bottle) oi.getBeverage()).getId() - 1) != null) {
                            this.bottles
                                    .get(((Bottle) oi.getBeverage()).getId() - 1)
                                    .setInStock(
                                            this.bottles
                                                            .get(
                                                                    ((Bottle) oi.getBeverage())
                                                                                    .getId()
                                                                            - 1)
                                                            .getInStock()
                                                    + oi.getQuantity());

                        } else if (oi.getBeverage() instanceof Crate) {

                            if (this.crates.get(((Crate) oi.getBeverage()).getId() - 1) != null)
                                this.crates
                                        .get(((Crate) oi.getBeverage()).getId() - 1)
                                        .setInStock(
                                                this.crates
                                                                .get(
                                                                        ((Crate) oi.getBeverage())
                                                                                        .getId()
                                                                                - 1)
                                                                .getInStock()
                                                        + oi.getQuantity());
                        }
                    }
                }
                break;

            case "rmv":
                for (OrderItem oi : order.getPositions()) {

                    if (oi.getBeverage() instanceof Bottle) {
                        if (this.bottles.get(((Bottle) oi.getBeverage()).getId() - 1) != null)
                            this.bottles
                                    .get(((Bottle) oi.getBeverage()).getId() - 1)
                                    .setInStock(
                                            this.bottles
                                                            .get(
                                                                    ((Bottle) oi.getBeverage())
                                                                                    .getId()
                                                                            - 1)
                                                            .getInStock()
                                                    - oi.getQuantity());
                    } else if (oi.getBeverage() instanceof Crate) {
                        if (this.crates.get(((Crate) oi.getBeverage()).getId() - 1) != null)
                            this.crates
                                    .get(((Crate) oi.getBeverage()).getId() - 1)
                                    .setInStock(
                                            this.crates
                                                            .get(
                                                                    ((Crate) oi.getBeverage())
                                                                                    .getId()
                                                                            - 1)
                                                            .getInStock()
                                                    - oi.getQuantity());
                    }
                }
                break;
        }
    }
}
