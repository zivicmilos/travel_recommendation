INSERT INTO coordinates (latitude, longitude) VALUES
    (45.267136, 19.833549),
    (39.621590, 19.915163),
    (30.284581, -97.743049),
    (48.864716, 2.349014),
    (59.901221, 30.374976),
    (25.202184, 55.287507),
    (40.707560, -73.933030),
    (20.734589, -99.935713),
    (-34.665473, -58.422258),
    (62.039358, 129.596713),
    (-33.829896, 151.121902),
    (-41.305072, 174.770091),
    (-18.857922, 47.515956),
    (38.895547, -77.009903),
    (18.078985, -15.978416),
    (35.690387, 139.773616),
    (39.931430, 116.361009),
    (28.599055, 77.216318),
    (55.743315, 37.646959),
    (40.403255, -3.686433),
    (41.894531, 12.500752),
    (43.673733, -79.433427),
    (34.034451, -118.242034),
    (-22.931806, -43.180801),
    (-3.049032, -59.980583),
    (-34.806351, -56.163378),
    (4.687191, -74.052595),
    (30.055149, 31.247946),
    (-26.219980, 28.048118),
    (-1.289879, 36.831135),
    (5.353444, -4.003127),
    (-8.815041, 13.271068);

INSERT INTO locations (city, country, continent, coordinates_id) VALUES
    ('Novi Sad', 'Serbia', 'Europe', 1),
    ('Corfu', 'Greece', 'Europe', 2),
    ('Austin', 'USA', 'North America', 3),
    ('Paris', 'France', 'Europe', 4),
    ('Saint Petersburg', 'Russia', 'Europe', 5),
    ('Dubai', 'United Arab Emirates', 'Asia', 6),
    ('New York', 'USA', 'North America', 7),
    ('Bernal', 'Mexico', 'North America', 8),
    ('Buenos Aires', 'Argentine', 'South America', 9),
    ('Yakutsk', 'Russia', 'Asia', 10),
    ('Sydney', 'Australia', 'Australia', 11),
    ('Wellington', 'New Zealand', 'Australia', 12),
    ('Antananarivo', 'Madagascar', 'Africa', 13),
    ('Washington', 'USA', 'North America', 14),
    ('Nouakchott', 'Mauritania', 'Africa', 15),
    ('Tokio', 'Japan', 'Asia', 16),
    ('Beijing', 'China', 'Asia', 17),
    ('New Delhi', 'India', 'Asia', 18),
    ('Moscow', 'Russia', 'Asia', 19),
    ('Madrid', 'Spain', 'Europe', 20),
    ('Rome', 'Italy', 'Europe', 21),
    ('Toronto', 'Canada', 'North America', 22),
    ('Los Angeles', 'USA', 'North America', 23),
    ('Rio de Janeiro', 'Brazil', 'South America', 24),
    ('Manaus', 'Brazil', 'South America', 25),
    ('Montevideo', 'Uruguay', 'South America', 26),
    ('Bogota', 'Colombia', 'South America', 27),
    ('Cairo', 'Egypt', 'Africa', 28),
    ('Johannesburg', 'South Africa', 'Africa', 29),
    ('Nairobi', 'Kenia', 'Africa', 30),
    ('Abidjan', 'Cote d Ivoire', 'Africa', 31),
    ('Luanda', 'Angola', 'Africa', 32);

INSERT INTO users (name, lastname, username, password, email, date_of_birth, status, location_id, user_rank) VALUES
    ('Petar', 'Petrovic', 'pera', '$2a$12$bxMF9a71CFSL2ciknZLUOeHBbURkcHUnSxvBGCtCeVnkk.AA3gYOu', 'pera@gmail.com', DATE '1996-05-05', 0, 1, 4),
    ('John', 'Johnson', 'john', '$2a$12$BXtr4o/Gw5UZqQwWWgsroeOZo9z8yM2E3tPA3uILicJKf4w8808F2', 'john@gmail.com', DATE '1976-09-25', 2, 3, 4),
    ('Marko', 'Markovic', 'admin', '$2a$12$8D6zxoVcplEPQP6vY2dtoerLXdJgPdkhFDiG/SRZsk67PiQOBOjgy', 'marko@gmail.com', DATE '1981-03-03', 2, 1, null);

INSERT INTO roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_CLIENT');

INSERT INTO users_roles (user_id, roles_id) VALUES
    (1, 2),
    (2, 2),
    (3, 1);

INSERT INTO destinations (weather, location_id) VALUES
    (3, 2),
    (2, 4),
    (1, 5),
    (3, 6),
    (2, 7),
    (3, 8),
    (3, 9),
    (1, 10),
    (3, 11),
    (2, 12),
    (3, 13),
    (2, 14),
    (3, 15),
    (2, 16),
    (2, 17),
    (2, 18),
    (1, 19),
    (3, 20),
    (3, 21),
    (1, 22),
    (3, 23),
    (3, 24),
    (3, 25),
    (3, 26),
    (3, 27),
    (3, 28),
    (2, 29),
    (3, 30),
    (3, 31),
    (3, 32);

INSERT INTO destination_types (id, destination_type) VALUES
    (1, 4), (1, 7), (1, 8),
    (2, 2), (2, 4),
    (3, 2), (3, 9),
    (4, 2), (4, 4), (4, 7),
    (5, 2), (5, 4), (5, 8),
    (6, 1), (6, 5),
    (7, 2), (7, 4), (7, 8),
    (8, 1), (8, 5),
    (9, 2), (9, 4), (9, 8), (9, 9),
    (10, 2), (10, 4),
    (11, 5), (11, 6), (11, 8),
    (12, 2), (12, 4), (12, 9),
    (13, 5), (13, 6),
    (14, 2), (14, 8), (14, 9),
    (15, 2), (15, 8),
    (16, 2),
    (17, 2), (17, 9),
    (18, 2), (18, 4), (18, 8), (18, 9),
    (19, 2), (19, 9),
    (20, 2),
    (21, 2), (21, 4), (21, 8),
    (22, 4), (22, 7), (22, 8),
    (23, 3), (23, 5), (23, 6),
    (24, 7), (24, 8),
    (25, 8),
    (26, 9),
    (27, 5), (27, 6),
    (28, 5), (28, 6),
    (29, 5), (29, 6), (29, 7),
    (30, 5), (30, 6), (30, 7);

INSERT INTO transportation_types (id, transportation_type) VALUES
    (1, 4),
    (10, 4),
    (11, 4),
    (14, 4);

INSERT INTO travels (user_id, destination_id, travel_date, transportation_type, grade, cost) VALUES
    (1, 1, DATE '2022-06-06', 4, 5, 250),
    (1, 5, DATE '2022-06-11', 4, 4, 250),
    (1, 2, DATE '2022-05-01', 4, 4, 400);

INSERT INTO likes (user_id, destination_id, time) VALUES
    (1, 1, DATE '2022-08-27');
