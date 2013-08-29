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
ALTER TABLE ROOT.INTERVENTION DROP CONSTRAINT intervention_facture_id_fk;
DROP TABLE ROOT.FACTURE;
ALTER TABLE ROOT.INTERVENTION DROP CONSTRAINT intervention_task_id_fk;
DROP TABLE ROOT.INTERVENTION;
ALTER TABLE ROOT.TASK DROP CONSTRAINT task_c_user_id_fk;
ALTER TABLE ROOT.TASK DROP CONSTRAINT task_client_id_fk;
ALTER TABLE ROOT.TASK DROP CONSTRAINT task_device_id_fk;
DROP TABLE ROOT.TASK;
ALTER TABLE ROOT.CLIENT DROP CONSTRAINT client_c_user_id_fk;
ALTER TABLE ROOT.MAIL DROP CONSTRAINT mail_client_id_fk;
DROP TABLE ROOT.MAIL;
ALTER TABLE ROOT.DEVICE DROP CONSTRAINT device_file_path_id_fk;
ALTER TABLE ROOT.DEVICE DROP CONSTRAINT device_client_id_fk;
DROP TABLE ROOT.DEVICE;
ALTER TABLE ROOT.SOFTWARE DROP CONSTRAINT software_file_path_id_fk;
ALTER TABLE ROOT.SOFTWARE DROP CONSTRAINT software_client_id_fk;
DROP TABLE ROOT.SOFTWARE;
ALTER TABLE ROOT.C_USER DROP CONSTRAINT user_client_id_fk;
ALTER TABLE ROOT.C_USER DROP CONSTRAINT user_file_path_id_fk;
ALTER TABLE ROOT.CLIENT DROP CONSTRAINT client_file_path_id_fk;
DROP TABLE ROOT.CLIENT;
DROP TABLE ROOT.FILE_PATH;
DROP TABLE ROOT.C_USER;

--------------- Création de la table FILE_PATH --------------
-- TABLE:			FILE_PATH
-- DESCRIPTION: 	Table concernant un dossier de fichiers
--  associé à une entité.
CREATE TABLE ROOT.FILE_PATH (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		file_path VARCHAR(250) NOT NULL);

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
		id_file_path INTEGER,
        name VARCHAR(45) NOT NULL DEFAULT 'unknown' UNIQUE,
        address VARCHAR(100) NOT NULL,
		postalCode INTEGER NOT NULL,
		city VARCHAR(45) NOT NULL,
        phone VARCHAR(14) NOT NULL,
        fax VARCHAR(14),
		tarif DOUBLE,
		deplacement DOUBLE,
        mail VARCHAR(30) NOT NULL,
        intervention_type VARCHAR(250),
		internet_operator VARCHAR(30),
		internet_login VARCHAR(30),
		internet_password VARCHAR(64),
		internet_dns VARCHAR(64),
		internet_dns_login VARCHAR(30),
		internet_dns_password VARCHAR(64),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT client_file_path_id_fk
                FOREIGN KEY (id_file_path)
                REFERENCES ROOT.FILE_PATH(id));

---------------- Création de la table C_USER ----------------
-- TABLE:			C_USER
-- DESCRIPTION: 	Table concernant les utilisateurs
--  appartenant à une société
CREATE TABLE ROOT.C_USER (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
        id_client INTEGER NOT NULL,
		id_file_path INTEGER,
        name VARCHAR(45) NOT NULL DEFAULT 'unnamed',
        direct_phone VARCHAR(14),
		position VARCHAR(30),
		login VARCHAR(30),
		password VARCHAR(64),
        mails VARCHAR(250),
        phone VARCHAR(14),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT user_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id),
        CONSTRAINT user_file_path_id_fk
                FOREIGN KEY (id_file_path)
                REFERENCES ROOT.FILE_PATH(id));


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
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT mail_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id));

---------------- Création de la table DEVICE ----------------
-- TABLE:			DEVICE
-- DESCRIPTION: 	Table concernant les périphériques
--  utilisés par les utilistaurs dans une société.
CREATE TABLE ROOT.DEVICE (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		id_file_path INTEGER,
		ws_type VARCHAR(64),
		name VARCHAR(64) NOT NULL,
		user_name_default VARCHAR(30),
		ip_address VARCHAR(39),
        brand VARCHAR(64),
        start_date DATE,
		processor VARCHAR(64),
		monitor VARCHAR(64),
		video_card VARCHAR(64),
		operating_system VARCHAR(64),
		system_version VARCHAR(64),
		system_license VARCHAR(64),
		ram VARCHAR(64),
		hard_drive VARCHAR(64),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT device_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id),
        CONSTRAINT device_file_path_id_fk
                FOREIGN KEY (id_file_path)
                REFERENCES ROOT.FILE_PATH(id));

--------------- Création de la table SERVICES ---------------
-- TABLE:			SERVICES
-- DESCRIPTION: 	Table concernant les services
--  associés à une société.
CREATE TABLE ROOT.SERVICES (
		id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		title VARCHAR(64),
		description VARCHAR(1024),
		CONSTRAINT services_client_id_pk
		FOREIGN KEY(id_client)
		REFERENCES ROOT.CLIENT(id));

--------------- Création de la table SOFTWARE ---------------
-- TABLE:			SOFTWARE
-- DESCRIPTION: 	Table concernant les logiciels utilisés
--  par un client ou une société.
CREATE TABLE ROOT.SOFTWARE (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		id_file_path INTEGER,
		name VARCHAR(64),
        version VARCHAR(64),
        license VARCHAR(64),
        serial_number VARCHAR(64),
		start_date DATE,
		editor VARCHAR(64),
        observations VARCHAR(250),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
        CONSTRAINT software_client_id_fk
                FOREIGN KEY (id_client)
                REFERENCES ROOT.CLIENT(id),
        CONSTRAINT software_file_path_id_fk
                FOREIGN KEY (id_file_path)
                REFERENCES ROOT.FILE_PATH(id));

----------------- Création de la table TASK -----------------
-- TABLE:			TASK
-- DESCRIPTION: 	Table concernant les tâches à effectuer
--  ou déjà effectuées.
CREATE TABLE ROOT.TASK (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_client INTEGER NOT NULL,
		id_user INTEGER,
		id_device INTEGER,
		description VARCHAR(250),
        start_date DATE,
        intended_duration DOUBLE,
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
        CONSTRAINT task_device_id_fk
                FOREIGN KEY (id_device)
                REFERENCES ROOT.DEVICE(id));

---------------- Création de la table FACTURE ---------------
-- TABLE:			FACTURE
-- DESCRIPTION: 	Table concernant les factures des
--  interventions.
CREATE TABLE ROOT.FACTURE (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		facture_number VARCHAR(20),
		sleeping BOOLEAN NOT NULL DEFAULT FALSE);

------------- Création de la table INTERVENTION -------------
-- TABLE:			INTERVENTION
-- DESCRIPTION: 	Table concernant les interventions
--  effectuées.
CREATE TABLE ROOT.INTERVENTION (
        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
		(START WITH 1, INCREMENT BY 1),
		id_task INTEGER NOT NULL,
		id_facture INTEGER,
        intervention_date DATE,
		duration DOUBLE,
		deplacement BOOLEAN DEFAULT FALSE,
		sleeping BOOLEAN NOT NULL DEFAULT FALSE,
		CONSTRAINT intervention_task_id_fk
			FOREIGN KEY (id_task)
			REFERENCES ROOT.TASK(id),
		CONSTRAINT intervention_facture_id_fk
			FOREIGN KEY (id_facture)
			REFERENCES ROOT.FACTURE(id));

-- File successfully loaded!
;
