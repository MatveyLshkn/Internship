package lma.util;

import lma.constants.CommonConstants;
import lma.entity.Analytics;
import lma.entity.Car;
import lma.entity.Customer;
import lma.entity.Discount;
import lma.entity.Employee;
import lma.entity.Model;
import lma.enums.CarTypeEnum;
import lma.service.CarService;
import lma.service.CrudService;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;



@UtilityClass
public class Executor {
    public void executeAll(){
        System.out.println("Analytics:");
        testAnalyticsService();

        System.out.println("\n\n\nCar:");
        testCarService();

        System.out.println("\n\n\nCustomer:");
        testCustomerService();

        System.out.println("\n\n\nDiscount:");
        testDiscountService();

        System.out.println("\n\n\nEmployee:");
        testEmployeeService();

        System.out.println("\n\n\nModel:");
        testModelService();
    }

    private static void testAnalyticsService() {
        CrudService<Long, Analytics> analyticsService = new CrudService<>(Analytics.class);

        Analytics analytics = Analytics.builder()
                .carId(101L)
                .date(LocalDate.now())
                .customerId(101L)
                .saleAssistantId(101L)
                .soldPrice(BigDecimal.valueOf(1223.67))
                .discountId(101l)
                .build();

        analyticsService.save(analytics);

        assert Objects.nonNull(analytics.getId());
        System.out.println(analyticsService.isPresent(analytics.getId()));
        System.out.println(analyticsService.findById(analytics.getId()));

        analytics.setDate(LocalDate.now().minus(4, ChronoUnit.YEARS));
        analyticsService.update(analytics);
        System.out.println(analyticsService.findById(analytics.getId()));

        analyticsService.delete(Analytics.builder().id(analytics.getId()).build());

        System.out.println(analyticsService.findAll());
    }

    private static void testCarService() {
        CarService carService = new CarService();

        Car car = Car.builder()
                .productionDate(LocalDate.now())
                .color("Blue")
                .price(BigDecimal.valueOf(1223.67))
                .mileage(10l)
                .modelId(101L)
                .build();

        carService.save(car);

        assert Objects.nonNull(car.getId());
        System.out.println(carService.isPresent(car.getId()));
        System.out.println(carService.findById(car.getId()));

        car.setProductionDate(LocalDate.now().minus(4, ChronoUnit.YEARS));
        carService.update(car);
        System.out.println(carService.findById(car.getId()));

        carService.delete(Car.builder().id(car.getId()).build());

        System.out.println(carService.findAll());

        System.out.println(carService.findAllByBrand("Mazda"));
    }

    private static void testCustomerService() {
        CrudService<Long, Customer> customerService = new CrudService<>(Customer.class);

        Customer customer = Customer.builder()
                .email("test@mail.com")
                .fullName("Kobe Bryant")
                .address("Pushkina")
                .phoneNumber("1234321")
                .build();

        customerService.save(customer);

        assert Objects.nonNull(customer.getId());
        System.out.println(customerService.isPresent(customer.getId()));
        System.out.println(customerService.findById(customer.getId()));

        customer.setPhoneNumber("976543");
        customerService.update(customer);
        System.out.println(customerService.findById(customer.getId()));

        customerService.delete(Customer.builder().id(customer.getId()).build());

        System.out.println(customerService.findAll());
    }

    private static void testDiscountService() {
        CrudService<Long, Discount> discountService = new CrudService<>(Discount.class);

        Discount discount = Discount.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .modelId(101L)
                .percentage(55d)
                .build();

        discountService.save(discount);

        assert Objects.nonNull(discount.getId());
        System.out.println(discountService.isPresent(discount.getId()));
        System.out.println(discountService.findById(discount.getId()));

        discount.setStartDate(LocalDate.now().minus(4, ChronoUnit.YEARS));
        discountService.update(discount);
        System.out.println(discountService.findById(discount.getId()));

        discountService.delete(Discount.builder().id(discount.getId()).build());

        System.out.println(discountService.findAll());
    }

    private static void testEmployeeService() {
        CrudService<Long, Employee> employeeService = new CrudService<>(Employee.class);

        Employee employee = Employee.builder()
                .fullName("test")
                .position("test")
                .hireDate(LocalDate.now())
                .salary(BigDecimal.ONE)
                .build();

        employeeService.save(employee);

        assert Objects.nonNull(employee.getId());
        System.out.println(employeeService.isPresent(employee.getId()));
        System.out.println(employeeService.findById(employee.getId()));

        employee.setHireDate(LocalDate.now().minus(4, ChronoUnit.YEARS));
        employeeService.update(employee);
        System.out.println(employeeService.findById(employee.getId()));

        employeeService.delete(Employee.builder().id(employee.getId()).build());

        System.out.println(employeeService.findAll());
    }

    private static void testModelService() {
        CrudService<Long, Model> modelService = new CrudService<>(Model.class);

        Model model = Model.builder()
                .carType(CarTypeEnum.CONVERTIBLE)
                .modelName("mustang")
                .brand("ford")
                .build();

        modelService.save(model);

        assert Objects.nonNull(model.getId());
        System.out.println(modelService.isPresent(model.getId()));
        System.out.println(modelService.findById(model.getId()));

        model.setCarType(CarTypeEnum.SPORT);
        modelService.update(model);
        System.out.println(modelService.findById(model.getId()));

        modelService.delete(Model.builder().id(model.getId()).build());

        System.out.println(modelService.findAll());
    }
}
