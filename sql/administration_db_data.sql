-- Running file...
-- .=====================================================. --
-- |               CREATION DES DONNEES                  | --
-- |               DE LA BASE DE DONNEES                 | --
-- '=====================================================' --
-------------------------------------------------------------
-- DATABSE: 		administration_db [root on ROOT]
-- URL:				jdbc:derby://localhost/administration_db
-- AUTHOR:			Brian GOHIER
-- DATE:			2013-05-22 22:05
-------------------------------------------------------------

----- Pour supprimer toutes les entrées dans les tables -----
-- DELETE FROM ROOT.T_USER;
-- ALTER TABLE ROOT.T_USER ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE ROOT.CLIENT DROP CONSTRAINT client_c_user_id_fk;
DELETE FROM ROOT.FACTURE;
ALTER TABLE ROOT.FACTURE ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.INTERVENTION;
ALTER TABLE ROOT.INTERVENTION ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.TASK;
ALTER TABLE ROOT.TASK ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.C_USER;
ALTER TABLE ROOT.C_USER ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.MAIL;
ALTER TABLE ROOT.MAIL ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.WORKSTATION;
ALTER TABLE ROOT.WORKSTATION ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.SOFTWARE;
ALTER TABLE ROOT.SOFTWARE ALTER COLUMN ID RESTART WITH 1;
DELETE FROM ROOT.CLIENT;
ALTER TABLE ROOT.CLIENT ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE ROOT.CLIENT ADD CONSTRAINT client_c_user_id_fk
                FOREIGN KEY (id_user)
                REFERENCES ROOT.C_USER(id);
DELETE FROM ROOT.FILE_PATH;
ALTER TABLE ROOT.FILE_PATH ALTER COLUMN ID RESTART WITH 1;

------- Permet de ne laisser l'identifiant '1' libre --------
-- Redémarre le compteur à '0', création d'admin temporaire
--  prenant alors en ID '0', prochaine entrée à '1'
-- ALTER TABLE ROOT.T_USER ALTER COLUMN ID RESTART WITH 0;

---------- Création d'un administrateur par défaut ----------
-- LOGIN (mail):		admin
-- PASSWORD:			admin
-- /!\ Ne pas oublier de supprimer cette entrée après  /!\ --
-- /!\ avoir créé votre administrateur via l'interface /!\ --
-- /!\ principale                                      /!\ --
-- INSERT INTO ROOT.T_USER
-- 		(MAIL, "NAME", FIRSTNAME, RIGHTS, PASSWORD) 
-- 	VALUES ('admin', '', '', 'ADMIN',
-- 				'!#/)zW¥§C‰JJ€Ã                ');

------------------------- FILE_PATH -------------------------
INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('Client/0001');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('Client/0002');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('Client/0003');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('Client/0004');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('Client/0005');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('Client/0006');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0001');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0002');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0003');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0004');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0005');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0006');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0007');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0008');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0009');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0010');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0011');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0012');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0013');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0014');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0015');

INSERT INTO ROOT.FILE_PATH (FILE_PATH) 
	VALUES ('CUser/0016');

---------------------- INITIALISATION -----------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, ID_FILE_PATH, "NAME", ADDRESS, POSTALCODE,
				PHONE, FAX, MAIL, INTERVENTION_TYPE,
				OBSERVATIONS) 
	VALUES (
		NULL,
		1,
		'Admin Services',
		'La Ville aux Dames',
                37700,
		'02.47.44.77.11',
		NULL,
		'contact@admin-services.fr',
		'SA',
		NULL);
		
INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS)  
	VALUES (1, 7, 'MINNE', '06.78.45.28.82', 'MINNE HOEVE');
	
UPDATE ROOT.CLIENT SET "ID_USER" = 1 WHERE ID = 1;

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (1, 'contact@admin-services.fr', '', '', '', '');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(1, 'Fixe', 'ASUS', '2006-06-01',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (1, 'Microsoft Office 2010', '2010',
		'With ToolKit', 'Microsoft Corporation ©', 1);

INSERT INTO ROOT.TASK (ID_CLIENT, ID_USER, ID_WORKSTATION,
		DESCRIPTION, START_DATE, INTENDED_DURATION,
		DEPLACEMENT, INTERVENTION_TYPE) 
	VALUES (1, 1, 1, 'Test base de données DERBY pour Admin Services',
		'2013-06-01', '02 h 00 mins', false, 'LOGICIELLE');

------------------------- CLIENT 2 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, ID_FILE_PATH, "NAME", ADDRESS, POSTALCODE,
				PHONE, FAX, MAIL, INTERVENTION_TYPE,
				OBSERVATIONS) 
	VALUES (
		NULL,
		2,
		'SOCIETY2',
		'SOCIETY CITY 2',
                22222,
		'02.00.00.00.02',
		NULL,
		'society_2@society.fr',
		'SA',
		NULL);
		
