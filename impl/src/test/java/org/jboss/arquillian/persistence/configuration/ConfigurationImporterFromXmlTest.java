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
package org.jboss.arquillian.persistence.configuration;

import static org.fest.assertions.Assertions.assertThat;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.configuration.PersistenceConfiguration;
import org.jboss.arquillian.persistence.data.descriptor.Format;
import org.junit.Test;

public class ConfigurationImporterFromXmlTest
{

   @Test
   public void shouldExtractDefaultDataSourceFromExternalConfigurationFile() throws Exception
   {
      // given
      String expectedDataSource = "Ike";
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptorFromDefaultConfigurationFile();

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDefaultDataSource()).isEqualTo(expectedDataSource);
   }

   @Test
   public void shouldExtractInitStatementFromExternalConfigurationFile() throws Exception
   {
      // given
      String expectedInitStatement = "SELECT * FROM ARQUILLIAN_TESTS";
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptorFromDefaultConfigurationFile();

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getInitStatement()).isEqualTo(expectedInitStatement);
   }

   @Test
   public void shouldExtractDefaultDataSetFormatDefinedInPropertyFile() throws Exception
   {
      // given
      Format expectedFormat = Format.EXCEL;
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptorFromDefaultConfigurationFile();

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDefaultDataSetFormat()).isEqualTo(expectedFormat);
   }

   @Test
   public void shouldUseXmlAsDefaultDataSetFormatWhenNotDefinedInConfiguration() throws Exception
   {
      // given
      Format expectedFormat = Format.XML;
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian-without-persistence-properties.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDefaultDataSetFormat()).isEqualTo(expectedFormat);
   }

   @Test
   public void shouldObtainDefaultTransactionMode() throws Exception
   {
      // given
      TransactionMode expectedMode = TransactionMode.ROLLBACK;
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDefaultTransactionMode()).isEqualTo(expectedMode);
   }

   @Test
   public void shouldHaveCommitAsDefaultTransactionModeIfNotDefinedInConfigurationFile() throws Exception
   {
      // given
      TransactionMode expectedMode = TransactionMode.COMMIT;
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian-without-persistence-properties.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDefaultTransactionMode()).isEqualTo(expectedMode);
   }

   @Test
   public void shouldBeAbleToTurnOnDatabaseDumps() throws Exception
   {
      // given
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.isDumpData()).isTrue();
   }

   @Test
   public void shouldHaveDatabaseDumpsDisabledByDefault() throws Exception
   {
      // given
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian-without-persistence-properties.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.isDumpData()).isFalse();
   }

   @Test
   public void shouldHaveSystemTempDirDefinedAsDefaultDumpDirectory() throws Exception
   {
      // given
      String systemTmpDir = System.getProperty("java.io.tmpdir");
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian-without-persistence-properties.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDumpDirectory()).isEqualTo(systemTmpDir);
   }
   
   @Test
   public void shouldHaveDeleteTestDatasetTablesOnlyDisabledByDefault() throws Exception
   {
      // given
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian-without-persistence-properties.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.isDeleteTestDatasetTablesOnly()).isFalse();
   }

   @Test
   public void shouldBeAbleToDefineDumpDirectory() throws Exception
   {
      // given
      String dumpDirectory = "/home/ike/dump";
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getDumpDirectory()).isEqualTo(dumpDirectory);
   }

   @Test
   public void shouldBeAbleToDefineUserTransactionJndi() throws Exception
   {
      // given
      String expectedUserTransactionJndi = "java:jboss/UserTransaction";
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getUserTransactionJndi()).isEqualTo(expectedUserTransactionJndi);
   }

   @Test
   public void shouldHaveDefaultUserTransactionJndi() throws Exception
   {
      // given
      String expectedUserTransactionJndi = "java:comp/UserTransaction";
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian-without-persistence-properties.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.getUserTransactionJndi()).isEqualTo(expectedUserTransactionJndi);
   }
   
   @Test
   public void shouldBeAbleToEnableDeleteTestDatasetTablesOnly() throws Exception
   {
      // given
      ArquillianDescriptor descriptor = TestConfigurationLoader.createArquillianDescriptor("arquillian.xml");

      // when
      PersistenceConfiguration configuration = new ConfigurationImporter().from(descriptor);

      // then
      assertThat(configuration.isDeleteTestDatasetTablesOnly()).isTrue();
   }
}
