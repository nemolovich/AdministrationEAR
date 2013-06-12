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
-- ALTER TABLE ROOT.CLIENT DROP CONSTRAINT client_c_user_id_fk;
-- DELETE FROM ROOT.C_USER;
-- ALTER TABLE ROOT.C_USER ALTER COLUMN ID RESTART WITH 1;
-- DELETE FROM ROOT.MAIL;
-- ALTER TABLE ROOT.MAIL ALTER COLUMN ID RESTART WITH 1;
-- DELETE FROM ROOT.WORKSTATION;
-- ALTER TABLE ROOT.WORKSTATION ALTER COLUMN ID RESTART WITH 1;
-- DELETE FROM ROOT.SOFTWARE;
-- ALTER TABLE ROOT.SOFTWARE ALTER COLUMN ID RESTART WITH 1;
-- DELETE FROM ROOT.CLIENT;
-- ALTER TABLE ROOT.CLIENT ALTER COLUMN ID RESTART WITH 1;
-- ALTER TABLE ROOT.CLIENT ADD CONSTRAINT client_c_user_id_fk
--                 FOREIGN KEY (id_user)
--                 REFERENCES ROOT.C_USER(id);

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

---------------------- INITIALISATION -----------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, "NAME", ADDRESS, POSTALCODE, PHONE, 
                FAX, MAIL, INTERVENTION_TYPE, OBSERVATIONS) 
	VALUES (
		NULL,
		'Admin Services',
		'La Ville aux Dames',
                37700,
		'02.47.44.77.11',
		NULL,
		'contact@admin-services.fr',
		'SA',
		NULL);
		
INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (1, 'MINNE', '06.78.45.28.82', 'MINNE HOEVE');

UPDATE ROOT.CLIENT SET "ID_USER" = 1 WHERE ID = 1;
	

------------------------- CLIENT 2 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, "NAME", ADDRESS, POSTALCODE, PHONE, 
                FAX, MAIL, INTERVENTION_TYPE, OBSERVATIONS) 
	VALUES (
		NULL,
		'SOCIETY2',
		'SOCIETY CITY 2',
                22222,
		'02.00.00.00.02',
		NULL,
		'society_2@society.fr',
		'SA',
		NULL);
		
-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (2, 'USER2', '06.00.00.00.02', 'Utilisateur 2');
UPDATE ROOT.CLIENT SET "ID_USER" = 2 WHERE ID = 2;

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (2, 'USER3', '06.00.00.00.03', 'Utilisateur 3');

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (2, 'USER4', '06.00.00.00.04', 'Utilisateur 4');

------------------------- CLIENT 3 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, "NAME", ADDRESS, POSTALCODE, PHONE, 
                FAX, MAIL, INTERVENTION_TYPE, OBSERVATIONS) 
	VALUES (
		NULL,
		'SOCIETY3',
		'SOCIETY CITY 3',
                33333,
		'02.00.00.00.03',
		NULL,
		'society_3@society.fr',
		'SA',
		NULL);
		
-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (3, 'USER5', '06.00.00.00.05', 'Utilisateur 5');
UPDATE ROOT.CLIENT SET "ID_USER" = 5 WHERE ID = 3;

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (3, 'USER6', '06.00.00.00.06', 'Utilisateur 6');

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (3, 'USER7', '06.00.00.00.07', 'Utilisateur 7');

------------------------- CLIENT 4 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, "NAME", ADDRESS, POSTALCODE, PHONE, 
                FAX, MAIL, INTERVENTION_TYPE, OBSERVATIONS) 
	VALUES (
		NULL,
		'SOCIETY4',
		'SOCIETY CITY 4',
                44444,
		'02.00.00.00.04',
		NULL,
		'society_4@society.fr',
		'SA',
		NULL);
		
INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (4, 'USER8', '06.00.00.00.08', 'Utilisateur 8');
UPDATE ROOT.CLIENT SET "ID_USER" = 8 WHERE ID = 4;

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (4, 'USER9', '06.00.00.00.09', 'Utilisateur 9');

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (4, 'USER10', '06.00.00.00.10', 'Utilisateur 10');

------------------------- CLIENT 5 --------------------------
INSERT INTO ROOT.CLIENT
		(ID_USER, "NAME", ADDRESS, POSTALCODE, PHONE, 
                FAX, MAIL, INTERVENTION_TYPE, OBSERVATIONS) 
	VALUES (
		NULL,
		'SOCIETY5',
		'SOCIETY CITY 5',
                55555,
		'02.00.00.00.05',
		NULL,
		'society_5@society.fr',
		'SA',
		NULL);

-------------------------- C_USER ---------------------------
INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (5, 'USER11', '06.00.00.00.11', 'Utilisateur 11');
UPDATE ROOT.CLIENT SET "ID_USER" = 11 WHERE ID = 5;

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (5, 'USER12', '06.00.00.00.12', 'Utilisateur 12');

INSERT INTO ROOT.C_USER (ID_CLIENT, "NAME", PHONE, OBSERVATIONS)  
	VALUES (5, 'USER13', '06.00.00.00.13', 'Utilisateur 13');

-- File successfully loaded!
;