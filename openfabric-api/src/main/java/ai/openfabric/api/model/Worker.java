package ai.openfabric.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "worker")
public class Worker extends Datable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "of-uuid")
    @GenericGenerator(name = "of-uuid", strategy = "ai.openfabric.api.model.IDGenerator")
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String containerId;


    @Getter
    @Setter
    private String image;

    @Getter
    @Setter
    private String name;


    @ElementCollection
    @CollectionTable(name = "worker_exposed_ports", joinColumns = @JoinColumn(name = "worker_id"))
    @Column(name = "port_number")
    @Getter
    @Setter
    private List<String> exposedPorts;
}
