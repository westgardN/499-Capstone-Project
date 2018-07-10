package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SchemaVersion;

/**
 * The SchemaVersionService provides an interface for easily working with the SchemaVersion.
 */
public interface SchemaVersionService {

    /**
     * Returns a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     *
     * @return a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     */
    SchemaVersion getSchemaVersion();

    /**
     * Updates the persistent SchemaVersion based on the specified SchemaVersion.
     *
     * @param schemaVersion the SchemaVersion to update.
     */
    void update(SchemaVersion schemaVersion);

}
