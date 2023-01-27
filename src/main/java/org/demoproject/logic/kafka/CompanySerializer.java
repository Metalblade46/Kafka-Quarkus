package org.demoproject.logic.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.demoproject.dataaccess.Entities.Company;

public class CompanySerializer implements Serializer<Company> {
    private final ObjectMapper objectMapper= new ObjectMapper();
    @Override
    public byte[] serialize(String topic, Company company) {
        try {
            if (company == null) {
                System.out.println("Null value of Company received at serializing");
                return null;
            }
            System.out.println("Serializing Company...");
            return objectMapper.writeValueAsBytes(company);
        } catch (Exception e) {
            throw new SerializationException("Error encountered when serializing Company to bytes[]");
        }

    }
}
