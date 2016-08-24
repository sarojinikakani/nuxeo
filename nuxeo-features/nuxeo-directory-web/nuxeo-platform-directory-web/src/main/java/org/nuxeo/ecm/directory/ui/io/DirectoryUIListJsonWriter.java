package org.nuxeo.ecm.directory.ui.io;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;

import org.nuxeo.ecm.core.io.marshallers.json.DefaultListJsonWriter;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;
import org.nuxeo.ecm.directory.api.ui.DirectoryUI;

/**
 * @since 8.4
 */
@Setup(mode = SINGLETON, priority = REFERENCE)
public class DirectoryUIListJsonWriter extends DefaultListJsonWriter<DirectoryUI> {
    
    public static final String ENTITY_TYPE = "directoryuis";

    public DirectoryUIListJsonWriter() {
        super(ENTITY_TYPE, DirectoryUI.class);
    }


}