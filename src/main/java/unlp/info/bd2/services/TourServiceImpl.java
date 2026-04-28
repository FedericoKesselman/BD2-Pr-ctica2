package unlp.info.bd2.services;

import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

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
    private StopRepository stopRepo;
    private ItemServiceRepository itemRepo;
    private EntityManager entityManager;

    @Autowired
    public TourServiceImpl(PurchaseRepository purchaseRepo,
    					 ReviewRepository reviewRepo,
                         RouteRepository routeRepo,
                         ServiceRepository serviceRepo,
                         SupplierRepository supplierRepo,
                         UserRepository userRepo,
                         StopRepository stopRepo,
                         ItemServiceRepository itemRepo,
                         EntityManager entityManager) {
    this.userRepo = userRepo;
    this.routeRepo = routeRepo;
    this.purchaseRepo = purchaseRepo;
    this.serviceRepo = serviceRepo;
    this.supplierRepo = supplierRepo;
    this.reviewRepo = reviewRepo;
    this.stopRepo = stopRepo;
    this.itemRepo = itemRepo;
    this.entityManager = entityManager;
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
		DriverUser driverUser = new DriverUser(username, password, fullName, email, birthdate, phoneNumber, expedient);
        return userRepo.save(driverUser);
	}

	@Override
	public TourGuideUser createTourGuideUser(String username, String password, String fullName, String email,
		Date birthdate, String phoneNumber, String education) throws ToursException {
		TourGuideUser tourGuideUser = new TourGuideUser(username, password, fullName, email, birthdate, phoneNumber, education);
        return userRepo.save(tourGuideUser);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserById(Long id) throws ToursException {
		return userRepo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserByUsername(String username) throws ToursException {
		return userRepo.findByUsername(username);
	}

	@Override
	public User updateUser(User user) throws ToursException {
		if (!userRepo.existsById(user.getId())) {
			throw new ToursException("Usuario no encontrado");
		}
		return userRepo.save(user);
	}

	@Override
	public void deleteUser(User user) throws ToursException {
		if (!user.isActive()) {
			throw new ToursException("El usuario se encuentra desactivado");
		}
		if (user instanceof TourGuideUser) {
			Long count = routeRepo.countRoutesByGuide(user.getId());
			if (count > 0) {
				throw new ToursException("El usuario no puede ser desactivado");
			}
		}
		if (user.getPurchaseList() != null & !user.getPurchaseList().isEmpty()) {
			user.setActive(false);
		} else {
			userRepo.delete(user);
		}
	}

	@Override
	public Stop createStop(String name, String description) throws ToursException {
		Stop stop = new Stop(name, description);
		return stopRepo.save(stop);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Stop> getStopByNameStart(String name) {
		return stopRepo.findByNameStartingWith(name);
	}

	@Override
	public Route createRoute(String name, float price, float totalKm, int maxNumberOfUsers, List<Stop> stops)
			throws ToursException {
		Route route = new Route(name, price, totalKm, maxNumberOfUsers, stops);
		return routeRepo.save(route);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Route> getRouteById(Long id) {
		return routeRepo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Route> getRoutesBelowPrice(float price) {
		return routeRepo.findByPriceLessThan(price);
	}

	@Override
	public void assignDriverByUsername(String username, Long idRoute) throws ToursException {
		Optional<User> optUSer = userRepo.findByUsername(username);
		if (optUSer.isEmpty() || !(optUSer.get() instanceof DriverUser)) {
			throw new ToursException("No pudo realizarse la asignación");
		}
		Optional<Route> optRoute = routeRepo.finById(idRoute);
		if (optRoute.isEmpty()) {
			throw new ToursException("No pudo realizarse la asignación");
		}
		Route route = optRoute.get();
		route.addDriver((DriverUser) optUSer.get());	
	}

	@Override
	public void assignTourGuideByUsername(String username, Long idRoute) throws ToursException {
		Optional<User> optUSer = userRepo.findByUsername(username);
		if (optUSer.isEmpty() || !(optUSer.get() instanceof DriverUser)) {
			throw new ToursException("No pudo realizarse la asignación");
		}
		Optional<Route> optRoute = routeRepo.finById(idRoute);
		if (optRoute.isEmpty()) {
			throw new ToursException("No pudo realizarse la asignación");
		}
		Route route = optRoute.get();
		route.addTourGuide((TourGuideUser) optUSer.get());
	}

	@Override
	public Supplier createSupplier(String businessName, String authorizationNumber) throws ToursException {
		Supplier supplier = new Supplier(businessName, authorizationNumber);
		return supplierRepo.save(supplier);
	}

	@Override
	public Serv addServiceToSupplier(String name, float price, String description, Supplier supplier)
			throws ToursException {
		try {
			Serv service = new Serv(name, price, description, supplier);
			Serv saved = serviceRepo.save(service);
			entityManager.flush();
			return saved;
		} catch(Exception e) {
			throw new ToursException("Constraint Violation");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Supplier> getSupplierById(Long id) {
		return supplierRepo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Supplier> getSupplierByAuthorizationNumber(String authorizationNumber) {
		return supplierRepo.findByAuthorizationNumber(authorizationNumber);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Serv> getServiceByNameAndSupplierId(String name, Long id) throws ToursException {
		return serviceRepo.findByNameAndSupplierId(name, id);
	}

	@Override
	public Purchase createPurchase(String code, Date date, Route route, User user) throws ToursException {
		long count = routeRepo.countPurchasesByRoute(route.getId());
		if (count >= route.getMaxNumberUsers()) {
			throw new ToursException("No se puede realizarse la compra");
		}
		try {
			Purchase purchase = new Purchase(code, date, route, user);
			Purchase saved = purchaseRepo.save(purchase);
			entityManager.flush();
			return saved;
		} catch (Exception e) {
			throw new ToursException("Constrain Violation");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Purchase> getPurchaseByCode(String code) {
		return purchaseRepo.findByCode(code);
	}

	@Override
	public void deletePurchase(Purchase purchase) throws ToursException {
		if (purchase.getUser() != null) {
			purchase.getUser().deletePurchase(purchase);
		}
		purchaseRepo.delete(purchase);
		entityManager.flush();
	}

	@Override
	public Review addReviewToPurchase(int rating, String comment, Purchase purchase) throws ToursException {
		Review review = new Review(rating, comment, purchase);
		purchase.setReview(review);
		return reviewRepo.save(review);
	}
}