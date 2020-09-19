package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.Order;
import de.uniba.dsg.jaxrs.models.logic.OrderItem;
import de.uniba.dsg.jaxrs.models.logic.OrderStatus;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = {"orderId", "positions", "price", "status", "href", "baseUri", "c"})
public class OrderDTO {

    private int orderId;
    private List<OrderItemDTO> positions;
    private double price;
    private OrderStatus status;

    private URI href;
    private URI baseUri = null;
    private Class c = null;

    public OrderDTO() {}

    public OrderDTO(final Order order, final URI baseUri, final Class c) {
        this.orderId = order.getOrderId();
        positions = new ArrayList<>();

        for (OrderItem oi : order.getPositions()) {
            positions.add(new OrderItemDTO(oi, baseUri, c));
        }
        this.price = order.getPrice();
        this.status = order.getStatus();
        this.href =
                UriBuilder.fromUri(baseUri)
                        .path(c)
                        .path("order/")
                        .path(Integer.toString(this.orderId))
                        .build();
        this.baseUri = baseUri;
        this.c = c;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDTO> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItem> positions) {
        for (OrderItem oi : positions) {
            this.positions.add(new OrderItemDTO(oi, baseUri, c));
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

    public static List<OrderDTO> marshall(List<Order> paginatedList, URI baseUri, Class c) {
        final ArrayList<OrderDTO> orders = new ArrayList<>();
        for (final Order order : paginatedList) {
            orders.add(new OrderDTO(order, baseUri, c));
        }
        return orders;
    }

    public Order unmarshall() {
        ArrayList<OrderItem> orderList = new ArrayList<>();
        for (OrderItemDTO oi : this.positions) {
            orderList.add(oi.unmarshall());
        }
        return new Order(this.orderId, orderList, this.price, this.status);
    }

    @Override
    public String toString() {
        return "OrderDTO{"
                + "orderId="
                + orderId
                + ", positions="
                + positions
                + ", price="
                + price
                + ", status="
                + status
                + '}';
    }
}
