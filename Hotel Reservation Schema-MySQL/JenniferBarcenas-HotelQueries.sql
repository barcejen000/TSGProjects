USE hoteldb;

-- 1. Write a query that returns a list of reservations that end in July 2023, 
-- including the name of the guest, the room number(s), and the reservation dates.
-- ----------------------------------------------------------------------------------
SELECT CONCAT(g.FirstName, ' ', g.lastname) GuestName,
r.CheckInDate, r.CheckOutDate, rr.RoomId RoomNumber
FROM ReservationRoom rr
INNER JOIN Reservation r ON rr.ReservationId = r.ReservationId
INNER JOIN Guest g ON r.GuestId = g.GuestId
WHERE r.checkOutDate  BETWEEN '2023-07-01' AND '2023-07-31';

-- RESULTS: 4 rows
-- # GuestName	CheckInDate	CheckOutDate	RoomNumber
-- Jennifer Barcenas	2023-06-28	2023-07-02	205
-- Walter Holaway	2023-07-13	2023-07-14	204
-- Wilfred Vise	2023-07-18	2023-07-21	401
-- Bettyann Seery	2023-07-28	2023-07-29	303

-- ------------------------------------------------------------------------------------
-- 2. Write a query that returns a list of all reservations for rooms with a jacuzzi, 
-- displaying the guest's name, the room number, and the dates of the reservation.
-- ----------------------------------------------------------------------------------
SELECT CONCAT(g.FirstName, ' ', g.lastname) GuestName,
rr.RoomId RoomNumber, res.CheckInDate, res.CheckOutDate
FROM Guest g
INNER JOIN Reservation res ON g.GuestId = res.GuestId 
INNER JOIN ReservationRoom rr ON res.ReservationId = rr.ReservationId
INNER JOIN Room r ON rr.RoomID = r.RoomId
INNER JOIN RoomAmenity ra on r.RoomId = ra.RoomId
WHERE ra.AmenityId =4;

-- RESULTS: 11 rows
-- # GuestName	RoomNumber	CheckInDate	CheckOutDate
-- Jennifer Barcenas	307	2023-03-17	2023-03-20
-- Jennifer Barcenas	205	2023-06-28	2023-07-02
-- Mack Simmer	301	2023-11-22	2023-11-25
-- Bettyann Seery	203	2023-02-05	2023-02-10
-- Bettyann Seery	303	2023-07-28	2023-07-29
-- Bettyann Seery	305	2023-08-30	2023-09-01
-- Duane Cullison	305	2023-02-22	2023-02-24
-- Karie Yang	201	2023-03-06	2023-03-07
-- Karie Yang	203	2023-09-13	2023-09-15
-- Walter Holaway	301	2023-04-09	2023-04-13
-- Wilfred Vise	207	2023-04-23	2023-04-24

