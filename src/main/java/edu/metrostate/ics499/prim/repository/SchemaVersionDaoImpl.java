package edu.metrostate.ics499.prim.repository;

import edu.metrostate.ics499.prim.model.SchemaVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("schemaVersionDao")
public class SchemaVersionDaoImpl extends AbstractDao<Integer, SchemaVersion> implements SchemaVersionDao {
    static final Logger logger = LoggerFactory.getLogger(SchemaVersionDaoImpl.class);

    /**
     * Returns a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     *
     * @return a persistent SchemaVersion object. If no SchemaVersion exists, null is returned.
     */
    @Override
    public SchemaVersion getSchemaVersion() {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SchemaVersion> crit = builder.createQuery(SchemaVersion.class);
        Root<SchemaVersion> from = crit.from(SchemaVersion.class);
        crit.select(from).orderBy(builder.desc(from.get("id")));
        TypedQuery<SchemaVersion> query = getSession().createQuery(crit);
        List<SchemaVersion> versionList = query.getResultList();

        return versionList.isEmpty() ? null : versionList.get(0);
    }
}
