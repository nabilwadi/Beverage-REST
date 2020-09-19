package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Beverage;
import de.uniba.dsg.jaxrs.models.logic.Order;
import de.uniba.dsg.jaxrs.models.logic.OrderItem;
import de.uniba.dsg.jaxrs.models.logic.OrderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderPost")
@XmlType(propOrder = {"positions", "price", "status"})
public class OrderPostDTO {

    private List<OrderItemDTO> positions;
    private double price;
    private OrderStatus status;

    public OrderPostDTO() {}

    public List<OrderItemDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItem> positions) {
        for (OrderItem oi : positions) {
            this.positions.add(new OrderItemDTO(oi));
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Order unmarshall() {
        ArrayList<OrderItem> orderList = new ArrayList<>();
        for (OrderItemDTO oi : this.positions) {
            orderList.add(
                    new OrderItem(
                            oi.getNumber(),
                            (oi.getBottle() == null
                                    ? (Beverage) oi.getBottle()
                                    : (Beverage) oi.getCrate()),
                            oi.getQuantity()));
        }
        return new Order(-1, orderList, this.price, this.status);
    }

    @Override
    public String toString() {
        return "OrderDTO{"
                + ", positions="
                + positions
                + ", price="
                + price
                + ", status="
                + status
                + '}';
    }
}
