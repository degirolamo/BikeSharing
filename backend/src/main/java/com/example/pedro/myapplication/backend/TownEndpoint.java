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
        name = "townApi",
        version = "v1",
        resource = "town",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.pedro.example.com",
                ownerName = "backend.myapplication.pedro.example.com",
                packagePath = ""
        )
)
public class TownEndpoint {

    private static final Logger logger = Logger.getLogger(TownEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Town.class);
    }

    /**
     * Returns the {@link Town} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Town} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "town/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Town get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Town with ID: " + id);
        Town town = ofy().load().type(Town.class).id(id).now();
        if (town == null) {
            throw new NotFoundException("Could not find Town with ID: " + id);
        }
        return town;
    }

    /**
     * Inserts a new {@code Town}.
     */
    @ApiMethod(
            name = "insert",
            path = "town",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Town insert(Town town) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that town.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(town).now();
        logger.info("Created Town with ID: " + town.getId());

        return ofy().load().entity(town).now();
    }

    /**
     * Updates an existing {@code Town}.
     *
     * @param id   the ID of the entity to be updated
     * @param town the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Town}
     */
    @ApiMethod(
            name = "update",
            path = "town/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Town update(@Named("id") long id, Town town) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(town).now();
        logger.info("Updated Town: " + town);
        return ofy().load().entity(town).now();
    }

    /**
     * Deletes the specified {@code Town}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Town}
     */
    @ApiMethod(
            name = "remove",
            path = "town/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Town.class).id(id).now();
        logger.info("Deleted Town with ID: " + id);
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
            path = "town",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Town> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Town> query = ofy().load().type(Town.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Town> queryIterator = query.iterator();
        List<Town> townList = new ArrayList<Town>(limit);
        while (queryIterator.hasNext()) {
            townList.add(queryIterator.next());
        }
        return CollectionResponse.<Town>builder().setItems(townList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Town.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Town with ID: " + id);
        }
    }
}