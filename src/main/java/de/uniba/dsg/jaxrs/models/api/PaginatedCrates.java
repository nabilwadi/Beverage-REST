package de.uniba.dsg.jaxrs.models.api;

import de.uniba.dsg.jaxrs.models.dto.CrateDTO;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"pagination", "crates", "href"})
public class PaginatedCrates {

    private Pagination pagination;
    private List<CrateDTO> crates;

    private URI href;

    public PaginatedCrates() {}

    public PaginatedCrates(
            final Pagination pagination, final List<CrateDTO> crates, final URI href) {
        this.pagination = pagination;
        this.crates = crates;
        this.href = href;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<CrateDTO> getCrates() {
        return crates;
    }

    public void setCrates(List<CrateDTO> crates) {
        this.crates = crates;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
