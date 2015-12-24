/*
 * Copyright 2015 Open mHealth
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
 */

package org.openmhealth.schema.service;

import org.openmhealth.schema.domain.omh.SchemaId;
import org.openmhealth.schema.domain.omh.SchemaSupport;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;


/**
 * A schema identifier service that uses reflection at load-time to determine which schema classes are available on the
 * classpath.
 *
 * @author Emerson Farrugia
 */
@Service
public class SchemaClassServiceImpl implements SchemaClassService {

    public static final String SCHEMA_CLASS_PACKAGE = "org.openmhealth.schema.domain";

    private Set<SchemaId> schemaIds = new HashSet<>();


    @PostConstruct
    public void initializeSchemaIds()
            throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        Reflections reflections = new Reflections(SCHEMA_CLASS_PACKAGE);

        for (Class<? extends SchemaSupport> schemaClass : reflections.getSubTypesOf(SchemaSupport.class)) {

            if (schemaClass.isInterface() || Modifier.isAbstract(schemaClass.getModifiers())) {
                continue;
            }

            SchemaId schemaId;

            if (schemaClass.isEnum()) {
                schemaId = schemaClass.getEnumConstants()[0].getSchemaId();
            }
            else {
                Constructor<? extends SchemaSupport> constructor = schemaClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                schemaId = constructor.newInstance().getSchemaId();
            }

            schemaIds.add(schemaId);
        }
    }

    @Override
    public Set<SchemaId> getSchemaIds() {
        return this.schemaIds;
    }
}
