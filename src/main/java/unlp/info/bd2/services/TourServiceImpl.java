package unlp.info.bd2.services;

import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TourServiceImpl implements ToursService {

    private UserRepository userRepo;
    private RouteRepository routeRepo;
    private PurchaseRepository purchaseRepo;
    private ServiceRepository serviceRepo;
    private SupplierRepository supplierRepo;
    private ReviewRepository reviewRepo;

    @Autowired
    public TourServiceImpl(UserRepository userRepo,
                         RouteRepository routeRepo,
                         PurchaseRepository purchaseRepo,
                         ServiceRepository serviceRepo,
                         SupplierRepository supplierRepo,
                         ReviewRepository reviewRepo) {
    this.userRepo = userRepo;
    this.routeRepo = routeRepo;
    this.purchaseRepo = purchaseRepo;
    this.serviceRepo = serviceRepo;
    this.supplierRepo = supplierRepo;
    this.reviewRepo = reviewRepo;
    }

    @Override
    @Transactional
    public User createUser(String username, String password, String name,
                           String email, Date birthdate, String phoneNumber) {
        User user = new User(username, password, name, email, birthdate, phoneNumber);
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public Purchase createPurchase(String code, Route route, User user) {
        Purchase purchase = new Purchase(code, route, user);
        return purchaseRepo.save(purchase);
    }

    @Override
    @Transactional
    public Serv updateServicePriceById(Long id, float newPrice) throws ToursException {
        Serv service = serviceRepo.findById(id).orElse(null);

        if (service == null) 
            throw new ToursException("Service not found");

        service.setPrice(newPrice);
        return service;
    }

    @Transactional
    public ItemService addItemToPurchase(Serv service, int quantity, Purchase purchase)  throws ToursException {

        Purchase managedPurchase = purchaseRepo.findById(purchase.getId()).orElse(null);
        Serv managedService = serviceRepo.findById(service.getId()).orElse(null);

        if (managedPurchase == null || managedService == null) 
            throw new ToursException("Purchase or Service not found");

        ItemService item = new ItemService(quantity, managedService, purchase);
        managedPurchase.addItem(item);

        return item;
    }

    // ---------- EJERCICIO 46 ----------

    // Inciso A
    @Override 
    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchasesOfUsername(String username) {
        return purchaseRepo.findByUserUsername(username);
    }

    // Inciso B
    @Override
    @Transactional(readOnly = true)
    public List<User> getUserSpendingMoreThan(float amount) {
        return userRepo.getUserSpendingMoreThan(amount);
    }

    // Inciso C
    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getTopNSuppliersInPurchases(int n) {
        Pageable pageable = PageRequest.of(0, n);
        return supplierRepo.getTopSuppliers(pageable);
    }

    // Inciso D
    @Override
    @Transactional(readOnly = true)
    public Long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return purchaseRepo.countByDateBetween(start, end);
    }

    // Inciso E
    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop) {
        return routeRepo.findyByStop(stop);
    }

    // Inciso F
    @Override
    @Transactional(readOnly = true)
    public Long getMaxStopOfRoutes() {
        return routeRepo.getMaxStopOfRoutes();
    }

    // Inciso G
    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesNotSell() {
        return routeRepo.getRoutesNotSell();
    }

    // Inciso H
    @Override
    @Transactional(readOnly = true)
    public List<Route> getTop3RoutesWithMaxRating() {
        Pageable pageable = PageRequest.of(0, 3);
        return routeRepo.getTop3RoutesWithMaxRating(pageable);
    }

    // Inciso I
    @Override
    @Transactional(readOnly = true)
    public Serv getMostDemandedService() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Serv> result = serviceRepo.getMostDemandedService(pageable);
        return result.isEmpty() ? null : result.get(0);
    }

    // Inciso J
    @Override
    @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return userRepo.getTourGuidesWithRating1();
    }

    
    
    // -----------------
	@Override
	public DriverUser createDriverUser(String username, String password, String fullName, String email, Date birthdate,
			String phoneNumber, String expedient) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
			Date birthdate, String phoneNumber, String education) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserById(Long id) throws ToursException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<User> getUserByUsername(String username) throws ToursException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public User updateUser(User user) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(User user) throws ToursException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Stop createStop(String name, String description) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stop> getStopByNameStart(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
			throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Route> getRouteById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Route> getRoutesBelowPrice(float price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Serv addServiceToSupplier(String name, float price, String description, Supplier supplier)
			throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Supplier> getSupplierById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Serv> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Purchase> getPurchaseByCode(String code) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deletePurchase(Purchase purchase) throws ToursException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
		// TODO Auto-generated method stub
		return null;
	}
}