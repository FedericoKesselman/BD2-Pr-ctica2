package unlp.info.bd2.model;


import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class DriverUser extends User {

    private String expedient;
    private List<Route> routes;
    
    public DriverUser(String username, String password, String name, String email, java.util.Date birthdate, 
			String phoneNumber, String expedient) {
    		super(username, password, name, email, birthdate, phoneNumber);
    		this.expedient = expedient;
    		this.routes = new LinkedList<>();
    }

    public String getExpedient() {
        return expedient;
    }

    public void setExpedient(String expedient) {
        this.expedient = expedient;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRouts(List<Route> routs) {
        this.routes = routs;
    }
}