-- ----------------------------------------------------------------------------------
-- 3. Write a query that returns all the rooms reserved for a specific guest, 
-- including the guest's name, the room(s) reserved, 
-- the starting date of the reservation, and how many people were included in the reservation.
-- (Choose a guest's name from the existing data.)
-- ----------------------------------------------------------------------------------
SELECT rr.RoomId, CONCAT(g.FirstName, ' ', g.lastname) GuestName,
r.CheckInDate, rr.AdultCount, rr.ChildrenCount, (rr.AdultCount + rr.ChildrenCount) AS TotalPeople
FROM Guest g 
INNER JOIN Reservation r ON g.GuestId= r.GuestId
INNER JOIN ReservationRoom rr ON r.ReservationId = rr.ReservationId
WHERE g.firstName = 'Bettyann'
AND g.lastName = 'Seery';

-- RESULTS: 3 rows
-- # RoomId	GuestName	CheckInDate	AdultCount	ChildrenCount	TotalPeople
-- 203	Bettyann Seery	2023-02-05	2	1	3
-- 303	Bettyann Seery	2023-07-28	2	1	3
-- 305	Bettyann Seery	2023-08-30	1	0	1

-- ----------------------------------------------------------------------------------
-- 4. Write a query that returns a list of rooms, reservation ID, 
-- and per-room cost for each reservation. The results should include all rooms, 
-- whether or not there is a reservation associated with the room.
-- ----------------------------------------------------------------------------------
SELECT r.roomId RoomNumber, rr.ReservationId, rr.PerRoomTotal,
CASE 
	WHEN(r.roomtypeId=1 AND ra.AmenityId = 4)
		THEN (datediff(res.CheckOutDate, res.CheckInDate) * (rt.Price + a.AdditionalAmenityCharge))
	WHEN(r.RoomTypeId=1)
		THEN(rt.Price * (datediff(res.CheckOutDate, res.CheckInDate)))
    WHEN(r.RoomTypeId=2 AND rr.AdultCount <= rt.StandardOccupancy)
		THEN(rt.Price * (datediff(res.CheckOutDate, res.CheckInDate)))
	WHEN(r.RoomTypeId=2 AND  rr.AdultCount >= rt.StandardOccupancy)
		THEN(rt.Price * (datediff(res.CheckOutDate, res.CheckInDate)) + ((rr.AdultCount - rt.StandardOccupancy) * rt.ExtraOccupantCharge))
    WHEN(r.RoomTypeId=2 AND rr.AdultCount <= rt.StandardOccupancy AND ra.AmenityId = 4)
		THEN(rt.Price * (datediff(res.CheckOutDate, res.CheckInDate)) + (a.AdditionalAmenityCharge * datediff(res.CheckOutDate, res.CheckInDate)))
	WHEN(r.RoomTypeId=2 AND  rr.AdultCount >= rt.StandardOccupancy AND ra.AmenityId = 4)
		THEN((rt.Price*(datediff(res.CheckOutDate, res.CheckInDate))) + (((rr.AdultCount - rt.StandardOccupancy) * rt.ExtraOccupantCharge)*(datediff(res.CheckOutDate, res.CheckInDate)))+ (a.AdditionalAmenityCharge * datediff(res.CheckOutDate, res.CheckInDate)))
	WHEN(r.RoomTypeId =3 AND rr.AdultCount <= rt.StandardOccupancy)
		THEN(rt.Price * (datediff(res.CheckOutDate, res.CheckInDate)))
	WHEN(r.RoomTypeId=3 AND  rr.AdultCount >= rt.StandardOccupancy)
		THEN((rt.Price*(datediff(res.CheckOutDate, res.CheckInDate))) + (((rr.AdultCount - rt.StandardOccupancy) * rt.ExtraOccupantCharge)*(datediff(res.CheckOutDate, res.CheckInDate))))  
    END TotalReservationCost
FROM Room r
left outer join RoomAmenity ra on r.roomId = ra.roomId
left outer join Amenity a on ra.amenityId = a.amenityId
left OUTER JOIN RoomType rt on r.RoomTypeId = rt.RoomtypeId
left OUTER JOIN reservationroom rr On r.roomId = rr.roomId
left outer JOIN Reservation res ON rr.ReservationId = res.ReservationId
GROUP by  r.roomid, res.reservationid;

SELECT r.roomId RoomNumber, rr.ReservationId, rr.PerRoomTotal
FROM Room r
LEFT OUTER JOIN ReservationRoom rr ON r.roomId = rr.roomId
LEFT OUTER JOIN Reservation res on rr.reservationid = res.reservationId;

-- -- -- RESULTS:  26 rows
-- # RoomNumber	ReservationId	PerRoomTotal
-- 201	4	199.99
-- 202	7	349.98
-- 203	2	999.95
-- 203	19	399.98
-- 204	15	184.99
-- 205	14	699.96
-- 206	12	599.96
-- 206	22	449.97
-- 207	10	174.99
-- 208	12	599.96
-- 208	20	149.99
-- 301	9	799.96
-- 301	22	599.97
-- 302	6	924.95
-- 302	23	699.96
-- 303	17	199.99
-- 304	13	184.99
-- 305	3	349.98
-- 305	18	349.98
-- 306	null null	
-- 307	5	524.97
-- 308	1	299.98
-- 401	11	1199.97
-- 401	16	1259.97
-- 401	21	1199.97
-- 402 null null

-- ----------------------------------------------------------------------------------
-- 5. Write a query that returns all the rooms accommodating at least three guests 
-- and that are reserved on any date in April 2023.
-- ----------------------------------------------------------------------------------
SELECT rr.RoomId RoomNumber
FROM ReservationRoom rr
INNER JOIN Reservation res ON rr.ReservationId = res.ReservationId
INNER JOIN Room r ON rr.roomId = r.roomid
INNER JOIN RoomType rt ON r.roomtypeid = rt.roomtypeid
WHERE (res.CheckOutDate BETWEEN '2023-04-01' AND '2023-04-30' OR res.CheckInDate BETWEEN '2023-04-01' AND '2023-04-30')
AND (rt.maximumoccupancy) >= 3;

-- RESULTS: 1 rows
-- # RoomNumber
-- 301

-- ----------------------------------------------------------------------------------
-- 6. Write a query that returns a list of all guest names and the number of reservations per guest, 
-- sorted starting with the guest with the most reservations and then by the guest's last name.
-- ----------------------------------------------------------------------------------
SELECT g.FirstName, 
g.lastname,
COUNT(r.reservationId) ReservationCount
FROM Guest g 
INNER JOIN Reservation r ON g.guestId = r.guestId
GROUP BY g.guestId
ORDER BY reservationcount desc, g.lastname; 

-- RESULTS: 11 rows
-- # FirstName	lastname	ReservationCount
-- Bettyann	Seery	3
-- Mack	Simmer	3
-- Jennifer	Barcenas	2
-- Duane	Cullison	2
-- Walter	Holaway	2
-- Aurore	Lipton	2
-- Maritza	Tilton	2
-- Wilfred	Vise	2
-- Karie	Yang	2
-- Zachery	Luechtefeld	1
-- Joleen	Tison	1


-- ----------------------------------------------------------------------------------
-- 7. Write a query that displays the name, address, and phone number of a guest 
-- based on their phone number. (Choose a phone number from the existing data.)
-- ----------------------------------------------------------------------------------
SELECT CONCAT(g.FirstName, ' ', g.lastname) GuestName,
g.Address, g.State, g.ZipCode, g.PhoneNumber
FROM Guest g
WHERE g.PhoneNumber = '(308)494-0198';

-- RESULTS: 1 row
-- # GuestName	Address	State	ZipCode	PhoneNumber
-- Duane Cullison	9662 Foxrun Lane	TX	78552	(308)494-0198



