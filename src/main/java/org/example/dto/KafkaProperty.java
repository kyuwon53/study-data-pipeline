package org.example.dto;

public class KafkaProperty {
    private String topic = "default";
    private String consumeServerId = "localhost:9092";
    private String produceServerId = "localhost:9092";
    private String consumeGroupId = "default-consume-group";
    private String produceGroupId = "default-produce-group";

    public String topic() {
        return topic;
    }

    public String consumeServerId() {
        return consumeServerId;
    }

    public String produceServerId() {
        return produceServerId;
    }

    public String consumeGroupId() {
        return consumeGroupId;
    }

    public String produceGroupId() {
        return produceGroupId;
    }

    public void topic(String topic) {
        this.topic = topic;
    }

    public void consumeServerId(String consumeServerId) {
        if (consumeServerId == null || consumeServerId.isBlank()) {
            return;
        }
        this.consumeServerId = consumeServerId;
    }

    public void produceServerId(String produceServerId) {
        if (produceServerId == null || produceServerId.isBlank()) {
            return;
        }
        this.produceServerId = produceServerId;
    }

    public void consumeGroupId(String consumeGroupId) {
        if (consumeGroupId == null || consumeGroupId.isBlank()) {
            return;
        }
        this.consumeGroupId = consumeGroupId;
    }

    public void produceGroupId(String produceGroupId) {
        if (produceGroupId == null || produceGroupId.isBlank()) {
            return;
        }
        this.produceGroupId = produceGroupId;
    }
}
