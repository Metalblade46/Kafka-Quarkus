package org.demoproject.logic.kafka;

import io.smallrye.reactive.messaging.kafka.Record;
import org.demoproject.dataaccess.Entities.Company;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompanyConsumer {
    private static final Logger logger = LoggerFactory.getLogger(CompanyProducer.class);

    @Incoming("company-in")
    public Company recieveKafkaCompany(Record<Long,Company> record) {
        logger.info("Received Company " + record.value().getName() + " Successfully!!");
        Company value = record.value();
        return value;
    }
}
