package ai.openfabric.api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateWorkerRequest{
    @JsonProperty("app_armor_profile")
    private String appArmorProfile;

    @JsonProperty("args")
    private List<String> args;

    @JsonProperty("config")
    private DockerConfig config;

    @JsonProperty("created")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.n]'Z'")
    private LocalDateTime created;

    @JsonProperty("driver")
    private String driver;

    @JsonProperty("exec_ids")
    private List<String> execIDs;

    @JsonProperty("host_config")
    private DockerHostConfig hostConfig;

    @JsonProperty("hostname_path")
    private String hostnamePath;

    @JsonProperty("hosts_path")
    private String hostsPath;

    @JsonProperty("log_path")
    private String logPath;

    @JsonProperty("id")
    private String id;

    @JsonProperty("image")
    private String image;

    @JsonProperty("mount_label")
    private String mountLabel;

    @JsonProperty("name")
    private String name;

    @JsonProperty("network_settings")
    private DockerNetworkSettings networkSettings;

    @JsonProperty("path")
    private String path;

    @JsonProperty("process_label")
    private String processLabel;

    @JsonProperty("resolv_conf_path")
    private String resolvConfPath;

    @JsonProperty("restart_count")
    private int restartCount;

    @JsonProperty("state")
    private DockerState state;

    @JsonProperty("mounts")
    private List<DockerMount> mounts;

}


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerConfig {
    @JsonProperty("attach_stderr")
    private boolean attachStderr;

    @JsonProperty("attach_stdin")
    private boolean attachStdin;

    @JsonProperty("attach_stdout")
    private boolean attachStdout;

    @JsonProperty("cmd")
    private List<String> cmd;

    @JsonProperty("domainname")
    private String domainname;

    @JsonProperty("env")
    private List<String> env;

    @JsonProperty("healthcheck")
    private DockerHealthcheck healthcheck;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("image")
    private String image;

    @JsonProperty("labels")
    private Map<String, String> labels;

    @JsonProperty("mac_address")
    private String macAddress;

    @JsonProperty("network_disabled")
    private boolean networkDisabled;

    @JsonProperty("open_stdin")
    private boolean openStdin;

    @JsonProperty("stdin_once")
    private boolean stdinOnce;

    @JsonProperty("tty")
    private boolean tty;

    @JsonProperty("user")
    private String user;

    @JsonProperty("volumes")
    private Map<String, Object> volumes;

    @JsonProperty("working_dir")
    private String workingDir;

    @JsonProperty("stop_signal")
    private String stopSignal;

    @JsonProperty("stop_timeout")
    private int stopTimeout;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerHealthcheck {
    @JsonProperty("test")
    private List<String> test;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerHostConfig {
    @JsonProperty("maximum_iops")
    private int maximumIOps;

    @JsonProperty("maximum_iobps")
    private int maximumIOBps;

    @JsonProperty("blkio_weight")
    private int blkioWeight;

    @JsonProperty("blkio_weight_device")
    private List<Object> blkioWeightDevice;

    @JsonProperty("blkio_device_read_bps")
    private List<Object> blkioDeviceReadBps;

    @JsonProperty("blkio_device_write_bps")
    private List<Object> blkioDeviceWriteBps;

    @JsonProperty("blkio_device_read_iops")
    private List<Object> blkioDeviceReadIOps;

    @JsonProperty("blkio_device_write_iops")
    private List<Object> blkioDeviceWriteIOps;

    @JsonProperty("container_id_file")
    private String containerIDFile;

    @JsonProperty("cpuset_cpus")
    private String cpusetCpus;

    @JsonProperty("cpuset_mems")
    private String cpusetMems;

    @JsonProperty("cpu_percent")
    private int cpuPercent;

    @JsonProperty("cpu_shares")
    private int cpuShares;

    @JsonProperty("cpu_period")
    private int cpuPeriod;

    @JsonProperty("cpu_realtime_period")
    private int cpuRealtimePeriod;

    @JsonProperty("cpu_realtime_runtime")
    private int cpuRealtimeRuntime;

    @JsonProperty("devices")
    private List<Object> devices;

    @JsonProperty("device_requests")
    private List<Object> deviceRequests;

    @JsonProperty("ipc_mode")
    private String ipcMode;

    @JsonProperty("memory")
    private long memory;

    @JsonProperty("memory_swap")
    private long memorySwap;

    @JsonProperty("memory_reservation")
    private long memoryReservation;

    @JsonProperty("oom_kill_disable")
    private boolean oomKillDisable;

    @JsonProperty("oom_score_adj")
    private int oomScoreAdj;

    @JsonProperty("network_mode")
    private String networkMode;

    @JsonProperty("pid_mode")
    private String pidMode;

    @JsonProperty("port_bindings")
    private Map<String, Object> portBindings;

    @JsonProperty("privileged")
    private boolean privileged;

    @JsonProperty("readonly_rootfs")
    private boolean readonlyRootfs;

    @JsonProperty("publish_all_ports")
    private boolean publishAllPorts;

    @JsonProperty("restart_policy")
    private DockerRestartPolicy restartPolicy;

    @JsonProperty("log_config")
    private DockerLogConfig logConfig;

    @JsonProperty("sysctls")
    private Map<String, String> sysctls;

    @JsonProperty("ulimits")
    private List<Object> ulimits;

    @JsonProperty("volume_driver")
    private String volumeDriver;

    @JsonProperty("shm_size")
    private long shmSize;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerRestartPolicy {
    @JsonProperty("maximum_retry_count")
    private int maximumRetryCount;

    @JsonProperty("name")
    private String name;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerLogConfig {
    @JsonProperty("type")
    private String type;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerState {
    @JsonProperty("error")
    private String error;

    @JsonProperty("exit_code")
    private int exitCode;

    @JsonProperty("finished_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.n]'Z'")
    private LocalDateTime finishedAt;

    @JsonProperty("health")
    private Map<String, Object> health;

    @JsonProperty("oom_killed")
    private boolean oomKilled;

    @JsonProperty("dead")
    private boolean dead;

    @JsonProperty("paused")
    private boolean paused;

    @JsonProperty("pid")
    private int pid;

    @JsonProperty("restarting")
    private boolean restarting;

    @JsonProperty("running")
    private boolean running;

    @JsonProperty("started_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.n]'Z'")
    private LocalDateTime startedAt;

    @JsonProperty("status")
    private String status;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerMount {
    @JsonProperty("name")
    private String name;

    @JsonProperty("source")
    private String source;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("driver")
    private String driver;

    @JsonProperty("mode")
    private String mode;

    @JsonProperty("rw")
    private boolean rw;

    @JsonProperty("propagation")
    private String propagation;
}


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerNetworkSettings {
    @JsonProperty("bridge")
    private String bridge;

    @JsonProperty("sandbox_id")
    private String sandboxID;

    @JsonProperty("hairpin_mode")
    private boolean hairpinMode;

    @JsonProperty("link_local_ipv6_address")
    private String linkLocalIPv6Address;

    @JsonProperty("link_local_ipv6_prefix_len")
    private int linkLocalIPv6PrefixLen;

    @JsonProperty("sandbox_key")
    private String sandboxKey;

    @JsonProperty("endpoint_id")
    private String endpointID;

    @JsonProperty("gateway")
    private String gateway;

    @JsonProperty("global_ipv6_address")
    private String globalIPv6Address;

    @JsonProperty("global_ipv6_prefix_len")
    private int globalIPv6PrefixLen;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("ip_prefix_len")
    private int ipPrefixLen;

    @JsonProperty("ipv6_gateway")
    private String ipv6Gateway;

    @JsonProperty("mac_address")
    private String macAddress;

    @JsonProperty("networks")
    private Map<String, DockerNetwork> networks;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class DockerNetwork {
    @JsonProperty("network_id")
    private String networkID;

    @JsonProperty("endpoint_id")
    private String endpointID;

    @JsonProperty("gateway")
    private String gateway;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("ip_prefix_len")
    private int ipPrefixLen;

    @JsonProperty("ipv6_gateway")
    private String ipv6Gateway;

    @JsonProperty("global_ipv6_address")
    private String globalIPv6Address;

    @JsonProperty("global_ipv6_prefix_len")
    private int globalIPv6PrefixLen;

    @JsonProperty("mac_address")
    private String macAddress;
}