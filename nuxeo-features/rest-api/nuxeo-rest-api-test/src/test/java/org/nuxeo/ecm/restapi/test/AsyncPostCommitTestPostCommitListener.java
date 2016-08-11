/*
 * (C) Copyright 2013 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Thomas Roger
 */

package org.nuxeo.ecm.restapi.test;

import javax.ws.rs.core.MediaType;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.event.DocumentEventTypes;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventBundle;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.PostCommitEventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.impl.MultiPartWriter;

public class AsyncPostCommitTestPostCommitListener implements PostCommitEventListener {

    public static String DOC_NAME = "TEST_EVENT_ASYNC_SIMPLE";

    public static long delay = 0l;

    public static String path = null;

    public static ClientResponse response = null;

    @Override
    public void handleEvent(EventBundle events) {
        if (!events.containsEventName(DocumentEventTypes.DOCUMENT_CREATED)) {
            return;
        }
        for (Event event : events) {
            if (!DocumentEventTypes.DOCUMENT_CREATED.equals(event.getName())) {
                return;
            }
            EventContext ctx = event.getContext();
            DocumentEventContext docCtx = (DocumentEventContext) ctx;
            DocumentModel model = docCtx.getSourceDocument();
            if (!DOC_NAME.equals(model.getName())) {
                return;
            }
            if (delay > 0l) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("The ref of the doc is: " + model.getRef());
            ClientConfig config = new DefaultClientConfig();
            config.getClasses().add(MultiPartWriter.class);
            Client client = Client.create(config);
            client.setConnectTimeout(Integer.valueOf(1000 * 60 * 5));
            client.setReadTimeout(Integer.valueOf(1000 * 60 * 5));
            client.addFilter(new HTTPBasicAuthFilter("Administrator", "Administrator"));
            String req = "http://localhost:18090/api/v1/id/" + model.getRef();
            System.out.println("The request is : " + req);
            WebResource wr = client.resource(req);
            Builder builder;
            builder = wr.accept(MediaType.APPLICATION_JSON).header("X-NXDocumentProperties", "dublincore");
            builder = builder.header("Content-Type", "application/json+nxentity");
            response = builder.get(ClientResponse.class);
        }
    }
}
