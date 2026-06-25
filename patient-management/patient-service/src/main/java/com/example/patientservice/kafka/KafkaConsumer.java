package com.example.patientservice.kafka;

import com.example.proto.PatientProto.PatientEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "patient-events", groupId = "analytics-group")
    public void consume(byte[] message) {
        try {
            // byte[] → Protobuf object
            PatientEvent event = PatientEvent.parseFrom(message);

            System.out.println("📨 Received Patient Event:");
            System.out.println("   ID    : " + event.getPatientId());
            System.out.println("   Name  : " + event.getName());
            System.out.println("   Email : " + event.getEmail());
            System.out.println("   Type  : " + event.getEventType());

            // Add your analytics logic here
            processEvent(event);

        } catch (Exception e) {
            System.err.println(" Failed to deserialize: " + e.getMessage());
        }
    }

    private void processEvent(PatientEvent event) {
        // e.g. store in DB, compute stats, trigger alerts
        System.out.println("📊 Processing analytics for: " + event.getName());
    }
}
