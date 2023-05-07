package ir.mahyco.p2p.config;

import lombok.Data;

@Data
public class SystemProperties {
    public String cbiUrl;
    public String hostAddress;
    public String urlService;

    //todo:check reading problem; why is null
    public String sslEnable;

    public String mainBootstrapServers;
    public String mainClientConsumerCount;
    //used for Specifies the maximum amount of time in milliseconds a consumer within a consumer group can be out of contact with a broker
    //before being considered inactive and a rebalancing is triggered between the active consumers in the group
    public String mainSessionTimeoutMs;
    //used for Specifies the interval in milliseconds between heartbeat checks to the consumer group coordinator to indicate that a consumer is active and connected.
    // The heartbeat interval must be lower, usually by a third, than the session timeout interval
    public String mainHeartbeatIntervalMs;
    //nack sleep time should be less than max poll interval ms
    public String mainNackSleepTime;
    public String mainMaxPollIntervalMs;

    public String bootstrapServers;
    public String sessionTimeoutMs;
    public String heartbeatIntervalMs;
    public String maxPollIntervalMs;
    public String attempts;
    public String backoffDelay;
    public String backoffMultiplier;

}
