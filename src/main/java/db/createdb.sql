DROP TABLE IF EXISTS VEHICLES;
DROP TABLE IF EXISTS DRIVERS;
DROP TABLE IF EXISTS ORGANIZATORS;
DROP TABLE IF EXISTS RECEIPTS;
DROP TABLE IF EXISTS EXCURSIONOBJECTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS EXCURSIONS;

create table USERS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(100) NOT NULL UNIQUE,
  money INT NOT NULL,
  excursionID INT
);

create table DRIVERS (
  id INT NOT NULL,
  isFree BOOL NOT NULL,
  givenPrice INT NOT NULL,
  isAgree BOOL NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES USERS(id)
);

create table VEHICLES (
  id INT AUTO_INCREMENT PRIMARY KEY,
  driversID INT NOT NULL,
  FOREIGN KEY (driversID) REFERENCES DRIVERS(id),
  model VARCHAR(100) NOT NULL,
  mileage INT NOT NULL,
  capacity INT NOT NULL,
  numbers VARCHAR(100) NOT NULL ,
  isChecked BOOL NOT NULL
);

create table EXCURSIONS (
  id INT AUTO_INCREMENT PRIMARY KEY,
  isPaid BOOL NOT NULL,
  minTourists INT,
  maxTourists INT,
  equipment VARCHAR(1000),
  status INT,
  departureDate DATE,
  canAddComments BOOL,
  comments VARCHAR(1000),
  name VARCHAR(1000) NOT NULL
);


create table ORGANIZATORS (
  id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES USERS(id)
);

create table RECEIPTS (
  uid INT AUTO_INCREMENT PRIMARY KEY,
  sum INT NOT NULL,
  excID INT NOT NULL,
  FOREIGN KEY (excID) REFERENCES EXCURSIONS(id)
);

create table EXCURSIONOBJECTS (
  uid INT AUTO_INCREMENT PRIMARY KEY,
  excID INT,
  description VARCHAR(1000),
  pict BLOB,
  FOREIGN KEY (excID) REFERENCES EXCURSIONS(id)
);

ALTER TABLE USERS ADD
  FOREIGN KEY (excursionID) REFERENCES EXCURSIONS(id);

#Add one driver
INSERT INTO USERS(USERS.login, USERS.money, USERS.excursionID)
  VALUES ("ASHOT", 150, NULL);
INSERT INTO DRIVERS(DRIVERS.id, DRIVERS.isFree, DRIVERS.givenPrice, DRIVERS.isAgree) VALUES (
  (SELECT (USERS.id) from USERS WHERE USERS.login = "ASHOT"),
  FALSE,
  -1,
  FALSE
);
INSERT INTO VEHICLES(VEHICLES.driversID, VEHICLES.model, VEHICLES.mileage,
                     VEHICLES.capacity, VEHICLES.numbers, VEHICLES.isChecked)
VALUES (
  (SELECT (USERS.id) from USERS WHERE USERS.login = "ASHOT"),
  "Vaz 2114", 100000, 5, "Е777ЕЕ78", TRUE
);


#Add organizator
INSERT INTO USERS(USERS.login, USERS.money, USERS.excursionID)
  VALUES ("DANIIL", 100, NULL);
INSERT INTO ORGANIZATORS(ORGANIZATORS.id) VALUES (
  (SELECT (USERS.id) from USERS WHERE USERS.login = "DANIIL")
);

#Create excursion objects and excursion
INSERT INTO EXCURSIONOBJECTS(EXCURSIONOBJECTS.description)
VALUES ("Псебай. Поселок, расположенный в живописной долине на берегах реки Малая Лаба, в окружении заповедного леса, гор, ручьев, озер и рек. ");
INSERT INTO EXCURSIONOBJECTS(EXCURSIONOBJECTS.description)
VALUES ("Река Малая Лаба. Чистейшая горная река — левый приток Кубани — начинается на высоте 2401 метр с северных склонов перевала Аишко.");
INSERT INTO EXCURSIONOBJECTS(EXCURSIONOBJECTS.description)
VALUES ("Аишха (Аишхо). Вершина в цепи ГКХ, между верховьями рек Малой Лабы и Мзымты. В 4 км на северо-запад располагается перевал Аишха (2401).");
INSERT INTO EXCURSIONOBJECTS(EXCURSIONOBJECTS.description)
VALUES ("Красная Поляна. Популярный российский горнолыжный курорт, снежная жемчужна Сочи.");

INSERT INTO EXCURSIONS(EXCURSIONS.isPaid, EXCURSIONS.name,
EXCURSIONS.minTourists, EXCURSIONS.maxTourists, EXCURSIONS.equipment,
EXCURSIONS.status, EXCURSIONS.departureDate, EXCURSIONS.canAddComments)
VALUES (FALSE, "Через горы к морю", 1, 3, "Лук и стрелы", 0, '2017-7-04', FALSE);
UPDATE USERS SET USERS.excursionID = 1 WHERE USERS.id = 2;
UPDATE EXCURSIONOBJECTS SET EXCURSIONOBJECTS.excID = 1 WHERE EXCURSIONOBJECTS.uid = 1;
UPDATE EXCURSIONOBJECTS SET EXCURSIONOBJECTS.excID = 1 WHERE EXCURSIONOBJECTS.uid = 2;
UPDATE EXCURSIONOBJECTS SET EXCURSIONOBJECTS.excID = 1 WHERE EXCURSIONOBJECTS.uid = 3;
UPDATE EXCURSIONOBJECTS SET EXCURSIONOBJECTS.excID = 1 WHERE EXCURSIONOBJECTS.uid = 4;

#Add driver and users to excursion
#UPDATE USERS SET USERS.excursionID = 1 WHERE USERS.id = 1;
#UPDATE DRIVERS SET DRIVERS.isFree = FALSE WHERE DRIVERS.id = 1;

INSERT INTO USERS(USERS.login, USERS.money, USERS.excursionID)
VALUES ("User1", 120, NULL);
INSERT INTO USERS(USERS.login, USERS.money, USERS.excursionID)
VALUES ("User2", 777, 1);
INSERT INTO USERS(USERS.login, USERS.money, USERS.excursionID)
VALUES ("VinDiesel", 777, NULL);
INSERT INTO DRIVERS(DRIVERS.id, DRIVERS.isFree, DRIVERS.givenPrice, DRIVERS.isAgree) VALUES (
  (SELECT (USERS.id) from USERS WHERE USERS.login = "VinDiesel"),
  TRUE,
  -1,
  FALSE
);

INSERT INTO VEHICLES(VEHICLES.driversID, VEHICLES.model, VEHICLES.mileage,
                     VEHICLES.capacity, VEHICLES.numbers, VEHICLES.isChecked)
VALUES (
  (SELECT (USERS.id) from USERS WHERE USERS.login = "VinDiesel"),
  "Chevrolet Camaro", 1, 5, "О001ОО78", TRUE
);