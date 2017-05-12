package com.example.pedro.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "bikeApi",
        version = "v1",
        resource = "bike",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.pedro.example.com",
                ownerName = "backend.myapplication.pedro.example.com",
                packagePath = ""
        )
)
public class BikeEndpoint {

    private static final Logger logger = Logger.getLogger(BikeEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Bike.class);
    }

    /**
     * Returns the {@link Bike} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Bike} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "bike/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Bike get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Bike with ID: " + id);
        Bike bike = ofy().load().type(Bike.class).id(id).now();
        if (bike == null) {
            throw new NotFoundException("Could not find Bike with ID: " + id);
        }
        return bike;
    }

    /**
     * Inserts a new {@code Bike}.
     */
    @ApiMethod(
            name = "insert",
            path = "bike",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Bike insert(Bike bike) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that bike.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(bike).now();
        logger.info("Created Bike with ID: " + bike.getId());

        return ofy().load().entity(bike).now();
    }

    /**
     * Updates an existing {@code Bike}.
     *
     * @param id   the ID of the entity to be updated
     * @param bike the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bike}
     */
    @ApiMethod(
            name = "update",
            path = "bike/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Bike update(@Named("id") long id, Bike bike) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(bike).now();
        logger.info("Updated Bike: " + bike);
        return ofy().load().entity(bike).now();
    }

    /**
     * Deletes the specified {@code Bike}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Bike}
     */
    @ApiMethod(
            name = "remove",
            path = "bike/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Bike.class).id(id).now();
        logger.info("Deleted Bike with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "bike",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Bike> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Bike> query = ofy().load().type(Bike.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Bike> queryIterator = query.iterator();
        List<Bike> bikeList = new ArrayList<Bike>(limit);
        while (queryIterator.hasNext()) {
            bikeList.add(queryIterator.next());
        }
        return CollectionResponse.<Bike>builder().setItems(bikeList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Bike.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Bike with ID: " + id);
        }
    }
}