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
        name = "cantonApi",
        version = "v1",
        resource = "canton",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.pedro.example.com",
                ownerName = "backend.myapplication.pedro.example.com",
                packagePath = ""
        )
)
public class CantonEndpoint {

    private static final Logger logger = Logger.getLogger(CantonEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 26;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Canton.class);
    }

    /**
     * Returns the {@link Canton} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Canton} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "canton/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Canton get(@Named("id") long id) throws NotFoundException {
        logger.info("Getting Canton with ID: " + id);
        Canton canton = ofy().load().type(Canton.class).id(id).now();
        if (canton == null) {
            throw new NotFoundException("Could not find Canton with ID: " + id);
        }
        return canton;
    }

    /**
     * Inserts a new {@code Canton}.
     */
    @ApiMethod(
            name = "insert",
            path = "canton",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Canton insert(Canton canton) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that canton.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(canton).now();
        logger.info("Created Canton with ID: " + canton.getId());

        return ofy().load().entity(canton).now();
    }

    /**
     * Updates an existing {@code Canton}.
     *
     * @param id     the ID of the entity to be updated
     * @param canton the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Canton}
     */
    @ApiMethod(
            name = "update",
            path = "canton/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Canton update(@Named("id") long id, Canton canton) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(canton).now();
        logger.info("Updated Canton: " + canton);
        return ofy().load().entity(canton).now();
    }

    /**
     * Deletes the specified {@code Canton}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Canton}
     */
    @ApiMethod(
            name = "remove",
            path = "canton/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Canton.class).id(id).now();
        logger.info("Deleted Canton with ID: " + id);
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
            path = "canton",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Canton> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Canton> query = ofy().load().type(Canton.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Canton> queryIterator = query.iterator();
        List<Canton> cantonList = new ArrayList<Canton>(limit);
        while (queryIterator.hasNext()) {
            cantonList.add(queryIterator.next());
        }
        return CollectionResponse.<Canton>builder().setItems(cantonList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(long id) throws NotFoundException {
        try {
            ofy().load().type(Canton.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Canton with ID: " + id);
        }
    }
}