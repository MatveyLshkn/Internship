INSERT INTO model (car_type, brand, model_name)
VALUES ('Sedan', 'Toyota', 'Camry'),
       ('SUV', 'Honda', 'CR-V'),
       ('Truck', 'Ford', 'F-150'),
       ('Convertible', 'Mazda', 'MX-5'),
       ('Hatchback', 'Volkswagen', 'Golf');

INSERT INTO customer (full_name, email, phone_number, address)
VALUES ('John Doe', 'johndoe@example.com', '123-456-7890', '123 Elm St, Springfield'),
       ('Jane Smith', 'janesmith@example.com', '098-765-4321', '456 Oak St, Metropolis'),
       ('Alice Johnson', 'alicej@example.com', '555-123-4567', '789 Maple St, Gotham'),
       ('Bob Brown', 'bobb@example.com', '111-222-3333', '101 Pine St, Star City'),
       ('Carol White', 'carolw@example.com', '222-333-4444', '202 Birch St, Central City');

INSERT INTO employee (full_name, position, hire_date, salary)
VALUES ('Michael Scott', 'Regional Manager', '2005-04-01', 75000),
       ('Dwight Schrute', 'Assistant to the Regional Manager', '2005-04-01', 55000),
       ('Jim Halpert', 'Sales Representative', '2006-07-10', 50000),
       ('Pam Beesly', 'Receptionist', '2005-04-01', 40000),
       ('Stanley Hudson', 'Sales Representative', '2005-04-01', 52000);

INSERT INTO discount (percentage, start_date, end_date, model_id)
VALUES (10.0, '2024-08-01', '2024-08-31', 1),
       (15.0, '2024-09-01', '2024-09-30', 2),
       (20.0, '2024-10-01', '2024-10-31', 3),
       (5.0, '2024-11-01', '2024-11-30', 4),
       (12.5, '2024-12-01', '2024-12-31', 5);

INSERT INTO car (model_id, color, production_date, price, mileage)
VALUES (101, 'Red', '2023-01-15', 30000, 5000),
       (102, 'Blue', '2022-05-20', 28000, 10000),
       (103, 'Black', '2021-03-25', 35000, 15000),
       (104, 'White', '2020-11-05', 27000, 20000),
       (105, 'Silver', '2019-08-15', 22000, 25000);

INSERT INTO analytics (car_id, customer_id, sale_date, sale_assistant_id, sold_price, discount_id)
VALUES (101, 101, '2024-07-01', 103, 29500, 101),
       (102, 102, '2024-07-02', 101, 27000, 102),
       (103, 103, '2024-07-03', 102, 34000, 103),
       (104, 104, '2024-07-04', 104, 26500, 104),
       (105, 105, '2024-07-05', 105, 21500, 105);