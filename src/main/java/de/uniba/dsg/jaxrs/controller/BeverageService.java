package de.uniba.dsg.jaxrs.controller;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.models.logic.Bottle;
import de.uniba.dsg.jaxrs.models.logic.Crate;
import de.uniba.dsg.jaxrs.models.logic.Order;
import de.uniba.dsg.jaxrs.models.logic.SearchMode;

import java.util.List;
import java.util.Optional;

public class BeverageService {

    public static final BeverageService instance = new BeverageService();

    private final DB beverage;

    private BeverageService() {
        this.beverage = new DB();
    }

    // GET
    public Bottle getBottle(final int id) {
        return this.beverage.getBottle(id);
    }

    public Crate getCrate(final int id) {
        return this.beverage.getCrate(id);
    }

    public Order getOrder(final int id) {
        return this.beverage.getOrder(id);
    }

    public List<Bottle> getBottles() {
        return this.beverage.getAllBottles();
    }

    public List<Crate> getCrates() {
        return this.beverage.getAllCrates();
    }

    public List<Order> getOrders() {
        return this.beverage.getAllOrders();
    }

    public List<Bottle> getBottlesFilter(
            final double minPrice, final double maxPrice, final String mode) {
        return this.beverage.getAllBottlesFilter(minPrice, maxPrice, mode);
    }

    public List<Crate> getCratesFilter(
            final double minPrice, final double maxPrice, final String mode) {
        return this.beverage.getAllCratesFilter(minPrice, maxPrice, mode);
    }

    public List<Bottle> searchBottles(final String search, final SearchMode mode) {
        return this.beverage.searchBottles(search, mode);
    }

    public List<Crate> searchCrates(final String search, final SearchMode mode) {
        return this.beverage.searchCrates(search, mode);
    }

    // POST
    public Bottle addBottle(final Bottle newBottle) {
        if (newBottle == null) {
            return null;
        }
        this.beverage.add(newBottle);

        return newBottle;
    }

    public Crate addCrate(final Crate newCrate) {
        if (newCrate == null) {
            return null;
        }
        this.beverage.add(newCrate);

        return newCrate;
    }

    public Order addOrder(final Order newOrder) {
        if (newOrder == null) {
            return null;
        }
        this.beverage.add(newOrder);

        return newOrder;
    }

    // PUT
    public Bottle updateBottle(final int id, final Bottle updatedBottle) {
        final Bottle bottle = this.getBottle(id);

        if (bottle == null || updatedBottle == null) {
            return null;
        }

        Optional.ofNullable(updatedBottle.getName()).ifPresent(bottle::setName);
        Optional.of(updatedBottle.getVolume()).ifPresent(bottle::setVolume);
        Optional.of(updatedBottle.isAlcoholic()).ifPresent(bottle::setAlcoholic);
        Optional.of(updatedBottle.getVolumePercent()).ifPresent(bottle::setVolumePercent);
        Optional.of(updatedBottle.getPrice()).ifPresent(bottle::setPrice);
        Optional.ofNullable(updatedBottle.getSupplier()).ifPresent(bottle::setSupplier);
        Optional.of(updatedBottle.getInStock()).ifPresent(bottle::setInStock);

        return bottle;
    }

    public Crate updateCrate(final int id, final Crate updatedCrate) {
        final Crate crate = this.getCrate(id);

        if (crate == null || updatedCrate == null) {
            return null;
        }

        Optional.ofNullable(updatedCrate.getBottle()).ifPresent(crate::setBottle);
        Optional.of(updatedCrate.getNoOfBottles()).ifPresent(crate::setNoOfBottles);
        Optional.of(updatedCrate.getPrice()).ifPresent(crate::setPrice);
        Optional.of(updatedCrate.getInStock()).ifPresent(crate::setInStock);

        return crate;
    }

    public Order updateOrder(final int id, final Order updatedOrder) {
        final Order order = this.getOrder(id);

        if (order == null || updatedOrder == null) {
            return null;
        }

        this.beverage.updateOrder("add", order);

        Optional.ofNullable(updatedOrder.getPositions()).ifPresent(order::setPositions);
        Optional.of(updatedOrder.getPrice()).ifPresent(order::setPrice);
        Optional.ofNullable(updatedOrder.getStatus()).ifPresent(order::setStatus);

        this.beverage.updateOrder("rmv", order);

        return order;
    }

    // DELETE
    public boolean deleteBottle(final int id) {
        return this.beverage.deleteBottle(id);
    }

    public boolean deleteCrate(final int id) {
        return this.beverage.deleteCrate(id);
    }

    public boolean deleteOrder(final int id) {
        return this.beverage.deleteOrder(id);
    }
}
