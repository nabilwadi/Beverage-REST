package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.models.api.PaginatedBottles;
import de.uniba.dsg.jaxrs.models.api.PaginatedCrates;
import de.uniba.dsg.jaxrs.models.dto.*;
import de.uniba.dsg.jaxrs.models.error.ErrorMessage;
import de.uniba.dsg.jaxrs.models.error.ErrorType;
import de.uniba.dsg.jaxrs.models.logic.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("user")
public class UserResource {
    private static final Logger logger = Logger.getLogger("UserResource");

    @POST
    @Path("order")
    public Response createOrder(@Context final UriInfo uriInfo, final OrderPostDTO newOrder) {

        logger.info("Create order " + newOrder);

        if (newOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Order order = newOrder.unmarshall();

        final Order addedOrder = BeverageService.instance.addOrder(order);

        return Response.created(
                        UriBuilder.fromUri(uriInfo.getBaseUri())
                                .path(EmployeeResource.class)
                                .path("order")
                                .path(Integer.toString(addedOrder.getOrderId()))
                                .build())
                .build();
    }

    @GET
    @Path("order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getOrder(@Context final UriInfo info, @PathParam("orderId") final int id) {

        logger.info("Get order with id " + id);

        final Order order = BeverageService.instance.getOrder(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new OrderDTO(order, info.getBaseUri(), UserResource.class)).build();
    }

    @PUT
    @Path("order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response UpdateOrder(
            @Context final UriInfo info,
            @PathParam("orderId") final int id,
            final OrderDTO updatedOrder) {

        if (updatedOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Order order = BeverageService.instance.getOrder(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (order.getStatus() != OrderStatus.SUBMITTED) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage(ErrorType.FORBIDDEN, "Order already processed"))
                    .build();
        }

        final Order resultOrder =
                BeverageService.instance.updateOrder(id, updatedOrder.unmarshall());

        return Response.ok()
                .entity(new OrderDTO(resultOrder, info.getBaseUri(), UserResource.class))
                .build();
    }

    @DELETE
    @Path("order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response DeleteBottle(@Context final UriInfo info, @PathParam("orderId") final int id) {

        logger.info("Delete order with id " + id);

        final Order order = BeverageService.instance.getOrder(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (order.getStatus() != OrderStatus.SUBMITTED) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorMessage(ErrorType.FORBIDDEN, "Order already processed"))
                    .build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("crates")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getCrate(
            @Context final UriInfo info,
            @QueryParam("minPrice") @DefaultValue("-1") final double minPrice,
            @QueryParam("maxPrice") @DefaultValue("-1") final double maxPrice,
            @QueryParam("sort") @DefaultValue("asc") final String mode,
            @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
            @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info(
                "Get all crates. Pagination parameter: page-"
                        + page
                        + " pageLimit-"
                        + pageLimit
                        + "with minPrice-"
                        + minPrice
                        + " and maxPrice-"
                        + maxPrice
                        + ", Sorting: "
                        + mode);

        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage =
                    new ErrorMessage(
                            ErrorType.INVALID_PARAMETER,
                            "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Crate> helper =
                new PaginationHelper<>(
                        BeverageService.instance.getCratesFilter(minPrice, maxPrice, mode));
        final PaginatedCrates response =
                new PaginatedCrates(
                        helper.getPagination(info, page, pageLimit),
                        CrateDTO.marshall(
                                helper.getPaginatedList(), info.getBaseUri(), UserResource.class),
                        info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("bottles")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(
            @Context final UriInfo info,
            @QueryParam("minPrice") @DefaultValue("-1") final double minPrice,
            @QueryParam("maxPrice") @DefaultValue("-1") final double maxPrice,
            @QueryParam("sort") @DefaultValue("asc") final String mode,
            @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
            @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info(
                "Get all bottles. Pagination parameter: page-"
                        + page
                        + " pageLimit-"
                        + pageLimit
                        + "with minPrice-"
                        + minPrice
                        + " and maxPrice-"
                        + maxPrice
                        + ", Sorting: "
                        + mode);

        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage =
                    new ErrorMessage(
                            ErrorType.INVALID_PARAMETER,
                            "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Bottle> helper =
                new PaginationHelper<>(
                        BeverageService.instance.getBottlesFilter(minPrice, maxPrice, mode));
        final PaginatedBottles response =
                new PaginatedBottles(
                        helper.getPagination(info, page, pageLimit),
                        BottleDTO.marshall(
                                helper.getPaginatedList(), info.getBaseUri(), UserResource.class),
                        info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("search")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(
            @Context final UriInfo uriInfo,
            @QueryParam("searchMode") @DefaultValue("") final String search,
            @QueryParam("mode") @DefaultValue("NAME") final SearchMode mode) {

        List<Bottle> bottles = BeverageService.instance.searchBottles(search, mode);
        List<Crate> crates = BeverageService.instance.searchCrates(search, mode);

        if (bottles == null || crates == null) {
            final ErrorMessage errorMessage =
                    new ErrorMessage(
                            ErrorType.INVALID_PARAMETER,
                            "Wrong searchmode. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        List<BottleDTO> bottlesDto = new ArrayList<>();
        for (Bottle bottle : bottles) {
            bottlesDto.add(new BottleDTO(bottle, uriInfo.getBaseUri(), UserResource.class));
        }

        List<CrateDTO> cratesDto = new ArrayList<>();
        for (Crate crate : crates) {
            cratesDto.add(new CrateDTO(crate, uriInfo.getBaseUri(), UserResource.class));
        }

        return Response.ok()
                .entity(
                        new BeverageDTO(
                                search,
                                bottlesDto,
                                cratesDto,
                                mode,
                                uriInfo.getBaseUri(),
                                UserResource.class))
                .build();
    }
}
