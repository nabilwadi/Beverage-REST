package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Bottle;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(
        propOrder = {
            "id",
            "name",
            "volume",
            "isAlcoholic",
            "volumePercent",
            "price",
            "supplier",
            "inStock",
            "href"
        })
public class BottleDTO {

    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;
    private URI href;

    public BottleDTO() {}

    public BottleDTO(Bottle bottle, URI baseUri, Class c) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
        this.href =
                UriBuilder.fromUri(baseUri)
                        .path(c)
                        .path("bottle")
                        .path(Integer.toString(this.id))
                        .build();
    }

    BottleDTO(Bottle bottle) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public static List<BottleDTO> marshall(List<Bottle> paginatedList, URI baseUri, Class c) {
        final ArrayList<BottleDTO> bottles = new ArrayList<>();
        for (final Bottle bottle : paginatedList) {
            bottles.add(new BottleDTO(bottle, baseUri, c));
        }
        return bottles;
    }

    Bottle unmarshall() {
        return new Bottle(
                this.id,
                this.name,
                this.volume,
                this.isAlcoholic,
                this.volumePercent,
                this.price,
                this.supplier,
                this.inStock);
    }

    @Override
    public String toString() {
        return "BottleDTO{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", volume="
                + volume
                + ", isAlcoholic="
                + isAlcoholic
                + ", volumePercent="
                + volumePercent
                + ", price="
                + price
                + ", supplier='"
                + supplier
                + '\''
                + ", inStock="
                + inStock
                + '}';
    }
}
