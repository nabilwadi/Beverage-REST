package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Crate;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"id", "bottle", "noOfBottles", "price", "inStock", "href"})
public class CrateDTO {
    private int id;
    private BottleDTO bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    private URI href;

    public CrateDTO() {}

    public CrateDTO(Crate crate, URI baseUri, Class c) {
        this.id = crate.getId();
        this.bottle = new BottleDTO(crate.getBottle(), baseUri, c);
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();
        this.inStock = crate.getInStock();
        this.href =
                UriBuilder.fromUri(baseUri)
                        .path(c)
                        .path("crate")
                        .path(Integer.toString(this.id))
                        .build();
    }

    CrateDTO(Crate crate) {
        this.id = crate.getId();
        this.bottle = new BottleDTO(crate.getBottle());
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();
        this.inStock = crate.getInStock();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BottleDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleDTO bottle) {
        this.bottle = bottle;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public static List<CrateDTO> marshall(List<Crate> paginatedList, URI baseUri, Class c) {
        final ArrayList<CrateDTO> crates = new ArrayList<>();
        for (final Crate crate : paginatedList) {
            crates.add(new CrateDTO(crate, baseUri, c));
        }
        return crates;
    }

    public Crate unmarshall() {
        return new Crate(
                this.id, this.bottle.unmarshall(), this.noOfBottles, this.price, this.inStock);
    }

    @Override
    public String toString() {
        return "CrateDTO{"
                + "id="
                + id
                + ", bottle="
                + bottle
                + ", noOfBottles="
                + noOfBottles
                + ", price="
                + price
                + ", inStock="
                + inStock
                + '}';
    }
}
