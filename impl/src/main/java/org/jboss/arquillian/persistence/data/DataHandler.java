/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.arquillian.persistence.data;

import org.jboss.arquillian.persistence.event.ApplyCleanupStatement;
import org.jboss.arquillian.persistence.event.ApplyInitStatement;
import org.jboss.arquillian.persistence.event.CleanupData;
import org.jboss.arquillian.persistence.event.CompareData;
import org.jboss.arquillian.persistence.event.ExecuteScripts;
import org.jboss.arquillian.persistence.event.PrepareData;

public interface DataHandler
{

   void initStatements(ApplyInitStatement applyInitStatementEvent);

   void cleanupStatements(ApplyCleanupStatement applyCleanupStatementEvent);

   void prepare(PrepareData prepareDataEvent);

   void compare(CompareData cleanupDataEvent);

   void cleanup(CleanupData cleanupDataEvent);

   void executeScripts(ExecuteScripts executeScriptsEvent);

}
