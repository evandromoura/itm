/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.trixti.itm.infra.security.annotations;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


/**
 * The secured controller restricts access to certain methods
 * 
 * @author Shane Bryzak
 * 
 */
// Expose the bean to EL
@Named
public class Controller {

    @Inject
    private FacesContext facesContext;


    /**
     * Only users with the admin application role can invoke this method
     */
    @Admin
    public void adminMethod() {
        facesContext.addMessage(null, new FacesMessage("You executed an @Admin method"));
    }

}
