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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.TransactionalFeature;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.platform.audit.AuditFeature;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;
import org.nuxeo.runtime.test.runner.LocalDeploy;
import org.nuxeo.runtime.transaction.TransactionHelper;

/**
 * @since 5.7.3
 */
@RunWith(FeaturesRunner.class)
@Features({ RestServerFeature.class, AuditFeature.class, TransactionalFeature.class })
@LocalDeploy({ "org.nuxeo.ecm.restapi.test:post-commit-async-event.xml" })
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = RestServerInit.class)
public class AsyncPostCommitEventTest extends BaseTest {

    @Test
    public void simpleListener() throws Exception {
        AsyncPostCommitTestSimpleListener.delay = 0l;
        AsyncPostCommitTestSimpleListener.response = null;
        DocumentModel model = session.createDocumentModel("/", AsyncPostCommitTestSimpleListener.DOC_NAME, "Folder");
        model = session.createDocument(model);
        session.save();
        TransactionHelper.commitOrRollbackTransaction();
        TransactionHelper.startTransaction();
        Thread.sleep(2000);
        assertNotNull(AsyncPostCommitTestSimpleListener.response);
        assertEquals(Response.Status.OK.getStatusCode(), AsyncPostCommitTestSimpleListener.response.getStatus());
    }

    @Test
    public void postCommitListener() throws Exception {
        AsyncPostCommitTestPostCommitListener.delay = 0l;
        AsyncPostCommitTestPostCommitListener.response = null;
        DocumentModel model = session.createDocumentModel("/", AsyncPostCommitTestPostCommitListener.DOC_NAME, "Folder");
        model = session.createDocument(model);
        session.save();
        TransactionHelper.commitOrRollbackTransaction();
        TransactionHelper.startTransaction();
        Thread.sleep(2000);
        assertNotNull(AsyncPostCommitTestPostCommitListener.response);
        assertEquals(Response.Status.OK.getStatusCode(), AsyncPostCommitTestPostCommitListener.response.getStatus());
    }

}
