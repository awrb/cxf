/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.cxf.jaxrs.swagger;

import org.apache.cxf.jaxrs.swagger.ui.OsgiSwaggerUiResolver;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiResolver;

import io.swagger.annotations.Api;

/**
 * SwaggerUI resolvers implementation for Swagger 1.5.x
 */
public final class SwaggerUi {
    private static final SwaggerUiResolver HELPER;
    
    static {
        SwaggerUiResolver theHelper;
        try {
            theHelper = new OsgiSwaggerUiResolver(Api.class);
        } catch (Throwable ex) {
            theHelper = new SwaggerUiResolver(AbstractSwaggerFeature.class.getClassLoader());
        }
        HELPER = theHelper;
    }

    private SwaggerUi() {
    }

    public static String findSwaggerUiRoot(String swaggerUiMavenGroupAndArtifact, 
                                           String swaggerUiVersion) {
        String root = HELPER.findSwaggerUiRootInternal(swaggerUiMavenGroupAndArtifact, 
                                                       swaggerUiVersion);
        if (root == null && HELPER.getClass() != SwaggerUiResolver.class) {
            root = new SwaggerUiResolver(AbstractSwaggerFeature.class.getClassLoader())
                .findSwaggerUiRootInternal(swaggerUiMavenGroupAndArtifact, swaggerUiVersion);
        }
        return root;
    }
}
