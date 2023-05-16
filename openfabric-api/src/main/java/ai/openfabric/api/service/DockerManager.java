/**
 * DockerManager.java
 * Encapsulate communication with Docker API
 * @author Ahmed Emad
 * @version 1.0
 */

package ai.openfabric.api.service;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient.Request;
import com.github.dockerjava.transport.DockerHttpClient.Response;
import ai.openfabric.api.utils.JsonParser;
import ai.openfabric.api.utils.Result;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

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
     * Get container statistics serves the route /containers/{containerId}/stats
     * @param containerId container id
     * @return Result object
     */
    public Result getContainerStatistics(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.GET)
                .path("/containers/" + containerId + "/stats?stream=0")
                .build();

        return requestToResult(request);
    }

    /**
     * Get container information serves the route /containers/{containerId}/info
     * @param containerId container id
     * @return Result object
     */
    public Result getContainerInfo(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.GET)
                .path("/containers/" + containerId + "/json")
                .build();

        return requestToResult(request);
    }

    /**
     * Start a container serves the route /containers/{containerId}/start
     * @param containerId container id
     * @return Result object
     */
    public Result startContainer(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.POST)
                .path("/containers/" + containerId + "/start")
                .build();

        return requestToResult(request);
    }

    /**
     * Stop a container serves the route /containers/{containerId}/stop
     * @param containerId container id
     * @return Result object
     */
    public Result stopContainer(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.POST)
                .path("/containers/" + containerId + "/stop")
                .build();

        return requestToResult(request);
    }

    /**
     * Remove a container serves the route /containers/{containerId}
     * @param containerId container id
     * @return Result object
     */
    public Result removeContainer(String containerId) {
        Request request = Request.builder()
                .method(Request.Method.DELETE)
                .path("/containers/" + containerId)
                .build();

        return requestToResult(request);
    }

    /**
     * Create a container serves the route /containers/create
     * @param requestBody requestBody
     * @return Result object
     */
    public Result createContainer(InputStream requestBody) {
        Map<String, String> headers = Map.of("Content-Type", "application/json");
        Request request = Request.builder()
                .method(Request.Method.POST)
                .path("/containers/create")
                .headers(headers)
                .body(requestBody)
                .build();

        return requestToResult(request);
    }


    /**
     * Transform a request to a Result object by executing the request and parsing the response.
     * Response body is parsed to a JsonObject
     * @see Result
     * @param request request object
     * @return Result object
     */
    private Result requestToResult(Request request){
        try {
            Response response = dockerHttpClient.execute(request);
            JsonObject responseBody = JsonParser.parse(response.getBody());
            if (response.getStatusCode() < 400) {
                return Result.success(response.getStatusCode(), responseBody);
            } else {
                return Result.failure(response.getStatusCode(), responseBody.get("message").getAsString());
            }

        } catch (Exception e) {
            return Result.failure(500, e.getMessage());
        }
    }
}
