package de.uniba.dsg.jaxrs.models.dto;

import de.uniba.dsg.jaxrs.models.logic.SearchMode;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverage")
@XmlType(propOrder = {"search", "bottle", "crate", "mode", "href"})
public class BeverageDTO {

    private String search;
    private List<BottleDTO> bottle;
    private List<CrateDTO> crate;
    private SearchMode mode;
    private URI href;

    public BeverageDTO() {}

    public BeverageDTO(
            String search,
            List<BottleDTO> bottle,
            List<CrateDTO> crate,
            SearchMode mode,
            URI baseUri,
            Class c) {
        this.search = search;
        this.bottle = bottle;
        this.crate = crate;
        this.mode = mode;
        this.href = UriBuilder.fromUri(baseUri).path(c).path("/search/").build(search);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<BottleDTO> getBottle() {
        return bottle;
    }

    public void setBottle(List<BottleDTO> bottle) {
        this.bottle = bottle;
    }

    public List<CrateDTO> getCrate() {
        return crate;
    }

    public void setCrate(List<CrateDTO> crate) {
        this.crate = crate;
    }

    public SearchMode getMode() {
        return mode;
    }

    public void setMode(SearchMode mode) {
        this.mode = mode;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
