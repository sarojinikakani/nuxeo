/*
 * (C) Copyright 2006-2007 Nuxeo SAS <http://nuxeo.com> and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jean-Marc Orliaguet, Chalmers
 *
 * $Id$
 */

package org.nuxeo.theme.test.jsf;

import org.nuxeo.theme.models.AbstractModel;
import org.nuxeo.theme.nodes.NodeTypeFamily;

public class DummyMenuItem extends AbstractModel {

    private String title;

    private String description;

    private String url;

    private boolean selected;

    private String icon;

    public DummyMenuItem(String title, String description, String url,
            boolean selected, String icon) {
        super();
        this.title = title;
        this.description = description;
        this.url = url;
        this.selected = selected;
        this.icon = icon;
    }

    @Override
    public String getModelTypeName() {
        return "menu item";
    }

    @Override
    public NodeTypeFamily getNodeTypeFamily() {
        return getChildren().isEmpty() ? NodeTypeFamily.LEAF
                : NodeTypeFamily.INNER;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}