-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (2, 8, 'USER2', '06.00.00.00.02', 'Utilisateur 2');
UPDATE ROOT.CLIENT SET "ID_USER" = 2 WHERE ID = 2;

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (2, 9, 'USER3', '06.00.00.00.03', 'Utilisateur 3');

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (2, 10, 'USER4', '06.00.00.00.04', 'Utilisateur 4');
	
--------------------------- MAIL ----------------------------
INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (2, 'mail2@mail.fr', 'pop2.pop', 'pop2pass',
				'smtp2.smtp', 'smtp2pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (2, 'mail3@mail.fr', 'pop3.pop', 'pop3pass',
				'smtp3.smtp', 'smtp3pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (2, 'mail4@mail.fr', 'pop4.pop', 'pop4pass',
				'smtp4.smtp', 'smtp4pass');

------------------------ WORKSTATION ------------------------
INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(2, 'Fixe', 'ASUS', '2006-06-02',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(2, 'Fixe', 'ASUS', '2006-06-03',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(2, 'Fixe', 'ASUS', '2006-06-04',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

-------------------------- SOFTWARE -------------------------
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (2, 'Software2', 'version.1.02',
		'SOFTWARE_LICENCE2', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (2, 'Software3', 'version.1.03',
		'SOFTWARE_LICENCE3', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (2, 'Software4', 'version.1.04',
		'SOFTWARE_LICENCE4', 'Software Corp. ©', 1);


------------------------- CLIENT 3 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, ID_FILE_PATH, "NAME", ADDRESS, POSTALCODE,
				PHONE, FAX, MAIL, INTERVENTION_TYPE,
				OBSERVATIONS) 
	VALUES (
		NULL,
		3,
		'SOCIETY3',
		'SOCIETY CITY 3',
                33333,
		'02.00.00.00.03',
		NULL,
		'society_3@society.fr',
		'SA',
		NULL);
		
-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (3, 11, 'USER5', '06.00.00.00.05', 'Utilisateur 5');
UPDATE ROOT.CLIENT SET "ID_USER" = 5 WHERE ID = 3;

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (3, 12, 'USER6', '06.00.00.00.06', 'Utilisateur 6');

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (3, 13, 'USER7', '06.00.00.00.07', 'Utilisateur 7');
	
--------------------------- MAIL ----------------------------
INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (3, 'mail5@mail.fr', 'pop5.pop', 'pop5pass',
				'smtp5.smtp', 'smtp5pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (3, 'mail6@mail.fr', 'pop6.pop', 'pop6pass',
				'smtp6.smtp', 'smtp6pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (3, 'mail7@mail.fr', 'pop7.pop', 'pop7pass',
				'smtp7.smtp', 'smtp7pass');

------------------------ WORKSTATION ------------------------
INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(3, 'Fixe', 'ASUS', '2006-06-05',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(3, 'Fixe', 'ASUS', '2006-06-06',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(3, 'Fixe', 'ASUS', '2006-06-07',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

-------------------------- SOFTWARE -------------------------
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (3, 'Software5', 'version.1.05',
		'SOFTWARE_LICENCE5', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (3, 'Software6', 'version.1.06',
		'SOFTWARE_LICENCE6', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (3, 'Software7', 'version.1.07',
		'SOFTWARE_LICENCE7', 'Software Corp. ©', 1);


------------------------- CLIENT 4 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, ID_FILE_PATH, "NAME", ADDRESS, POSTALCODE,
				PHONE, FAX, MAIL, INTERVENTION_TYPE,
				OBSERVATIONS) 
	VALUES (
		NULL,
		4,
		'SOCIETY4',
		'SOCIETY CITY 4',
                44444,
		'02.00.00.00.04',
		NULL,
		'society_4@society.fr',
		'SA',
		NULL);
		
-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (4, 14, 'USER8', '06.00.00.00.08', 'Utilisateur 8');
UPDATE ROOT.CLIENT SET "ID_USER" = 8 WHERE ID = 4;

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (4, 15, 'USER9', '06.00.00.00.09', 'Utilisateur 9');

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (4, 16, 'USER10', '06.00.00.00.10', 'Utilisateur 10');
	
--------------------------- MAIL ----------------------------
INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (4, 'mail8@mail.fr', 'pop8.pop', 'pop8pass',
				'smtp8.smtp', 'smtp8pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (4, 'mail9@mail.fr', 'pop9.pop', 'pop9pass',
				'smtp9.smtp', 'smtp9pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (4, 'mail10@mail.fr', 'pop10.pop', 'pop10pass',
				'smtp10.smtp', 'smtp10pass');

------------------------ WORKSTATION ------------------------
INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(4, 'Fixe', 'ASUS', '2006-06-08',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(4, 'Fixe', 'ASUS', '2006-06-09',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(4, 'Fixe', 'ASUS', '2006-06-10',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

-------------------------- SOFTWARE -------------------------
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (4, 'Software8', 'version.1.08',
		'SOFTWARE_LICENCE8', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (4, 'Software9', 'version.1.09',
		'SOFTWARE_LICENCE9', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (4, 'Software10', 'version.1.10',
		'SOFTWARE_LICENCE10', 'Software Corp. ©', 1);


------------------------- CLIENT 5 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, ID_FILE_PATH, "NAME", ADDRESS, POSTALCODE,
				PHONE, FAX, MAIL, INTERVENTION_TYPE,
				OBSERVATIONS) 
	VALUES (
		NULL,
		5,
		'SOCIETY5',
		'SOCIETY CITY 5',
                55555,
		'02.00.00.00.05',
		NULL,
		'society_5@society.fr',
		'SA',
		NULL);

-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (5, 17, 'USER11', '06.00.00.00.11', 'Utilisateur 11');
UPDATE ROOT.CLIENT SET "ID_USER" = 11 WHERE ID = 5;

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (5, 18, 'USER12', '06.00.00.00.12', 'Utilisateur 12');

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (5, 19, 'USER13', '06.00.00.00.13', 'Utilisateur 13');
	
--------------------------- MAIL ----------------------------
INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (5, 'mail11@mail.fr', 'pop11.pop', 'pop11pass',
				'smtp11.smtp', 'smtp11pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (5, 'mail12@mail.fr', 'pop12.pop', 'pop12pass',
				'smtp12.smtp', 'smtp12pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (5, 'mail13@mail.fr', 'pop13.pop', 'pop13pass',
				'smtp13.smtp', 'smtp13pass');

------------------------ WORKSTATION ------------------------
INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(5, 'Fixe', 'ASUS', '2006-06-11',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(5, 'Fixe', 'ASUS', '2006-06-12',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(5, 'Fixe', 'ASUS', '2006-06-13',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

-------------------------- SOFTWARE -------------------------
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (5, 'Software11', 'version.1.11',
		'SOFTWARE_LICENCE11', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (5, 'Software12', 'version.1.12',
		'SOFTWARE_LICENCE12', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (5, 'Software13', 'version.1.13',
		'SOFTWARE_LICENCE13', 'Software Corp. ©', 1);


------------------------- CLIENT 6 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, ID_FILE_PATH, "NAME", ADDRESS, POSTALCODE,
				PHONE, FAX, MAIL, INTERVENTION_TYPE,
				OBSERVATIONS) 
	VALUES (
		NULL,
		6,
		'SOCIETY6',
		'SOCIETY CITY 6',
                666666,
		'02.00.00.00.06',
		NULL,
		'society_6@society.fr',
		'SA',
		NULL);

-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (6, 20, 'USER14', '06.00.00.00.14', 'Utilisateur 14');
UPDATE ROOT.CLIENT SET "ID_USER" = 14 WHERE ID = 6;

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (6, 21, 'USER15', '06.00.00.00.15', 'Utilisateur 15');

INSERT INTO ROOT.C_USER
		(ID_CLIENT, ID_FILE_PATH, "NAME", PHONE, OBSERVATIONS) 
	VALUES (6, 22, 'USER16', '06.00.00.00.16', 'Utilisateur 16');
	
--------------------------- MAIL ----------------------------
INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (6, 'mail14@mail.fr', 'pop14.pop', 'pop14pass',
				'smtp14.smtp', 'smtp14pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (6, 'mail15@mail.fr', 'pop15.pop', 'pop15pass',
				'smtp15.smtp', 'smtp15pass');

INSERT INTO ROOT.MAIL
		(ID_CLIENT, MAIL, POP, POP_PASSWORD, SMTP, SMTP_PASSWORD) 
	VALUES (6, 'mail16@mail.fr', 'pop16.pop', 'pop16pass',
				'smtp16.smtp', 'smtp16pass');

------------------------ WORKSTATION ------------------------
INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(6, 'Fixe', 'ASUS', '2006-06-14',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(6, 'Fixe', 'ASUS', '2006-06-15',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

INSERT INTO ROOT.WORKSTATION
		(ID_CLIENT, WS_TYPE, BRAND, START_DATE, PROCESSOR, MONITOR,
				VIDEO_CARD, OPERATING_SYSTEM, RAM, HARD_DRIVE) 
	VALUES
		(6, 'Fixe', 'ASUS', '2006-06-16',
		'Intel Core 2 duo E8600 @2x3,30GHz', 'Philips 19"',
		'NVIDIA GeForce 9800 GTX+ @ 1768Mo', 'UBUNTU 12.10 LTS x64',
		'4Go - 2x2Go @ 800MHz', '320Go SEAGATE @ 7200t/m');

-------------------------- SOFTWARE -------------------------
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (6, 'Software14', 'version.1.14',
		'SOFTWARE_LICENCE14', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (6, 'Software15', 'version.1.15',
		'SOFTWARE_LICENCE15', 'Software Corp. ©', 1);
		
INSERT INTO ROOT.SOFTWARE
		(ID_CLIENT, "NAME", VERSION, LICENSE,
				EDITOR, STATION_NUMBER) 
	VALUES (6, 'Software16', 'version.1.16',
		'SOFTWARE_LICENCE16', 'Software Corp. ©', 1);

------------------- INTERVENTIONS - TASKS -------------------
-- Insertion des tâches et des interventions

----------------------- INTERVENTIONS -----------------------
INSERT INTO ROOT.TASK (ID_CLIENT, ID_USER, ID_WORKSTATION,
		DESCRIPTION, START_DATE, INTENDED_DURATION,
		DEPLACEMENT, INTERVENTION_TYPE) 
	VALUES (2, 2, 2, 'Task description 2',
		'2013-06-02', '01 h 00 mins', true, 'MATERIELLE');

INSERT INTO ROOT.TASK (ID_CLIENT, ID_USER, ID_WORKSTATION,
		DESCRIPTION, START_DATE, INTENDED_DURATION,
		DEPLACEMENT, INTERVENTION_TYPE) 
	VALUES (3, 5, 5, 'Task description 3',
		'2013-06-03', '01 h 00 mins', true, 'MATERIELLE');

INSERT INTO ROOT.TASK (ID_CLIENT, ID_USER, ID_WORKSTATION,
		DESCRIPTION, START_DATE, INTENDED_DURATION,
		DEPLACEMENT, INTERVENTION_TYPE) 
	VALUES (4, 8, 8, 'Task description 4',
		'2013-06-04', '01 h 00 mins', true, 'MATERIELLE');

INSERT INTO ROOT.TASK (ID_CLIENT, ID_USER, ID_WORKSTATION,
		DESCRIPTION, START_DATE, INTENDED_DURATION,
		DEPLACEMENT, INTERVENTION_TYPE) 
	VALUES (5, 11, 11, 'Task description 5',
		'2013-06-05', '01 h 00 mins', true, 'MATERIELLE');

INSERT INTO ROOT.TASK (ID_CLIENT, ID_USER, ID_WORKSTATION,
		DESCRIPTION, START_DATE, INTENDED_DURATION,
		DEPLACEMENT, INTERVENTION_TYPE) 
	VALUES (6, 14, 14, 'Task description 6',
		'2013-06-06', '01 h 00 mins', true, 'MATERIELLE');

-- File successfully loaded!
;
