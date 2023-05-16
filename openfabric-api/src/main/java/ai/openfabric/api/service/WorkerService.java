package ai.openfabric.api.service;

import ai.openfabric.api.model.Worker;
import ai.openfabric.api.payload.CreateWorkerRequest;
import ai.openfabric.api.payload.WorkerCreateResponse;
import ai.openfabric.api.utils.Result;
import ai.openfabric.api.repository.WorkerRepository;
import ai.openfabric.api.payload.WorkerListResponse;
import ai.openfabric.api.utils.Constants;
import com.google.gson.Gson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final DockerManager dockerManager;


    public WorkerService(WorkerRepository workerRepository, DockerManager dockerManager) {
        this.workerRepository = workerRepository;
        this.dockerManager = dockerManager;
    }

    /**
    * Get paginated workers
    * @param pageNo page number
    * @param pageSize page size
    * @param sortBy sort by
    * @param sortDir sort direction
    * @return WorkerListResponse
     */
    public WorkerListResponse getPaginatedWorkers(
            @RequestParam(value = "pageNo", defaultValue = Constants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = Constants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = Constants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Worker> workers = workerRepository.findAll(pageable);
        List<Worker> workersList = workers.getContent();

        WorkerListResponse workerListResponse = new WorkerListResponse();
        workerListResponse.setWorkers(workersList);
        workerListResponse.setPageNo(workers.getNumber());
        workerListResponse.setPageSize(workers.getSize());
        workerListResponse.setTotalElements(workers.getTotalElements());
        workerListResponse.setTotalPages(workers.getTotalPages());
        workerListResponse.setLast(workers.isLast());

        return workerListResponse;
    }

    /**
     * Get worker by id
     *
     * @param id worker id
     * @return Worker
     */
    public Optional<Worker> getWorker(String id) {
        return workerRepository.findById(id);
    }

    /**
     * Get worker information
     * @param containerId worker container id
     * @return WorkerInformationResponse
     */
    public Result getWorkerInformation(String containerId) {
        return dockerManager.getContainerInfo(containerId);
    }

    /**
     * Get worker stats
     * @param containerId worker container id
     * @return WorkerStatsResponse
     */
    public Result getWorkerStats(String containerId) {
        return dockerManager.getContainerStatistics(containerId);
    }


    /**
     * Delete worker by id
     * @param id worker id
     */
    public Result deleteWorker(String id) {
        System.out.println("test");
        Worker worker = workerRepository.findById(id).orElse(null);
        if(worker == null) {
            return Result.failure(404, "Worker not found");
        }
        System.out.println("test");

        String containerId = worker.getContainerId();
        System.out.println("test");
        Result result = dockerManager.removeContainer(containerId);
        System.out.println("test");

        if(result.isSuccessful()){
            System.out.println("test");
            workerRepository.deleteById(id);
            System.out.println("test");
        }
        return result;
    }

    /**
     * Create worker
     * @param payload container configuration
     * @return Worker
     */
    public Result createWorker(CreateWorkerRequest payload)  {
        InputStream inputStream;
        try{
            Gson gson = new Gson();
            String config = gson.toJson(payload.toJson()).toString();
            inputStream = new ByteArrayInputStream(config.getBytes());
        }
        catch (Exception e){
            return Result.failure(500, "Error creating worker");
        }

        Result result = dockerManager.createContainer(payload.getName(), inputStream);

        if(result.isSuccessful()){
            Worker worker = new Worker();
            String containerId = ((WorkerCreateResponse)result.getData()).getId();
            worker.setContainerId(containerId);
            worker.setName(payload.getName());
            worker.setImage(payload.getImageName());
            worker.setExposedPorts(payload.getExposedPorts());
            workerRepository.save(worker);
        }

        return result;
    }

    /**
     * Stop worker
     *
     * @param id
     * @return Worker
     */
    public Result stopWorker(String containerId) {
        return dockerManager.stopContainer(containerId);
    }

    /**
     * Start worker
     * @param containerId
     * @return Worker
     */
    public Result startWorker(String containerId) {
        return dockerManager.startContainer(containerId);
    }


}
