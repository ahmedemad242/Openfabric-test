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
    ){
        return workerService.getPaginatedWorkers(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public Optional<Worker> getWorker(@PathVariable String id) {
        return workerService.getWorker(id);
    }

    @GetMapping("/{containerId}/info")
    public ResponseEntity<WorkerInformationResponse> getWorkerInformation(@PathVariable String containerId) {
        Result result = workerService.getWorkerInformation(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>((WorkerInformationResponse) result.getData(), HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

    @GetMapping("/{containerId}/stats")
    public ResponseEntity<WorkerStatResponse> getWorkerStats(@PathVariable String containerId) {
        Result result = workerService.getWorkerStats(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>((WorkerStatResponse) result.getData(), HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

    @GetMapping("/{containerId}/start")
    public ResponseEntity startWorker(@PathVariable String containerId) {
        Result result = workerService.startWorker(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

    @GetMapping("/{containerId}/stop")
    public ResponseEntity stopWorker(@PathVariable String containerId) {
        Result result = workerService.stopWorker(containerId);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

    @PostMapping("/create")
    public ResponseEntity createWorker(@RequestBody CreateWorkerRequest payload){
        Result result = workerService.createWorker(payload);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteWorker(@PathVariable String id) {
        System.out.println("delete worker");
        Result result = workerService.deleteWorker(id);
        if (result.isSuccessful()) {
            return new ResponseEntity<>(HttpStatus.valueOf(result.getStatusCode()));
        }
        throw new ResponseStatusException(HttpStatus.valueOf(result.getStatusCode()),result.getMessage());
    }

}
