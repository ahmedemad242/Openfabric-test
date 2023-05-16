package ai.openfabric.api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerStatResponse {
    @JsonProperty("read")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.n]'Z'")
    private LocalDateTime read;

    @JsonProperty("pids_stats")
    private PidsStats pidsStats;

    @JsonProperty("networks")
    private Map<String, NetworkStats> networks;

    @JsonProperty("memory_stats")
    private MemoryStats memoryStats;

    @JsonProperty("blkio_stats")
    private BlkioStats blkioStats;

    @JsonProperty("cpu_stats")
    private CpuStats cpuStats;

    @JsonProperty("precpu_stats")
    private PreCpuStats preCpuStats;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PidsStats {
    @JsonProperty("current")
    private int current;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class NetworkStats {
    @JsonProperty("rx_bytes")
    private long rxBytes;

    @JsonProperty("rx_dropped")
    private long rxDropped;

    @JsonProperty("rx_errors")
    private long rxErrors;

    @JsonProperty("rx_packets")
    private long rxPackets;

    @JsonProperty("tx_bytes")
    private long txBytes;

    @JsonProperty("tx_dropped")
    private long txDropped;

    @JsonProperty("tx_errors")
    private long txErrors;

    @JsonProperty("tx_packets")
    private long txPackets;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class MemoryStats {
    @JsonProperty("stats")
    private MemoryStatsData stats;

    @JsonProperty("max_usage")
    private long maxUsage;

    @JsonProperty("usage")
    private long usage;

    @JsonProperty("failcnt")
    private long failcnt;

    @JsonProperty("limit")
    private long limit;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class MemoryStatsData {
    @JsonProperty("total_pgmajfault")
    private long totalPgmajfault;

    @JsonProperty("cache")
    private long cache;

    @JsonProperty("mapped_file")
    private long mappedFile;

    @JsonProperty("total_inactive_file")
    private long totalInactiveFile;

    @JsonProperty("pgpgout")
    private long pgpgout;

    @JsonProperty("rss")
    private long rss;

    @JsonProperty("total_mapped_file")
    private long totalMappedFile;

    @JsonProperty("writeback")
    private long writeback;

    @JsonProperty("unevictable")
    private long unevictable;

    @JsonProperty("pgpgin")
    private long pgpgin;

    @JsonProperty("total_unevictable")
    private long totalUnevictable;

    @JsonProperty("pgmajfault")
    private long pgmajfault;

    @JsonProperty("total_rss")
    private long totalRss;

    @JsonProperty("total_rss_huge")
    private long totalRssHuge;

    @JsonProperty("total_writeback")
    private long totalWriteback;

    @JsonProperty("total_inactive_anon")
    private long totalInactiveAnon;

    @JsonProperty("rss_huge")
    private long rssHuge;

    @JsonProperty("hierarchical_memory_limit")
    private long hierarchicalMemoryLimit;

    @JsonProperty("total_pgfault")
    private long totalPgfault;

    @JsonProperty("total_active_file")
    private long totalActiveFile;

    @JsonProperty("active_anon")
    private long activeAnon;

    @JsonProperty("total_active_anon")
    private long totalActiveAnon;

    @JsonProperty("total_pgpgout")
    private long totalPgpgout;

    @JsonProperty("total_cache")
    private long totalCache;

    @JsonProperty("inactive_anon")
    private long inactiveAnon;

    @JsonProperty("active_file")
    private long activeFile;

    @JsonProperty("pgfault")
    private long pgfault;

    @JsonProperty("inactive_file")
    private long inactiveFile;

    @JsonProperty("total_pgpgin")
    private long totalPgpgin;

}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class BlkioStats {
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class CpuStats {
    private CpuUsage cpuUsage;
    private long systemCpuUsage;
    private int onlineCpus;
    private ThrottlingData throttlingData;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class CpuUsage {
    @JsonProperty("percpu_usage")
    private long[] percpuUsage;

    @JsonProperty("usage_in_usermode")
    private long usageInUsermode;

    @JsonProperty("total_usage")
    private long totalUsage;

    @JsonProperty("usage_in_kernelmode")
    private long usageInKernelmode;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class PreCpuStats {
    @JsonProperty("cpu_usage")
    private CpuUsage cpuUsage;

    @JsonProperty("system_cpu_usage")
    private long systemCpuUsage;

    @JsonProperty("online_cpus")
    private int onlineCpus;

    @JsonProperty("throttling_data")
    private ThrottlingData throttlingData;
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class ThrottlingData {
    private long periods;

    @JsonProperty("throttled_periods")
    private long throttledPeriods;

    @JsonProperty("throttled_time")
    private long throttledTime;
}