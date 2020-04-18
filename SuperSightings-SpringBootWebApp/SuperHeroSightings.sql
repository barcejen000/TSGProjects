drop database if exists supersightingsdbtest;

create database supersightingsdbtest;

use supersightingsdbtest;

create table superperson(
	id int primary key auto_increment,
    `name` varchar(45) not null,
     `type` boolean not null,
    `description` varchar(100),
    superpower varchar(45) not null,
    picLink varchar(100) default "https://ombud.alaska.gov/wp-content/uploads/2018/01/no-user.jpg"
    );
    
create table `organization`(
	id int primary key auto_increment,
    `name` varchar(45) not null,
    `type` boolean not null,
    `description` varchar(100),
    address varchar(60) not null,
    contact varchar(20) not null
    );

create table location(
	id int primary key auto_increment,
    name varchar(45) not null,
    `description` varchar(100),
    address varchar(60) not null,
    latitude decimal(12,9) not null,
    longitude decimal(12,9) not null
	);
    
create table sighting(
	id int primary key auto_increment,
    sightingTime datetime not null,
    locationId int not null,
    foreign key(locationId) references location(id));

create table super_organization(
	superId int not null,
    organizationId int not null,
    primary key(superId, organizationId),
    foreign key(superId) references superperson(id),
    foreign key(organizationId) references organization(id));
    
create table super_sighting(
	superId int not null,
    sightingId int not null,
    primary key(superId, sightingId),
    foreign key(superId) references superperson(id),
    foreign key(sightingId) references sighting(id));
    
    
    
    


    
    
    
