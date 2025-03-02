use evernorth; 

create table member (
	memberID varchar(12) PRIMARY KEY,
    email varchar(255) UNIQUE NOT NULL,
    active boolean default false
); 

create table user (
	userID INT AUTO_INCREMENT PRIMARY KEY,
	memberID varchar(12) UNIQUE NOT NULL,
    email varchar(255) UNIQUE NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name varchar(255),
    country_code varchar(5) NOT NULL,
    mobile_number varchar(10) NOT NULL,
    dob DATE NOT NULL,
    password varchar(255) NOT NULL,
    image varchar(255)  DEFAULT 'https://res.cloudinary.com/dkezdazmt/image/upload/v1735478208/Evernorth/evernorth_logo.png',
    role varchar(10) DEFAULT 'MEMBER',
    percentage DECIMAL(5, 2) DEFAULT 0 
);
ALTER TABLE user AUTO_INCREMENT=10000000;	

create table delivery_address (
	addressID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    address varchar(255),
    city varchar(255),
    state varchar(255),
    country varchar(255),
    postalCode varchar(255)
);


create table health_condition (
	healthConditionID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    healthCondition varchar(255) NOT NULL
);


create table allergy (
	allergyID INT AUTO_INCREMENT PRIMARY KEY,
    userID INT NOT NULL,
    allergy varchar(255) NOT NULL
);


-- create table payment_information (
-- 	paymentInformationID INT AUTO_INCREMENT PRIMARY KEY,
--     userID INT NOT NULL,
--     paymentInformation varchar


create table dependent (
	dependentID varchar(255) PRIMARY KEY,
    userID INT NOT NULL,
    firstName varchar(255) NOT NULL,
    lastName varchar(255),
    image varchar(255) NOT NULL DEFAULT 'https://res.cloudinary.com/dkezdazmt/image/upload/v1735478208/Evernorth/evernorth_logo.png'
);

create table dependent_health_condition (
	dependentConditionID INT AUTO_INCREMENT PRIMARY KEY,
    dependentID varchar(255) NOT NULL,
    healthCondition varchar(255) NOT NULL
);

create table dependent_allergy (
	dependentAllergyID INT AUTO_INCREMENT PRIMARY KEY,
    dependentID varchar(255) NOT NULL,
    dependentAllergy varchar(255) NOT NULL
);