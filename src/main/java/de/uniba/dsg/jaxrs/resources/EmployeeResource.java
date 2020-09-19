package de.uniba.dsg.jaxrs.resources;

import de.uniba.dsg.jaxrs.controller.BeverageService;
import de.uniba.dsg.jaxrs.models.api.PaginatedBottles;
import de.uniba.dsg.jaxrs.models.api.PaginatedCrates;
import de.uniba.dsg.jaxrs.models.api.PaginatedOrders;
import de.uniba.dsg.jaxrs.models.dto.*;
import de.uniba.dsg.jaxrs.models.error.ErrorMessage;
import de.uniba.dsg.jaxrs.models.error.ErrorType;
import de.uniba.dsg.jaxrs.models.logic.Bottle;
import de.uniba.dsg.jaxrs.models.logic.Crate;
import de.uniba.dsg.jaxrs.models.logic.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("employees")
public class EmployeeResource {
    private static final Logger logger = Logger.getLogger("EmployeeResource");

    @GET
    @Path("orders")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getOrder(
            @Context final UriInfo info,
            @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
            @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info(
                "Get all orders. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage =
                    new ErrorMessage(
                            ErrorType.INVALID_PARAMETER,
                            "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Order> helper =
                new PaginationHelper<>(BeverageService.instance.getOrders());
        final PaginatedOrders response =
                new PaginatedOrders(
                        helper.getPagination(info, page, pageLimit),
                        OrderDTO.marshall(
                                helper.getPaginatedList(),
                                info.getBaseUri(),
                                EmployeeResource.class),
                        info.getRequestUri());

        return Response.ok(response).build();
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
        return Response.ok(new OrderDTO(order, info.getBaseUri(), EmployeeResource.class)).build();
    }

    @PUT
    @Path("order/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response UpdateOrder(
            @Context final UriInfo info,
            @PathParam("orderId") final int id,
            final OrderDTO updatedOrder) {

        logger.info("Update order " + updatedOrder);

        if (updatedOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Order order = BeverageService.instance.getOrder(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Order resultOrder =
                BeverageService.instance.updateOrder(id, updatedOrder.unmarshall());

        return Response.ok()
                .entity(new OrderDTO(order, info.getBaseUri(), EmployeeResource.class))
                .build();
    }

    @GET
    @Path("crates")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getCrate(
            @Context final UriInfo info,
            @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
            @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info(
                "Get all crates. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage =
                    new ErrorMessage(
                            ErrorType.INVALID_PARAMETER,
                            "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Crate> helper =
                new PaginationHelper<>(BeverageService.instance.getCrates());
        final PaginatedCrates response =
                new PaginatedCrates(
                        helper.getPagination(info, page, pageLimit),
                        CrateDTO.marshall(
                                helper.getPaginatedList(),
                                info.getBaseUri(),
                                EmployeeResource.class),
                        info.getRequestUri());
        return Response.ok(response).build();
    }

    @POST
    @Path("crate")
    public Response CreateCrate(@Context final UriInfo uriInfo, final CratePostDTO newCrate) {

        logger.info("Create crate " + newCrate);

        if (newCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Crate crate = newCrate.unmarshall();

        final Crate addedCrate = BeverageService.instance.addCrate(crate);

        return Response.created(
                        UriBuilder.fromUri(uriInfo.getBaseUri())
                                .path(EmployeeResource.class)
                                .path("crate")
                                .path(Integer.toString(addedCrate.getId()))
                                .build())
                .build();
    }

    @GET
    @Path("crate/{crateId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response UpdateCrate(@Context final UriInfo info, @PathParam("crateId") final int id) {

        logger.info("Get crate with id " + id);

        final Crate crate = BeverageService.instance.getCrate(id);

        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new CrateDTO(crate, info.getBaseUri(), EmployeeResource.class)).build();
    }

    @PUT
    @Path("crate/{crateId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response UpdateCrate(
            @Context final UriInfo info,
            @PathParam("crateId") final int id,
            final CrateDTO updatedCrate) {

        logger.info("Update crate " + updatedCrate);

        if (updatedCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Crate crate = BeverageService.instance.getCrate(id);

        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Crate resultCrate =
                BeverageService.instance.updateCrate(id, updatedCrate.unmarshall());

        return Response.ok()
                .entity(new CrateDTO(resultCrate, info.getBaseUri(), EmployeeResource.class))
                .build();
    }

    @DELETE
    @Path("crate/{crateId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response DeleteCrate(@Context final UriInfo info, @PathParam("crateId") final int id) {

        logger.info("Delete crate with id " + id);

        final boolean deleted = BeverageService.instance.deleteCrate(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }

    @GET
    @Path("bottles")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(
            @Context final UriInfo info,
            @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
            @QueryParam("page") @DefaultValue("1") final int page) {

        logger.info(
                "Get all bottles. Pagination parameter: page-" + page + " pageLimit-" + pageLimit);

        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage =
                    new ErrorMessage(
                            ErrorType.INVALID_PARAMETER,
                            "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        final PaginationHelper<Bottle> helper =
                new PaginationHelper<>(BeverageService.instance.getBottles());
        final PaginatedBottles response =
                new PaginatedBottles(
                        helper.getPagination(info, page, pageLimit),
                        BottleDTO.marshall(
                                helper.getPaginatedList(),
                                info.getBaseUri(),
                                EmployeeResource.class),
                        info.getRequestUri());
        return Response.ok(response).build();
    }

    @POST
    @Path("bottle")
    public Response CreateBottle(@Context final UriInfo uriInfo, final BottlePostDTO newBottle) {

        logger.info("Create bottle " + newBottle);

        if (newBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }
        final Bottle bottle = newBottle.unmarshall();

        final Bottle addedBottle = BeverageService.instance.addBottle(bottle);

        return Response.created(
                        UriBuilder.fromUri(uriInfo.getBaseUri())
                                .path(EmployeeResource.class)
                                .path("bottle")
                                .path(Integer.toString(addedBottle.getId()))
                                .build())
                .build();
    }

    @GET
    @Path("bottle/{bottleId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(@Context final UriInfo info, @PathParam("bottleId") final int id) {

        logger.info("Get bottle with id " + id);

        final Bottle bottle = BeverageService.instance.getBottle(id);

        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(new BottleDTO(bottle, info.getBaseUri(), EmployeeResource.class))
                .build();
    }

    @PUT
    @Path("bottle/{bottleId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response UpdateBottle(
            @Context final UriInfo info,
            @PathParam("bottleId") final int id,
            final BottlePostDTO updatedBottle) {

        logger.info("Update bottle " + updatedBottle);

        if (updatedBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Bottle bottle = BeverageService.instance.getBottle(id);

        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final Bottle resultBottle =
                BeverageService.instance.updateBottle(id, updatedBottle.unmarshall());

        return Response.ok()
                .entity(new BottleDTO(resultBottle, info.getBaseUri(), EmployeeResource.class))
                .build();
    }

    @DELETE
    @Path("bottle/{bottleId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response DeleteBottle(@Context final UriInfo info, @PathParam("bottleId") final int id) {

        logger.info("Delete bottle with id " + id);

        final boolean deleted = BeverageService.instance.deleteBottle(id);

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().build();
        }
    }
}
