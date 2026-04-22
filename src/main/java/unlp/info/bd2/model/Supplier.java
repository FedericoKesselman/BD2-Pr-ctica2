package unlp.info.bd2.model;

import java.util.List;

public class Supplier {

    private Long id;

    private String businessName;

    private String authorizationNumber;

    private List<Serv> services;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public List<Serv> getServices() {
        return services;
    }

    public void setServices(List<Serv> services) {
        this.services = services;
    }

}
