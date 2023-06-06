-- -----------------------------------------------------
-- Schema travel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `travel` ;

-- -----------------------------------------------------
-- Table `travel`.`destination`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel`.`destination` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))

-- -----------------------------------------------------
-- Table `travel`.`itinerary`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel`.`itinerary` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `destination_id` INT NULL DEFAULT NULL,
  `plan` TEXT NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `destination_id` (`destination_id` ASC) VISIBLE,
  CONSTRAINT `itinerary_ibfk_1`
    FOREIGN KEY (`destination_id`)
    REFERENCES `travel`.`destination` (`id`))