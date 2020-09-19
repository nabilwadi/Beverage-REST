package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Crate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cratePost")
@XmlType(propOrder = {"bottle", "noOfBottles", "price", "inStock"})
public class CratePostDTO {

    private BottlePostDTO bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public CratePostDTO() {}

    public BottlePostDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottlePostDTO bottle) {
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

    public Crate unmarshall() {
        return new Crate(-1, this.bottle.unmarshall(), this.noOfBottles, this.price, this.inStock);
    }

    @Override
    public String toString() {
        return "CrateDTO{"
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
