-- these are just to test that tunder request are correctly executed

SELECT SEAT.*,  SEAT_STATUS.name 
FROM SEAT, SEAT_STATUS where (SEAT_STATUS_FK = SEAT_STATUS.id);

