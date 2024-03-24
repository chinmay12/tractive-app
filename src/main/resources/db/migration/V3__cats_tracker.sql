CREATE TABLE cats_tracker(
    pet_id int PRIMARY KEY,
    lost_tracker boolean
);

ALTER TABLE cats_tracker
ADD CONSTRAINT CATS_TRACKER_FK
FOREIGN KEY (pet_id) REFERENCES pets(id);