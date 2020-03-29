DROP database if exists hoteldb;

CREATE database hoteldb;

USE hoteldb;

CREATE table Guest(
	 GuestId int primary key auto_increment,
     FirstName varchar(30) not null,
     LastName varchar(30) not null,
     Address varchar(100) null,
     City varchar(20) not null,
     State varchar(20) not null,
     ZipCode varchar(10) not null,
     PhoneNumber varchar(20) not null
     );
	
CREATE table RoomType(
	RoomTypeId int primary key auto_increment,
    RoomTypeName varchar(10) not null,
    StandardOccupancy int not null,
    MaximumOccupancy int not null,
    Price decimal(5,2) not null,
    ExtraOccupantCharge decimal(4,2) null
    );
    
CREATE table Amenity(
	AmenityId int primary key auto_increment,
    AmenityName varchar(20) not null,
    AdditionalAmenityCharge decimal(4,2) null
    );
    
CREATE table Room(
	RoomId int primary key auto_increment,
    Accessibility bool not null,
    FloorLocation varchar(1),
    RoomTypeId int not null
    );

CREATE table RoomAmenity(
	RoomId int not null,
    AmenityId int not null,
    constraint pk_RoomAmenity primary key (RoomId,AmenityId),
	constraint fk_AmenityRoom_Room foreign key(RoomId)
		references Room(RoomId),
	constraint fk_RoomAmenity_Amenity foreign key(AmenityId)
		references Amenity(AmenityId)
	);
    
CREATE table Reservation(
	ReservationId int primary key auto_increment,
    CheckInDate date not null,
    CheckOutDate date not null,
    GuestId int not null
    );
    
Create table ReservationRoom(
	ReservationId int not null,
    RoomId int not null,
    AdultCount int not null,
    ChildrenCount int null,
    PerRoomTotal decimal (8,2) not null,
    constraint pk_ReservationRoom primary key (ReservationId, RoomId),
		constraint fk_ReservationRoom_Reservation foreign key (ReservationId)
		references Reservation(ReservationId),
        constraint fk_ReservationRoom_Room foreign key (RoomId)
        references Room(RoomId)
	);
		
ALTER table Room 
	ADD constraint  fk_Room_RoomType foreign key(RoomTypeId)
		references RoomType(RoomTypeId);

ALTER table Reservation
	ADD constraint fk_Reservation_Guest foreign key(GuestId)
		references Guest(GuestId);
        


    
    


    
    
    

    

    
    
     

     
