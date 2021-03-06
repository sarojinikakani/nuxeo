/*
 * (C) Copyright 2006-2010 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     bstefanescu
 */
package org.nuxeo.runtime.model.impl;

import java.util.Set;

import org.nuxeo.runtime.model.ComponentName;
import org.nuxeo.runtime.model.RegistrationInfo;

/**
 * Deactivate components in the proper order to avoid exceptions at shutdown.
 * 
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class ShutdownTask {

    final static void shutdown(ComponentManagerImpl mgr) {
        RegistrationInfoImpl[] ris = mgr.reg.getComponentsArray();
        for (RegistrationInfoImpl ri : ris) {
            shutdown(mgr, ri);
        }
    }

    private static void shutdown(ComponentManagerImpl mgr, RegistrationInfoImpl ri) {
        ComponentName name = ri.getName();
        if (name == null) {
            return; // already destroyed
        }
        if (ri.getState() <= RegistrationInfo.RESOLVED) {
            // not yet activated so we can destroy it right now
            mgr.unregister(name);
            return;
        }
        // an active component - get the components depending on it
        Set<ComponentName> reqs = mgr.reg.requirements.get(name);
        if (reqs != null && !reqs.isEmpty()) {
            // there are some components depending on me - cannot shutdown
            for (ComponentName req : reqs.toArray(new ComponentName[reqs.size()])) {
                RegistrationInfoImpl parentRi = mgr.reg.components.get(req);
                if (parentRi != null) {
                    shutdown(mgr, parentRi);
                }
            }
        }
        // no components are depending on me - shutdown now
        mgr.unregister(name);
    }

}
