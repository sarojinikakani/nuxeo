package org.nuxeo.ecm.directory.ui.io;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.io.marshallers.json.ExtensibleEntityJsonWriter;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;
import org.nuxeo.ecm.directory.api.ui.DirectoryUI;

/**
 * 
 * @since 8.4
 */
@Setup(mode = SINGLETON, priority = REFERENCE)
public class DirectoryUIJsonWriter extends ExtensibleEntityJsonWriter<DirectoryUI> {

    public static final String ENTITY_TYPE = "directoryui";
    
    public DirectoryUIJsonWriter() {
        super(ENTITY_TYPE, DirectoryUI.class);
    }

    @Override
    protected void writeEntityBody(DirectoryUI entity, JsonGenerator jg) throws IOException {
        jg.writeStringField("name", entity.getName());
    }

}