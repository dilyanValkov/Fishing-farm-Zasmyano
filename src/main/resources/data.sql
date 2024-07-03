ALTER TABLE `zasmyanodb`.`users_roles`
ADD PRIMARY KEY (`user_id`, `roles_id`);

INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('1', '2', '80');
INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('2', '2', '80');
INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('3', '4', '80');

INSERT INTO `zasmyanodb`.`lakes` (`id`, `description`, `facebook_url`, `fishing_rules`, `latitude`, `longitude`, `name`, `phone_number`) VALUES ('1', 'Язовир засмяно на 25 км то Варна', 'https://www.facebook.com/ZasmyanoDam', 'Внимавайте', '43.39904530919316', '27.719879150192654', 'Язовир Засмяно', '+359879111751');

INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('1', '3', '40', '25', 'Място 1', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('2', '3', '40', '25', 'Място 2', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('3', '3', '40', '25', 'Място 3', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('4', '2', '40', '25', 'Място 4', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('5', '2', '40', '25', 'Място 5', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('6', '3', '40', '25', 'Място 6', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('7', '4', '40', '25', 'Място 7', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('8', '3', '40', '25', 'Място 8', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('9', '3', '40', '25', 'Място 9', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('10', '3', '40', '25', 'Място 10', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('11', '2', '40', '25', 'Място 11', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('12', '3', '40', '25', 'Място 12', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('13', '3', '40', '25', 'Място 13', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('14', '2', '40', '25', 'Място 14', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('15', '3', '40', '25', 'Място 15', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('16', '2', '40', '25', 'Място 16', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('17', '3', '40', '25', 'Място 17', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('18', '3', '40', '25', 'Място 18', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('19', '2', '40', '25', 'Място 19', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('20', '2', '40', '25', 'Място 20', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('21', '3', '40', '25', 'Място 21', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('22', '3', '40', '25', 'Място 22', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('23', '4', '40', '25', 'Място 23', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('24', '3', '40', '25', 'Място 24', '1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`, `lake_id`) VALUES ('25', '4', '40', '25', 'Място 25', '1');
