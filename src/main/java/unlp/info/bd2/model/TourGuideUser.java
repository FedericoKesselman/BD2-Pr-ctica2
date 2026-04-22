package unlp.info.bd2.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TourGuideUser extends User {

	private String education;

    private List<Route> routes;
    
    public TourGuideUser(String username, String password, String name, String email, Date birthdate, 
    					String phoneNumber, String education) {
		super(username, password, name, email, birthdate, phoneNumber);
		this.education = education;
		this.routes = new LinkedList<>();
	}

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

}
