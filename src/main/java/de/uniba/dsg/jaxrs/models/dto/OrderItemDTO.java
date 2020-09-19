package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Bottle;
import de.uniba.dsg.jaxrs.models.logic.Crate;
import de.uniba.dsg.jaxrs.models.logic.OrderItem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderitem")
@XmlType(propOrder = {"number", "bottle", "crate", "quantity"})
public class OrderItemDTO {
    private int number;
    private BottleDTO bottle;
    private CrateDTO crate;
    private int quantity;

    public OrderItemDTO() {}

    OrderItemDTO(OrderItem orderItem, URI baseUri, Class c) {
        this.number = orderItem.getNumber();
        if (orderItem.getBeverage() instanceof Bottle) {
            this.bottle = new BottleDTO((Bottle) orderItem.getBeverage(), baseUri, c);
        } else if (orderItem.getBeverage() instanceof Crate) {
            this.crate = new CrateDTO((Crate) orderItem.getBeverage(), baseUri, c);
        }
        this.quantity = orderItem.getQuantity();
    }

    OrderItemDTO(OrderItem orderItem) {
        this.number = orderItem.getNumber();
        if (orderItem.getBeverage() instanceof Bottle) {
            this.bottle = new BottleDTO((Bottle) orderItem.getBeverage());
        } else if (orderItem.getBeverage() instanceof Crate) {
            this.crate = new CrateDTO((Crate) orderItem.getBeverage());
        }
        this.quantity = orderItem.getQuantity();
    }

    int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BottleDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleDTO bottle) {
        this.bottle = bottle;
    }

    CrateDTO getCrate() {
        return crate;
    }

    public void setCrate(CrateDTO crate) {
        this.crate = crate;
    }

    int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    OrderItem unmarshall() {
        return new OrderItem(
                this.getNumber(),
                (this.getBottle() != null
                        ? (this.getBottle().unmarshall())
                        : (this.getCrate().unmarshall())),
                this.getQuantity());
    }

    @Override
    public String toString() {
        return "OrderItemDTO{"
                + "number="
                + number
                + ", bottle="
                + bottle
                + ", crate="
                + crate
                + ", quantity="
                + quantity
                + '}';
    }
}
