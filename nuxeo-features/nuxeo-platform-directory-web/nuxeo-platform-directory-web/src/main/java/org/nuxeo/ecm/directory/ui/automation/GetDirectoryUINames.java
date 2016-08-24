package org.nuxeo.ecm.directory.ui.automation;

import java.util.List;

import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.directory.api.ui.DirectoryUIManager;
import org.nuxeo.runtime.api.Framework;

@Operation(id = GetDirectoryUINames.ID, category = Constants.CAT_SERVICES, label = "Get directory UI names", description = "Get directory UI names."
        + "No value is returned.")
public class GetDirectoryUINames {
    
    public static final String ID = "Directory.GetDirecotryUINames";
    
    @OperationMethod()
    public List<String> run() {
        DirectoryUIManager directoryUIManager = Framework.getService(DirectoryUIManager.class);
        return directoryUIManager.getDirectoryNames();
    }
    
}
