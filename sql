CREATE TABLE IDENTITIES 
    (IDENTITIES_UID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT IDENTITIES_PK PRIMARY KEY,
    IDENTITIES_DISPLAYNAME VARCHAR(255),
    IDENTITIES_EMAIL VARCHAR(255),
    IDENTITIES_BIRTHDATE DATE
    );