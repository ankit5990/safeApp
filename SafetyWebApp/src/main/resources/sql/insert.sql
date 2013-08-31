delete from raw_data where id>0;
/*insert into raw_data table*/
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(1,28.6352494,77.22443450000002,'shivaji bridge',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(2,28.6361037,77.2235254,'minto road, ajmeri gate',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(3,28.6371418,77.2238768,'Deen Dayal Upadhaya Marg',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(4,28.6372979,77.2236341,'Deen Dayal Upadhaya Marg',234567346,2);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(5,28.6351384,77.22166919999999,'Connaught Circus, Yusuf Zai Market',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(6,28.6314397,77.21671549999999,'Radial Road Number 2, Block G, Connaught Place',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(7,28.6271148,77.2073465,'Baba Kharak Singh Marg',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(8,28.6265379,77.20611510000001,'744, Baba Kharak Singh Marg',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(9,28.6240845,77.2004407,'744, Baba Kharak Singh Marg, Type 3',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(10,28.6098976,77.1914984,'Mother Teresa Crescent',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(11,28.593804,77.16525249999999,'National Highway 8, Dhaula Kuan Enclave I',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(12,28.5860847,77.14671059999999,'Parade Rd, Khyber Lines',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(13,28.4478079,77.0381445,'Delhi-Gurgaon Expy, Institutional Area',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(14,28.4472259,77.0377934,'Institutional Area, Sector 32',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(15,28.4454161,77.0338599,'Sohna Rd, Sector 33',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(16,28.45933,77.0264317,'Railway Rd, New Basti, Sector 8',23456789,1);
insert into raw_data(id, latitude, longitude, streetName, `time`, victimTransport) values(17,28.4452987,77.0264317,'Sector 33, Gurgaon',23456789,1);


delete from processed_data where autoId > 0;
/*insert into processed_data table*/
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (1,28.6371418,77.2238768,1,3,'Deen Dayal Upadhaya Marg',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (2,28.6372979,77.2236341,1,3,'Deen Dayal Upadhaya Marg',3,2);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (3,28.6098976,77.1914984,1,3,'Mother Teresa Crescent',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (4,28.6240845,77.2004407,1,3,'744, Baba Kharak Singh Marg, Type 3',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (5,28.6351384,77.2216692,1,3,'Connaught Circus, Yusuf Zai Market',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (6,28.6271148,77.2073465,1,3,'Baba Kharak Singh Marg',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (7,28.6265379,77.2061151,1,3,'744, Baba Kharak Singh Marg',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (8,28.6314397,77.21671549999999,1,3,'Radial Road Number 2, Block G, Connaught Place',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (9,28.6361037,77.2235254,1,3,'minto road, ajmeri gate',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (10,28.6352494,77.22443450000002,1,3,'shivaji bridge',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (11,28.45933,77.0264317,1,3,'Railway Rd, New Basti, Sector 8',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (12,28.4454161,77.0338599,1,3,'Sohna Rd, Sector 33',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (13,28.4452987,77.0264317,1,3,'Sector 33, Gurgaon',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (14,28.4472259,77.0377934,1,3,'Institutional Area, Sector 32',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (15,28.4478079,77.0381445,1,3,'Delhi-Gurgaon Expy, Institutional Area',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (16,28.593804,77.1652525,1,3,'National Highway 8, Dhaula Kuan Enclave I',1,1);
INSERT INTO `processed_data` (`autoId`,`latitude`,`longitude`,`numberOfCrimes`,`rating`,`streetName`,`timeOfDay`,`victimTransport`) VALUES (17,28.5860847,77.14671059999999,1,3,'Parade Rd, Khyber Lines',1,1);
