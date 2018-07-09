package edu.metrostate.ics499.prim.service;

import edu.metrostate.ics499.prim.model.SchemaVersion;
import edu.metrostate.ics499.prim.repository.SchemaVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The SchemaVersionServiceImpl implements the SchemaVersionService
 * interface for easily working with the SchemaVersion.
 */
@Service("schemaVersionService")
@Transactional
public class SchemaVersionServiceImpl implements SchemaVersionService {

    @Autowired
    private SchemaVersionDao dao;

    /**
     * Returns a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     *
     * @return a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     */
    @Override
    public SchemaVersion getSchemaVersion() {
        return dao.getSchemaVersion();
    }

    /**
     * Updates the persistent SchemaVersion based on the specified SchemaVersion.
     *
     * @param schemaVersion the SchemaVersion to update.
     */
    @Override
    public void update(SchemaVersion schemaVersion) {
        SchemaVersion entity = dao.getSchemaVersion();
        if (entity != null) {
            entity.setMajor(schemaVersion.getMajor());
            entity.setMinor(schemaVersion.getMinor());
            entity.setRevision(schemaVersion.getRevision());
        }
    }
}
