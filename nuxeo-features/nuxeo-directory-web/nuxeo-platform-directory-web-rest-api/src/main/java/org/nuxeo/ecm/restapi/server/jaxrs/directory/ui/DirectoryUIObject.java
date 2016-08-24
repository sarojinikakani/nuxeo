package org.nuxeo.ecm.restapi.server.jaxrs.directory.ui;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;

import org.nuxeo.ecm.directory.api.ui.DirectoryUI;
import org.nuxeo.ecm.directory.api.ui.DirectoryUIManager;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;
import org.nuxeo.runtime.api.Framework;

/**
 * @since 8.4
 */
@WebObject(type = "directoryui")
public class DirectoryUIObject extends DefaultObject {

    @GET
    public List<DirectoryUI> getDirectoryUIs() {
        DirectoryUIManager directoryUIManager = Framework.getService(DirectoryUIManager.class);
        List<String> names = directoryUIManager.getDirectoryNames();
        List<DirectoryUI> result = new ArrayList<DirectoryUI>(names.size());
        for (String name : names) {
            result.add(directoryUIManager.getDirectoryInfo(name));
        }
        return result;
    }
    
}
