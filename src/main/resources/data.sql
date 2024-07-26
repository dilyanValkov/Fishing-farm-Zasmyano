ALTER TABLE `zasmyanodb`.`users_roles`
ADD PRIMARY KEY (`user_id`, `roles_id`);

INSERT INTO `zasmyanodb`.`roles` (`id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `zasmyanodb`.`roles` (`id`, `role`) VALUES ('2', 'USER');

INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('1', '2', '80');
INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('2', '2', '80');
INSERT INTO zasmyanodb.bungalows (`id`, `capacity`, `price`) VALUES ('3', '4', '80');


INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('2', '2', '40', '25', 'Налична сянка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('3', '3', '40', '25', 'Налична сянка.Разполага с маса и пейка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('1', '3', '40', '25', 'Разполага с маса и пейка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('4', '2', '40', '25', 'Разполага с маса и пейка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('5', '2', '40', '25', 'Налична сянка.Разполага с маса и пейка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('6', '3', '40', '25', 'Налична сянка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('7', '4', '40', '25', 'Разполага с маса и пейка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('8', '3', '40', '25', 'Разполага с маса и пейка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('9', '3', '40', '25', 'Налична сянка.');
INSERT INTO `zasmyanodb`.`fishing_spots` (`id`, `capacity`, `day_and_night_price`, `day_price`, `description`) VALUES ('10', '3', '40', '25', 'С платформа над водата');
INSERT INTO `zasmyanodb`.`users` (`id`, `attitude`, `email`, `first_name`, `last_name`, `password`, `phone_number`) VALUES ('1', '4', 'dilqnvalkov@gmail.com', 'Дилян ', 'Вълков', 'feeffb9ad3e174f61de2dfc5179a9a959799046de5633a8136174c573c4c61d5c30fa955bf075c90c3a17dc2541f114a', '0899363327');

INSERT INTO `zasmyanodb`.`users_roles` (`user_id`, `roles_id`) VALUES ('1', '1');