-- Running file...
-- .=====================================================. --
-- |              CREATION DE LA STRUCTURE               | --
-- |               DE LA BASE DE DONNEES                 | --
-- '=====================================================' --
-------------------------------------------------------------
-- DATABSE: 		administration_db [root on ROOT]
-- URL:				jdbc:derby://localhost/administration_db
-- AUTHOR:			Brian GOHIER
-- DATE:			2013-05-22 22:05
-------------------------------------------------------------

--- Supprime les tables si elles existent, sinon commenter --
-- DROP TABLE ROOT.T_USER;
ALTER TABLE ROOT.FACTURE DROP CONSTRAINT facture_intervention_id_fk;
DROP TABLE ROOT.FACTURE;
ALTER TABLE ROOT.INTERVENTION DROP CONSTRAINT intervention_task_id_fk;
DROP TABLE ROOT.INTERVENTION;
ALTER TABLE ROOT.TASK DROP CONSTRAINT task_c_user_id_fk;
ALTER TABLE ROOT.TASK DROP CONSTRAINT task_client_id_fk;
ALTER TABLE ROOT.TASK DROP CONSTRAINT task_workstation_id_fk;
DROP TABLE ROOT.TASK;
ALTER TABLE ROOT.CLIENT DROP CONSTRAINT client_c_user_id_fk;
ALTER TABLE ROOT.MAIL DROP CONSTRAINT mail_client_id_fk;
DROP TABLE ROOT.MAIL;
ALTER TABLE ROOT.WORKSTATION DROP CONSTRAINT workstation_client_id_fk;
DROP TABLE ROOT.WORKSTATION;
ALTER TABLE ROOT.SOFTWARE DROP CONSTRAINT software_client_id_fk;
DROP TABLE ROOT.SOFTWARE;
ALTER TABLE ROOT.C_USER DROP CONSTRAINT user_client_id_fk;
DROP TABLE ROOT.CLIENT;
DROP TABLE ROOT.C_USER;

---------------- Création de la table T_USER ----------------
-- TABLE:			T_USER
-- DESCRIPTION: 	Table concernant les utilisateurs
-- 	du site, ceux qui ont accès aux données de la base
-- 	de données.
--  CREATE TABLE ROOT.T_USER (
--  	id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
--  		(START WITH 1, INCREMENT BY 1),
--  	mail VARCHAR(64) NOT NULL UNIQUE,
--  	name VARCHAR(30) NOT NULL,
--  	firstname VARCHAR(30) NOT NULL,
--  	rights VARCHAR(32) NOT NULL DEFAULT 'UNKNOWN'
--  	CONSTRAINT rights_ck CHECK (rights IN
--  		('UNKNOWN','USER','ADMIN')),
--  	password CHAR(32) NOT NULL
--  	);

---------------- Création de la table CLIENT ----------------
-- TABLE:			CLIENT
-- DESCRIPTION: 	Table concernant les clients ou les
--  sociétés qui requierent des services.
CREATE TABLE ROOT.CLIENT (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
        id_user INTEGER,
        name VARCHAR(45) NOT NULL DEFAULT 'unknown' UNIQUE,
        address VARCHAR(100) NOT NULL,
		postalCode INTEGER NOT NULL,
        phone VARCHAR(14) NOT NULL,
        fax VARCHAR(14),
		tarif DOUBLE,
		deplacement DOUBLE,
        mail VARCHAR(30) NOT NULL,
        intervention_type VARCHAR(250),
		operator VARCHAR(30),
		internet_login VARCHAR(30),
		internet_password VARCHAR(64),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE);

---------------- Création de la table C_USER ----------------
-- TABLE:			C_USER
-- DESCRIPTION: 	Table concernant les utilisateurs
--  appartenant à une société
CREATE TABLE ROOT.C_USER (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
        id_client INTEGER NOT NULL,
        name VARCHAR(45) NOT NULL DEFAULT 'unnamed',
        phone VARCHAR(14) NOT NULL,
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT user_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id));


-------------- Modification de la table C_USER --------------
-- DESCRIPTION: 	Ajoute le contrainte que l'interlocuteur
--  doit être un client sur la table ROOT.CLIENT.
ALTER TABLE ROOT.CLIENT ADD CONSTRAINT client_c_user_id_fk
                FOREIGN KEY (id_user)
                REFERENCES ROOT.C_USER(id);

----------------- Création de la table MAIL -----------------
-- TABLE:			MAIL
-- DESCRIPTION: 	Table concernant les mails associés à
--  des utilisateurs dans une société.
CREATE TABLE ROOT.MAIL (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		mail VARCHAR(64) NOT NULL UNIQUE,
        pop VARCHAR(64),
        pop_password VARCHAR(64),
        smtp VARCHAR(64),
        smtp_password VARCHAR(64),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT mail_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id));

------------- Création de la table WORKSTATION --------------
-- TABLE:			WORKSTATION
-- DESCRIPTION: 	Table concernant les postes de travail
--  utilisés par les utilistaurs dans une société.
CREATE TABLE ROOT.WORKSTATION (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		ws_type VARCHAR(64),
        brand VARCHAR(64) NOT NULL,
        start_date DATE,
		processor VARCHAR(64),
		monitor VARCHAR(64),
		video_card VARCHAR(64),
		operating_system VARCHAR(64),
		ram VARCHAR(64),
		hard_drive VARCHAR(64),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT workstation_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id));

--------------- Création de la table SOFTWARE ---------------
-- TABLE:			SOFTWARE
-- DESCRIPTION: 	Table concernant les logiciels utilisés
--  par un client ou une société.
CREATE TABLE ROOT.SOFTWARE (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		name VARCHAR(64),
        version VARCHAR(64),
        license VARCHAR(64),
		editor VARCHAR(64),
		station_number INTEGER,
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT software_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id));

----------------- Création de la table TASK -----------------
-- TABLE:			TASK
-- DESCRIPTION: 	Table concernant les tâches à effectuer
--  ou déjà effectuées.
CREATE TABLE ROOT.TASK (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		id_user INTEGER,
		id_workstation INTEGER,
		description VARCHAR(250),
        intended_duration VARCHAR(30),
		deplacement BOOLEAN DEFAULT FALSE,
		intervention_type VARCHAR(10),
		CONSTRAINT intervention_type_ck
			CHECK(intervention_type IN
				('MATERIELLE','LOGICIELLE')),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT task_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id),
        CONSTRAINT task_c_user_id_fk
                FOREIGN KEY (id_user)
                REFERENCES ROOT.C_USER(id),
        CONSTRAINT task_workstation_id_fk
                FOREIGN KEY (id_workstation)
                REFERENCES ROOT.WORKSTATION(id));

------------- Création de la table INTERVENTION -------------
-- TABLE:			INTERVENTION
-- DESCRIPTION: 	Table concernant les interventions
--  effectuées.
CREATE TABLE ROOT.INTERVENTION (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_task INTEGER NOT NULL,
		duration VARCHAR(30),
		facture_number INTEGER,
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
		CONSTRAINT intervention_task_id_fk
			FOREIGN KEY (id_task)
			REFERENCES ROOT.TASK(id));

---------------- Création de la table FACTURE ---------------
-- TABLE:			FACTURE
-- DESCRIPTION: 	Table concernant les factures des
--  interventions.
CREATE TABLE ROOT.FACTURE (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_intervention INTEGER NOT NULL,
		facture_number VARCHAR(7),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
		CONSTRAINT facture_intervention_id_fk
			FOREIGN KEY (id_intervention)
			REFERENCES ROOT.INTERVENTION(id));

-- File successfully loaded!
;
