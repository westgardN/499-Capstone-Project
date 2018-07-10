package edu.metrostate.ics499.prim.converter;

import edu.metrostate.ics499.prim.model.Role;
import edu.metrostate.ics499.prim.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A converter class used by the framework to map user ids to user profile objects
 */
@Component
public class RoleIdToRoleTypeConverter implements Converter<Object, Role>{

    static final Logger logger = LoggerFactory.getLogger(RoleIdToRoleTypeConverter.class);

    @Autowired
    RoleService roleService;

    /**
     * Gets Role by Id
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    public Role convert(Object element) {
        Integer id = Integer.parseInt((String)element);
        Role role= roleService.findById(id);
        logger.info("Role : {}", role);
        return role;
    }

}