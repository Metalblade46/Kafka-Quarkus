package org.demoproject.logic.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.demoproject.dataaccess.Entities.Company;

public class CompanyDeserializer implements Deserializer<Company> {
    private final ObjectMapper objectMapper= new ObjectMapper();
    @Override
    public Company deserialize(String topic, byte[] bytes) {
        try {
            if (bytes == null) {
                System.out.println("Null value of Company received while deserializing");
                return null;
            }
            System.out.println("Deserializing Company...");
            return objectMapper.readValue(new String(bytes, "UTF-8"), Company.class);
        } catch (Exception e) {
            throw new SerializationException("Error encountered when serializing Company to bytes[]");
        }
    }
}
