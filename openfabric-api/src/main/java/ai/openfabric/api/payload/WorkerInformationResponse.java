package ai.openfabric.api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerInformationResponse {

        @JsonProperty("AppArmorProfile")
        private String appArmorProfile;

        @JsonProperty("Args")
        private List<String> args;

        @JsonProperty("Config")
        private DockerConfig config;

        @JsonProperty("Created")
        private String created;

        @JsonProperty("Driver")
        private String driver;

        @JsonProperty("ExecIDs")
        private List<String> execIDs;

        @JsonProperty("HostConfig")
        private DockerHostConfig hostConfig;

        @JsonProperty("HostnamePath")
        private String hostnamePath;

        @JsonProperty("HostsPath")
        private String hostsPath;

        @JsonProperty("LogPath")
        private String logPath;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("Image")
        private String image;

        @JsonProperty("MountLabel")
        private String mountLabel;

        @JsonProperty("Name")
        private String name;

        @JsonProperty("NetworkSettings")
        private DockerNetworkSettings networkSettings;

        @JsonProperty("Path")
        private String path;

        @JsonProperty("ProcessLabel")
        private String processLabel;

        @JsonProperty("ResolvConfPath")
        private String resolvConfPath;

        @JsonProperty("RestartCount")
        private int restartCount;

        @JsonProperty("State")
        private DockerState state;

        @JsonProperty("Mounts")
        private List<DockerMount> mounts;
}
