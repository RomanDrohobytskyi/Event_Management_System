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