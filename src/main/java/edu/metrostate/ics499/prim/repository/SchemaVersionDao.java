package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SchemaVersion;

/**
 * The SchemaVersionDao interface defines the operations that can be performed on an Interaction instance.
 */
public interface SchemaVersionDao extends IRepository {
    /**
     * Returns a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     *
     * @return a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     */
    SchemaVersion getSchemaVersion();
}
