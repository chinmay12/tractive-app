CREATE TABLE pets(
    id SERIAL PRIMARY KEY,
    name varchar(255),
    pet_type varchar(10),
    tracker_type varchar(10),
    owner_id int,
    in_zone boolean
);

ALTER TABLE pets
ADD CONSTRAINT PETS_OWNER_FK
FOREIGN KEY (owner_id) REFERENCES owners;