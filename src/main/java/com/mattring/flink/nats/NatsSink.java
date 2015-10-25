/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mattring.flink.nats;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.nats.Connection;

/**
 *
 * @author Matthew Ring
 */
public class NatsSink extends RichSinkFunction<String> {
    
    private static final long serialVersionUID = 1L;
    
    private final NatsConfig natsConfig;
    private Connection natsConnection;

    public NatsSink(NatsConfig natsConfig) {
        this.natsConfig = natsConfig;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        natsConnection = Connection.connect(natsConfig.getAsJava_NatsProperties());
    }

    @Override
    public void close() throws Exception {
        natsConnection.close(true);
        natsConnection = null;
    }

    @Override
    public void invoke(String in) throws Exception {
        natsConnection.publish(natsConfig.getTopic(), in);
    }
    
}
