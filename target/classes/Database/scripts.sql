-- -----------------------------------------------------
-- Table 'user'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user (
  id BIGINT(20) NOT NULL,
  activation_code VARCHAR(255) NULL DEFAULT NULL,
  active BIT(1) NOT NULL,
  email VARCHAR(255) NOT NULL,
  first_name VARCHAR(34) NOT NULL,
  last_name VARCHAR(42) NOT NULL,
  password VARCHAR(255) NOT NULL,
  username VARCHAR(24) NOT NULL,
  PRIMARY KEY (`id`));



-- -----------------------------------------------------
-- Table 'user_role'
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS user_role (
  id BIGINT(20) NOT NULL,
  user_id BIGINT(20) NOT NULL,
  role_id BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`));
  
  -- -----------------------------------------------------
-- Table 'role'
-- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS role (
  id BIGINT(20) NOT NULL,
  role VARCHAR(34) NOT NULL,
  PRIMARY KEY (`id`));
  -- -----------------------------------------------------
-- default values for role table
-- -----------------------------------------------------
  insert into role (role)
  values ('ADMIN');

  insert into role (role)
  values ('USER');

-- -----------------------------------------------------
-- Table 'event'
-- -----------------------------------------------------
  CREATE TABLE event (
    id bigint(20) NOT NULL,
    creation_date datetime(6) DEFAULT NULL,
    date datetime(6) DEFAULT NULL,
    day_of_week varchar(255) DEFAULT NULL,
    starts_from time DEFAULT NULL,
    modification_date datetime(6) DEFAULT NULL,
    title varchar(255) DEFAULT NULL,
    end_to time DEFAULT NULL,
    user_id bigint(20) DEFAULT NULL,
    creator_id bigint(20) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY user_id_fk (user_id),
  KEY creator_id_fk (creator_id),
  CONSTRAINT creator_id_fk FOREIGN KEY (creator_id) REFERENCES user (id),
  CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user (id)
  );

-- -----------------------------------------------------
-- Many to many - user - event tables
-- -----------------------------------------------------

CREATE TABLE event_participants (
  events_id bigint(20) NOT NULL,
  participants_id bigint(20) NOT NULL,
  PRIMARY KEY (events_id, participants_id),
  KEY participants_id_fk (participants_id),
CONSTRAINT participants_id_fk FOREIGN KEY (participants_id) REFERENCES user (id),
CONSTRAINT events_id_fk FOREIGN KEY (events_id) REFERENCES event (id)
);
