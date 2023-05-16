package ai.openfabric.api.controller;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.payload.CreateWorkerRequest;
import ai.openfabric.api.payload.WorkerInformationResponse;
import ai.openfabric.api.payload.WorkerListResponse;
import ai.openfabric.api.payload.WorkerStatResponse;
import ai.openfabric.api.service.WorkerService;
import ai.openfabric.api.utils.Constants;
import ai.openfabric.api.utils.LocalDateTimeDeserializer;
import ai.openfabric.api.utils.Result;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.ContainerConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("${node.api.path}/worker")
public class WorkerController {
    private WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public WorkerListResponse getPaginatedWorkers(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return workerService.getPaginatedWorkers(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public Optional<Worker> getWorker(@PathVariable String id) {
        return workerService.getWorker(id);
    }

    @GetMapping("/{containerId}/info")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public ResponseEntity<WorkerInformationResponse> getWorkerInformation(@PathVariable String containerId) {
        Result result = workerService.getWorkerInformation(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>((WorkerInformationResponse) result.getData(), HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()), result.getMessage());
    }

    @GetMapping("/{containerId}/stats")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    public ResponseEntity<WorkerStatResponse> getWorkerStats(@PathVariable String containerId) {
        Result result = workerService.getWorkerStats(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>((WorkerStatResponse) result.getData(), HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()), result.getMessage());
    }

    @GetMapping("/{containerId}/start")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Worker started successfully", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "304", description = "Worker already started", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity startWorker(@PathVariable String containerId) {
        Result result = workerService.startWorker(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()), result.getMessage());
    }

    @GetMapping("/{containerId}/stop")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Worker stopped successfully", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "304", description = "Worker already stopped", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity stopWorker(@PathVariable String containerId) {
        Result result = workerService.stopWorker(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()), result.getMessage());
    }

    @PostMapping("/create")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Something went wrong", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "409",  description = "Conflict, Something went wrong", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createWorker(@RequestBody CreateWorkerRequest payload) {
        Result result = workerService.createWorker(payload);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()), result.getMessage());
    }

    @DeleteMapping("/{id}/delete")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Worker deleted successfully", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400",  description = "Something went wrong", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "409",  description = "Cant remove a running container", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteWorker(@PathVariable String id) {
        System.out.println("delete worker");
        Result result = workerService.deleteWorker(id);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

}
