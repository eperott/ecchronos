/*
 * Copyright 2018 Telefonaktiebolaget LM Ericsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericsson.bss.cassandra.ecchronos.application;

import com.ericsson.bss.cassandra.ecchronos.connection.JmxConnectionProvider;
import com.ericsson.bss.cassandra.ecchronos.connection.impl.LocalJmxConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.Properties;

public class DefaultJmxConnectionProvider implements JmxConnectionProvider
{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultJmxConnectionProvider.class);

    private final LocalJmxConnectionProvider myLocalJmxConnectionProvider;

    public DefaultJmxConnectionProvider(Properties properties) throws IOException
    {
        DefaultJmxConnectionProperties connectionProperties = DefaultJmxConnectionProperties.from(properties);
        LOG.info("Connecting through JMX using {}", connectionProperties);

        myLocalJmxConnectionProvider = new LocalJmxConnectionProvider(connectionProperties.getJmxHost(), connectionProperties.getJmxPort());
    }

    @Override
    public JMXConnector getJmxConnector() throws IOException
    {
        return myLocalJmxConnectionProvider.getJmxConnector();
    }

    @Override
    public void close() throws IOException
    {
        myLocalJmxConnectionProvider.close();
    }
}
