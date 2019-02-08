package ro.utm.jc.model.server;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "servers")
public class Server implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String address;
    private String environment;
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean availableFlag;

    public Server() {
    }


    public Server(String name, String address, String environment, Boolean availableFlag) {
        this.name = name;
        this.address = address;
        this.environment = environment;
        this.availableFlag = availableFlag;
    }
}
