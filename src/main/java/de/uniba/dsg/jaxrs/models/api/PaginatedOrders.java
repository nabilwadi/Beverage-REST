package de.uniba.dsg.jaxrs.models.api;

import de.uniba.dsg.jaxrs.models.dto.OrderDTO;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"pagination", "orders", "href"})
public class PaginatedOrders {

    private Pagination pagination;
    private List<OrderDTO> orders;

    private URI href;

    public PaginatedOrders() {}

    public PaginatedOrders(
            final Pagination pagination, final List<OrderDTO> orders, final URI href) {
        this.pagination = pagination;
        this.orders = orders;
        this.href = href;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "PaginatedOrders{"
                + "pagination="
                + pagination
                + ", orders="
                + orders
                + ", href="
                + href
                + '}';
    }
}
