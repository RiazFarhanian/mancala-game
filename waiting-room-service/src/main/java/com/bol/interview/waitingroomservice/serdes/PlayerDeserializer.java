package com.bol.interview.waitingroomservice.serdes;

import com.bol.interview.common.dto.PlayerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PlayerDeserializer implements Deserializer<PlayerDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PlayerDto deserialize(String topic, byte[] bytes) {
        try {
            if (bytes == null){
                return null;
            }
            return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), PlayerDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to " + PlayerDto.class.getName());
        }

    }

    @Override
    public PlayerDto deserialize(String topic, Headers headers, byte[] data) {
        return this.deserialize(topic, data);
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }
}
