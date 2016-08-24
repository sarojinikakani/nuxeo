package org.nuxeo.ecm.restapi.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.test.EmbeddedAutomationServerFeature;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.restapi.test.BaseTest;
import org.nuxeo.ecm.restapi.test.RestServerInit;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;
import org.nuxeo.runtime.test.runner.LocalDeploy;

import com.sun.jersey.api.client.ClientResponse;

@RunWith(FeaturesRunner.class)
@Features({ EmbeddedAutomationServerFeature.class })
@RepositoryConfig(cleanup = Granularity.METHOD, init = RestServerInit.class)
@Jetty(port = 18090)
@Deploy({ "org.nuxeo.ecm.directory", "org.nuxeo.ecm.directory.sql", "org.nuxeo.ecm.directory.types.contrib",
        "org.nuxeo.ecm.actions", "org.nuxeo.ecm.directory.web", "org.nuxeo.ecm.platform.restapi.server.directory.web",
        "org.nuxeo.ecm.automation.test", "org.nuxeo.ecm.automation.io", "org.nuxeo.ecm.platform.restapi.io",
        "org.nuxeo.ecm.platform.restapi.test", "org.nuxeo.ecm.platform.restapi.server" })
@LocalDeploy({ "org.nuxeo.ecm.platform.restapi.server.directory.web.test:OSGI-INF/test-directory-ui-sql-contrib.xml",
    "org.nuxeo.ecm.platform.restapi.server.directory.web.test:OSGI-INF/test-directory-ui-contrib.xml" })
public class DirectoryUIObjectTest extends BaseTest {

    @Test
    public void testDirectoryUIsInfo() throws JsonProcessingException, IOException {
        ClientResponse response = getResponse(RequestType.GET, "/directoryui");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        JsonNode node = mapper.readTree(response.getEntityInputStream());
        assertNotNull(node);
        JsonNode entries = node.get("entries");
        assertEquals(2, entries.size());
        assertEquals("continent", entries.get(0).get("name").getTextValue());
        assertEquals("country", entries.get(1).get("name").getTextValue());
    }

}
