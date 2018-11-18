DROP DATABASE IF EXISTS Popov15;
CREATE DATABASE Popov15;
USE Popov15;
CREATE TABLE shop(
address VARCHAR(50) NOT NULL,
name VARCHAR(50) NOT NULL,
manager VARCHAR(50) NOT NULL,
PRIMARY KEY (name)
);

CREATE TABLE fruits(
name VARCHAR(50) NOT NULL,
price DECIMAL(10) NOT NULL,
name_shop VARCHAR(50) NOT NULL,
FOREIGN KEY(name_shop) REFERENCES shop(name),
PRIMARY KEY (name)
);
CREATE TABLE suppliers(
name VARCHAR(50) NOT NULL,
city VARCHAR(50) NOT NULL,
PRIMARY KEY(name)
);

CREATE TABLE Suppliers_Fruits(
supplier VARCHAR(50) NOT NULL,
fruit VARCHAR(50) NOT NULL,
price VARCHAR(50) NOT NULL,
FOREIGN KEY (supplier) REFERENCES suppliers(name),
FOREIGN KEY (fruit) REFERENCES fruits(name)
);

INSERT INTO shop(name, address, manager) VALUES
("Ашан","Стрийська 61","Криворучка Ольга"),
("Вопак","Гашека 13","Попов Андрій"),
("Близенько","Городоцька 4","Манько Анна"),
("Метро","КИївська 5","Остапчук Олександр"),
("Два кроки від хати","Степана Бандери 5","Рой Віталій"),
("Фуршет","Сахарова 78","Питько Дмитро"),
("Продукти","Львівська 8","Петрів Ілона"),
("Фрукти","Максимовича 65","Поломана Олена"),
("Барвінок","Трускавецька 78","Саган Максим"),
("Сільпо","Сихівська 43","Крук Роман");

INSERT INTO fruits(name, price, name_shop) VALUES
("Апельсин",45.25,"Фуршет"),
("Ківі",89.56,"Фуршет"),
("Мандарини",23,"Барвінок"),
("Грейфрут",45.29,"Фуршет"),
("Яблуко",6.5,"Фуршет"),
("Груша",9.25,"Фуршет"),
("Банан",25.98,"Фуршет"),
("Малина",89.10,"Фуршет"),
("Ананас",105.98,"Фуршет"),
("Манго",205.5,"Фуршет"),
("Авокадо",250.89,"Фуршет");

INSERT INTO suppliers(name, city) VALUES
("ППАНАНАС","Львів"),
("ФРуктиОпт","Одеса"),
("Всім фрукти","Київ"),
("Фрукти в кожну хату","Суми"),
("Фрукти кожен день","Львів"),
("Коден день","Тернопіль"),
("Агуша","Львів"),
("Супер фрукт","Одеса"),
("Мега фрукт","Львів"),
("Фруктозавр","Львів"),
("ФруктоМагазин","Київ");

INSERT INTO Suppliers_Fruits(supplier, fruit,price) VALUES
("Фруктозавр","Ананас",50),
("Фруктозавр","Малина",25),
("Фруктозавр","Яблуко",3),
("Фруктозавр","Манго",10),
("Агуша","Ананас",50),
("Агуша","Авокадо",49),
("Агуша","Малина",89),
("Агуша","Груша",78),
("Агуша","Ківі",65),
("Агуша","Апельсин",95.18)