/**
 * DockerManager.java
 * Encapsulate communication with Docker API
 * @author Ahmed Emad
 * @version 1.0
 */

package ai.openfabric.api.service;

import ai.openfabric.api.payload.Message;
import ai.openfabric.api.payload.WorkerCreateResponse;
import ai.openfabric.api.payload.WorkerInformationResponse;
import ai.openfabric.api.payload.WorkerStatResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient.Request;
import com.github.dockerjava.transport.DockerHttpClient.Response;
import ai.openfabric.api.utils.JsonParser;
import ai.openfabric.api.utils.Result;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

@Service
public class DockerManager {
    private final DockerHttpClient dockerHttpClient;

    public DockerManager() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
        dockerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(5))
                .responseTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * Get container statistics
     * @param containerId container id
     * @return Result object
     */
    public Result getContainerStatistics(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.GET)
                .path("/containers/" + containerId + "/stats?stream=0")
                .build();

        return requestToResult(request, WorkerStatResponse.class);
    }

    /**
     * Get container information
     * @param containerId container id
     * @return Result object
     */
        public Result getContainerInfo(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.GET)
                .path("/containers/" + containerId + "/json")
                .build();

        return requestToResult(request, WorkerInformationResponse.class);
    }

    /**
     * Start a container
     * @param containerId container id
     * @return Result object
     */
    public Result startContainer(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.POST)
                .path("/containers/" + containerId + "/start")
                .build();

        return requestToResult(request, null);
    }

    /**
     * Stop a container
     * @param containerId container id
     * @return Result object
     */
    public Result stopContainer(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.POST)
                .path("/containers/" + containerId + "/stop")
                .build();

        return requestToResult(request, null);
    }

    /**
     * Remove a container
     * @param containerId container id
     * @return Result object
     */
    public Result removeContainer(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.DELETE)
                .path("/containers/" + containerId)
                .build();

        return requestToResult(request, null);
    }

    /**
     * Create a container
     * @param requestBody requestBody
     * @return Result object
     */
    public Result createContainer(String containerName, InputStream requestBody) {
        Map<String, String> headers = Map.of("Content-Type", "application/json");
        Request request = Request.builder()
                .method(Request.Method.POST)
                .path("/containers/create?name="+containerName)
                .headers(headers)
                .body(requestBody)
                .build();



        return requestToResult(request, WorkerCreateResponse.class);
    }


    /**
     * Transform a request to a Result object by executing the request and parsing the response.
     * Response body is parsed to a JsonObject
     * @see Result
     * @param request request object
     * @return Result object
     */
    private Result requestToResult(Request request, Class type){
        try {
            Response response = dockerHttpClient.execute(request);

            if (response.getStatusCode() < 400) {
                Object responseBody = JsonParser.parse(response.getBody(), type);
                return Result.success(response.getStatusCode(), responseBody);
            } else {
                Object responseBody = JsonParser.parse(response.getBody(), Message.class);;
                return Result.failure(response.getStatusCode(), ((Message) responseBody).getMessage());
            }

        } catch (Exception e) {
            return Result.failure(500, e.getMessage());
        }
    }


}
