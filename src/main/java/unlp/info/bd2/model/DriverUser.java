package unlp.info.bd2.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("DRIVER")
public class DriverUser extends User {
	
	public DriverUser() {
		
	}
	
	public DriverUser(String username, String password, String name, String email, java.util.Date birthdate, 
			String phoneNumber, String expedient) {
			super(username, password, name, email, birthdate, phoneNumber);
			this.expedient = expedient;
	}

    @Column
    private String expedient;

    @ManyToMany(mappedBy = "driverList") 
    private List<Route> routes = new ArrayList<>();

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}


