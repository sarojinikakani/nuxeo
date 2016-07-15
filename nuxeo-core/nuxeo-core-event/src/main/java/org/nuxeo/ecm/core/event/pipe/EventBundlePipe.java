/*
 * (C) Copyright 2016 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     tiry
 */
package org.nuxeo.ecm.core.event.pipe;

import java.util.Map;

import org.nuxeo.ecm.core.event.EventBundle;
import org.nuxeo.ecm.core.event.EventService;

/**
 * Interface for a pipe of events. This is the abstraction used to bridge the Nuxeo events to pipes that consume them.
 *
 * @since 8.4
 */
public interface EventBundlePipe {

    /**
     * Initialize the Pipe when Nuxeo Event Service starts
     *
     * @param name the name as defined in the XMap descriptor
     * @param params the parameters as defined in the XMap descriptor
     */
    void initPipe(String name, Map<String, String> params);

    /**
     * Send an {@link EventBundle} inside the pipe
     *
     * @param events
     */
    void sendEventBundle(EventBundle events);

    /**
     * Wait until the end of event consumption
     *
     * @param timeoutMillis
     * @return
     * @throws InterruptedException
     */
    boolean waitForCompletion(long timeoutMillis) throws InterruptedException;

    /**
     * Shutdown callback when the {@link EventService} stops
     *
     * @throws InterruptedException
     */
    void shutdown() throws InterruptedException;
}
