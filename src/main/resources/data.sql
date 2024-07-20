ALTER TABLE `zasmyanodb`.`users_roles`
ADD PRIMARY KEY (`user_id`, `roles_id`);

INSERT INTO `zasmyanodb`.`roles` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `zasmyanodb`.`roles` (`id`, `role`) VALUES ('2', 'USER');

INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('1', '2', '80');
INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('2', '2', '80');
INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('3', '4', '80');


INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('2', '3', '40', '25', 'Място 2');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('3', '3', '40', '25', 'Място 3');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('1', '3', '40', '25', 'Място 1');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('4', '2', '40', '25', 'Място 4');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('5', '2', '40', '25', 'Място 5');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('6', '3', '40', '25', 'Място 6');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('7', '4', '40', '25', 'Място 7');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('8', '3', '40', '25', 'Място 8');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('9', '3', '40', '25', 'Място 9');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('10', '3', '40', '25', 'Място 10');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('11', '2', '40', '25', 'Място 11');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('12', '3', '40', '25', 'Място 12');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('13', '3', '40', '25', 'Място 13');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('14', '2', '40', '25', 'Място 14');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('15', '3', '40', '25', 'Място 15');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('16', '2', '40', '25', 'Място 16');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('17', '3', '40', '25', 'Място 17');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('18', '3', '40', '25', 'Място 18');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('19', '2', '40', '25', 'Място 19');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('20', '2', '40', '25', 'Място 20');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('21', '3', '40', '25', 'Място 21');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('22', '3', '40', '25', 'Място 22');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('23', '4', '40', '25', 'Място 23');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('24', '3', '40', '25', 'Място 24');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('25', '4', '40', '25', 'Място 25');

INSERT INTO `zasmyanodb`.`users` (`id`, `attitude`, `email`, `first_name`, `last_name`, `password`, `phone_number`) VALUES ('1', '4', 'dilqnvalkov@gmail.com', 'Дилян ', 'Вълков', 'feeffb9ad3e174f61de2dfc5179a9a959799046de5633a8136174c573c4c61d5c30fa955bf075c90c3a17dc2541f114a', '0899363327');
INSERT INTO `zasmyanodb`.`users_roles` (`user_id`, `roles_id`) VALUES ('1', '1');