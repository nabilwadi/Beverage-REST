package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Bottle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottlePost")
@XmlType(
        propOrder = {
            "id",
            "name",
            "volume",
            "isAlcoholic",
            "volumePercent",
            "price",
            "supplier",
            "inStock"
        })
public class BottlePostDTO {

    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;

    public BottlePostDTO() {}

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

    public Bottle unmarshall() {
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
