package org.nuxeo.ecm.directory.ui;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationChain;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.directory.ui.automation.GetDirectoryUINames;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.LocalDeploy;

@RunWith(FeaturesRunner.class)
@Features(PlatformFeature.class)
@Deploy({ "org.nuxeo.ecm.directory", "org.nuxeo.ecm.directory.sql", "org.nuxeo.ecm.directory.types.contrib",
    "org.nuxeo.ecm.actions", "org.nuxeo.ecm.directory.web", "org.nuxeo.ecm.automation.core",
    "org.nuxeo.ecm.automation.features" })
@LocalDeploy("org.nuxeo.ecm.directory.web.tests:OSGI-INF/test-directory-ui-sql-contrib.xml")
public class TestDirectoryUIOperation {
    
    @Inject
    CoreSession session;

    @Inject
    AutomationService service;

    OperationChain chain;

    @Test
    public void testGetDirectoryUINames() throws Exception {
        chain = new OperationChain("test-chain");
        chain.add(GetDirectoryUINames.ID);

        OperationContext ctx = new OperationContext(session);

        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) service.run(ctx, chain);

        // Test the result of the operation
        assertFalse(result.isEmpty());
    }
}
