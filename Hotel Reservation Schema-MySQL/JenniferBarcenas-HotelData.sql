use hoteldb;

-- INSERT into Amenity(AmenityId, AmenityName, AdditionalAmenityCharge)
-- 	values(1, 'Microwave',0.00),
--     (2,'Oven',0.00),
--     (3,'Refrigerator',0.00),
--     (4,'Jacuzzi',25.00);
--     
-- SELECT * FROM Amenity;
--     
-- INSERT into RoomType(RoomTypeId, RoomTypeName, StandardOccupancy, MaximumOccupancy, Price, ExtraOccupantCharge)
-- 	values(1,'Single',2,2,149.99,0.00),
--     (2,'Double',2,4,174.99,10.00),
--     (3,'Suite',3,8,399.99,20.00);
--     
SELECT * FROM RoomType;

-- INSERT into Guest(GuestId, FirstName, LastName, Address, City, State, ZipCode, PhoneNumber)
-- 	values(1,'Jennifer','Barcenas','123 5th Avenue','South Saint Paul','MN','55075','(832)222-2222'),
--     (2,'Mack','Simmer','379 Old Shore Street','Council Bluffs','IA','51501','(291)553-0508'),
--     (3,'Bettyann','Seery','750 Wintergreen Dr.','Wasilla','AK','99654','(478)277-9632'),
--     (4,'Duane','Cullison','9662 Foxrun Lane','Harlingen','TX','78552','(308)494-0198'),
--     (5,'Karie','Yang','9378 W. Augusta Ave.','West Deptford','NJ','08096','(214)730-0298'),
--     (6,'Aurore','Lipton','762 Wild Rose Street','Saginaw','MI','48601','(377)507-0974'),
--     (7,'Zachery','Luechtefeld','7 Poplar Dr.','Arvada','CO','80003','(814)485-2615'),
--     (8,'Jeremiah','Pendergrass','70 Oakwood St.','Zion','IL','60099','(279)491-0960'),
--     (9,'Walter','Holaway','7556 Arrowhead St.','Cumberland','RI','02864','(446)396-6785'),
--     (10,'Wilfred','Vise','77 West Surrey Street','Oswego','NY','13126','(834)727-1001'),
--     (11,'Maritza','Tilton','939 Linda Rd.','Burke','VA','22015','(446)351-6860'),
--     (12,'Joleen','Tison','87 Queen St.','Drexel Hill','PA','19026','(231)893-2755');
-- --     
SELECT * FROM Guest;

-- INSERT into Room(RoomId,Accessibility,FloorLocation,RoomTypeId)
-- 	values(201,0,2,2),(202,1,2,2),
--     (203,0,2,2),(204,1,2,2),
--     (205,0,2,1),(206,1,2,1),
--     (207,0,2,1),(208,1,2,1),
--     (301,0,3,2),(302,1,3,2),
--     (303,0,3,2),(304,1,3,2),
--     (305,0,3,1),(306,1,3,1),
--     (307,0,3,1),(308,1,3,1),
--     (401,1,4,3),(402,1,4,3);
--     
SELECT * FROM Room;

-- INSERT into RoomAmenity(RoomId, AmenityId)
-- 	values(201,1),(201,4),
--     (202,3),
--     (203,1),(203,4),
--     (204,3),
--     (205,1),(205,3),(205,4),
--     (206,1),(206,3),
--     (207,1),(207,3),(207,4),
--     (208,1),(208,3),
--     (301,1),(301,4),
--     (302,3),
--     (303,1),(303,4),
--     (304,3),
--     (305,1),(305,3),(305,4),
--     (306,1),(306,3),
--     (307,1),(307,3),(307,4),
--     (308,1),(308,3),
--     (401,1),(401,3),(401,2),
--     (402,1),(402,3),(402,2);
--     
SELECT * FROM RoomAmenity;

-- INSERT into Reservation(ReservationId, CheckInDate, CheckOutDate, GuestId)
-- 	values(1,'2023-02-02','2023-02-04',2),
--     (2,'2023-02-05','2023-02-10',3),
--     (3,'2023-02-22','2023-02-24',4),
--     (4,'2023-03-06','2023-03-07',5),
--     (5,'2023-03-17','2023-03-20',1),
--     (6,'2023-03-18','2023-03-23',6),
--     (7,'2023-03-29','2023-03-31',7),
--     (8,'2023-03-31','2023-04-05',8),
--     (9,'2023-04-09','2023-04-13',9),
--     (10,'2023-04-23','2023-04-24',10),
--     (11,'2023-05-30','2023-06-02',11),
--     (12,'2023-06-10','2023-06-14',12),
--     (13,'2023-06-17','2023-06-18',6),
--     (14,'2023-06-28','2023-07-02',1),
--     (15,'2023-07-13','2023-07-14',9),
--     (16,'2023-07-18','2023-07-21',10),
--     (17,'2023-07-28','2023-07-29',3),
--     (18,'2023-08-30','2023-09-01',3),
--     (19,'2023-09-13','2023-09-15',5),
--     (20,'2023-09-16','2023-09-17',2),
--     (21,'2023-11-22','2023-11-25',4),
--     (22,'2023-11-22','2023-11-25',2),
--     (23,'2023-12-24','2023-12-28',11);
--     
SELECT * FROM Reservation;

-- INSERT into ReservationRoom(ReservationId, RoomId, AdultCount, ChildrenCount,PerRoomTotal)
-- 	values(1,308,1,0,299.98),
--     (2,203,2,1,999.95),
--     (3,305,2,0,349.98),
--     (4,201,2,2,199.99),
--     (5,307,1,1,524.97),
--     (6,302,3,0,924.95),
--     (7,202,2,2,349.98),
--     (8,304,2,0,874.95),
--     (9,301,1,0,799.96),
--     (10,207,1,1,174.99),
--     (11,401,2,4,1199.97),
--     (12,206,2,0,599.96),(12,208,1,0,599.96),
--     (13,304,3,0,184.99),
--     (14,205,2,0,699.96),
--     (15,204,3,1,184.99),
--     (16,401,4,2,1259.97),
--     (17,303,2,1,199.99),
--     (18,305,1,0,349.98),
--     (19,203,2,2,399.98),
--     (20,208,2,0,149.99),
--     (21,401,2,2,1199.97),
--     (22,206,2,0,449.97),(22,301,2,2,599.97),
--     (23,302,2,0,699.96);
--     
SELECT * FROM ReservationRoom;

DELETE FROM ReservationRoom 
Where reservationId = 8;

DELETE FROM Reservation
Where GuestId = 8;

DELETE FROM Guest 
Where GuestId =8;


	