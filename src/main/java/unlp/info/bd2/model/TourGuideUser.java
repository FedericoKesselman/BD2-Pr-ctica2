package unlp.info.bd2.model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("TOUR_GUIDE")
public class TourGuideUser extends User {

    @Column
    private String education;

    @ManyToMany(mappedBy = "tourGuideList") 
    private List<Route> routes = new ArrayList<>();
    
    public TourGuideUser(String username, String password, String name, String email, java.util.Date birthdate, 
    		String phoneNumber, String education) {
    super(username, password, name, email, birthdate, phoneNumber);
    this.education = education;
    this.routes = new LinkedList<>();
    }
    
    public TourGuideUser() {}


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