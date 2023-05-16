package ai.openfabric.api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateWorkerRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("image")
    String imageName;

    @JsonProperty("exposed_ports")
    Map<String, Object> exposedPorts;

    @JsonProperty("host_config")
    HostConfig hostConfig;

    public List<String> getExposedPorts() {
        return exposedPorts.keySet().stream().collect(Collectors.toList());
    }

    public Map<String, Object> toJson() {
        return Map.of(
                "image", imageName,
                "exposed_ports", exposedPorts,
                "host_config", hostConfig
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class HostConfig {
    @JsonProperty("port_bindings")
    Map<String, Object> portBindings;

}


