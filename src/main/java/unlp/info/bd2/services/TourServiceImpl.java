package unlp.info.bd2.services;

import unlp.info.bd2.model.*;
import unlp.info.bd2.repositories.*;
import unlp.info.bd2.utils.ToursException;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TourseServiceImpl implements ToursService {

    private UserRepository userRepo;
    private RouteRepository routeRepo;
    private PurchaseRepository purchaseRepo;
    private ServiceRepository serviceRepo;
    private SupplierRepository supplierRepo;
    private ReviewRepository reviewRepo;

    @Autowired
    public TourseServiceImpl(UserRepository userRepo,
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
        Purchase purchase = new Purchase(code, new Date(), route, user);
        return purchaseRepo.save(purchase);
    }

    @Override
    @Transactional
    public Service updateServicePriceById(Long id, float newPrice) throws ToursException {
        Service service = serviceRepo.findById(id).orElse(null);

        if (service == null) 
            throw new ToursException("Service not found");

        service.setPrice(newPrice);
        return service;
    }

    @Override
    @Transactional
    public ItemService addItemToPurchase(Service service, int quantity, Purchase purchase)  throws ToursException {

        Purchase managedPurchase = purchaseRepo.findById(purchase.getId()).orElse(null);
        Service managedService = serviceRepo.findById(service.getId()).orElse(null);

        if (managedPurchase == null || managedService == null) 
            throw new ToursException("Purchase or Service not found");

        ItemService item = new ItemService(quantity, managedService);
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
    public long getCountOfPurchasesBetweenDates(Date start, Date end) {
        return purchaseRepo.countByDateBetween(start, end);
    }

    // Inciso E
    @Override
    @Transactional(readOnly = true)
    public List<Route> getRoutesWithStop(Stop stop) {
        return routeRepo.findByStops(stop);
    }

    // Inciso F
    @Override
    @Transactional(readOnly = true)
    public int getMaxStopOfRoutes() {
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
    public Service getMostDemandedService() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Service> result = serviceRepo.getMostDemandedService(pageable);
        return result.isEmpty() ? null : result.get(0);
    }

    // Inciso J
    @Override
    @Transactional(readOnly = true)
    public List<TourGuideUser> getTourGuidesWithRating1() {
        return userRepo.getTourGuidesWithRating1();
    }
}