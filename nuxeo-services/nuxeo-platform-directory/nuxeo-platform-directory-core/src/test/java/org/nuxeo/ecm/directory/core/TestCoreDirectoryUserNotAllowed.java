/*
 * (C) Copyright 2014-2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Maxime Hilaire
 *     Florent Guillaume
 */
package org.nuxeo.ecm.directory.core;

import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.directory.Directory;
import org.nuxeo.ecm.directory.Session;
import org.nuxeo.ecm.platform.login.test.ClientLoginFeature;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@RunWith(FeaturesRunner.class)
@Features(CoreDirectoryFeature.class)
public class TestCoreDirectoryUserNotAllowed {

    @Inject
    @Named(value = CoreDirectoryFeature.CORE_DIRECTORY_NAME)
    protected Directory coreDir;

    protected Session dirNotAllowedSession = null;

    @Inject
    ClientLoginFeature login;

    @Before
    public void setUp() throws Exception {
        login.login(CoreDirectoryFeature.USER3_NAME);
        dirNotAllowedSession = coreDir.getSession();
    }

    @After
    public void tearDown() throws Exception {
        try {
            dirNotAllowedSession.close();
        } finally {
            login.logout();
        }
    }

    @Test
    public void testGetEntry() throws Exception {
        DocumentModel entry;
        entry = dirNotAllowedSession.getEntry(CoreDirectoryInit.DOC_ID_USER2);
        assertNull(entry);
        entry = dirNotAllowedSession.getEntry(CoreDirectoryInit.DOC_ID_USER1);
        assertNull(entry);

    }

}
