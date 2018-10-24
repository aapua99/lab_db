USE MASTER;
GO
DROP DATABASE IF EXISTS Popov15;
GO
CREATE DATABASE Popov15;
GO

USE Popov15


 
 create table Medicine(
 name varchar(50) not null,
 image varchar(50) not null,
 packing varchar(50) not null,
 count_in_package int not null,
 clasification varchar(50) not null,
 instruction text not null,
  
 );
 create table Pharmacy(
 number int not null,
 adress varchar(50) not null,
 telephone varchar(50) not null,
 manager varchar(60)not null,
 CHECK (telephone LIKE '[(][0-9][0-9][0-9][)][0-9][0-9][0-9][-][0-9][0-9][-][0-9][0-9]')
 );

  ALTER TABLE Pharmacy ADD PRIMARY KEY(number);
  ALTER TABLE Medicine ADD PRIMARY KEY(name);


 create table Name_Number(
 name varchar(50) ,
 number int not null,
 count int not null,
 FOREIGN KEY(name) REFERENCES Medicine(name) ,
 FOREIGN KEY(number) REFERENCES Pharmacy(number),
   CONSTRAINT CHK_Count CHECK (count>0)
 );




 Create Index index1
 on Pharmacy(adress)

 Create Index index2
 on Medicine(clasification)

 

 INSERT INTO Pharmacy(number ,adress ,telephone,manager) VALUES
 (1,'��������� 81','(063)404-08-12','����� ����� ���������'),
 (2,'��������� 107','(096)135-81-35','��� ³���� ���������'),
 (3,'������ 13','(089)404-08-12','����� ����� ����������');

 INSERT INTO Medicine(name , image ,packing, count_in_package, clasification, instruction) VALUES
 ('������', 'thepathofimage.jpg', 250, 20, '������','�������� �� ������������ �����'),
 ('�������', 'thepathofimage.jpg', 20, 10, '������','�������� �� ������������ �����');

 Insert INTO Name_Number(name, number, count) VALUES
 ('������', 1, 5),
 ('�������', 2, 25),
 ('�������', 1, 30),
 ('������', 1, 8);



