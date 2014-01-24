SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


DROP TABLE IF EXISTS `applicants`;
CREATE TABLE IF NOT EXISTS `applicants` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `sex` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `resume_file` varchar(255) DEFAULT NULL,
  `test_answer_file` varchar(255) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'IN_PROGRESS',
  `viewed` tinyint(1) NOT NULL DEFAULT '0',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `deal` (`deal_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;


INSERT INTO `applicants` (`id`, `deal_id`, `first_name`, `last_name`, `description`, `sex`, `age`, `resume_file`, `test_answer_file`, `status`, `viewed`, `updated_date`) VALUES
(1, 1, 'Александр', 'Иванов', 'Программирую даже во сне', 'Мужской', 34, '#', '#', 'IN_PROGRESS', 1, '2014-01-22 04:17:39'),
(2, 1, 'Павел', 'Никифоров', 'Круто программирует на всём', 'Мужской', 45, '#', '#', 'IN_PROGRESS', 1, '2014-01-22 04:17:40'),
(3, 2, 'Михаил', 'Луценко', 'Чиню любые трубы: пластик', 'Мужской', 42, '#', '#', 'IN_PROGRESS', 0, '2014-01-22 04:13:37'),
(4, 3, 'Афанасий', 'Афанасьев', 'фывавы', NULL, NULL, '', '', 'IN_PROGRESS', 0, '2014-01-22 04:15:41'),
(5, 3, 'Михаил', 'Платонов', 'Аккак', NULL, NULL, '', '', 'IN_PROGRESS', 0, '2014-01-22 04:15:53'),
(6, 1, 'Игорь', 'Вавилов', 'Папаапап', NULL, NULL, '', '', 'IN_PROGRESS', 0, '2014-01-22 04:16:14'),
(7, 1, 'Олег', 'Кошевой', 'ываыва', NULL, NULL, '', '', 'REJECTED', 1, '2014-01-22 04:18:08'),
(8, 1, 'Константин', 'Никольский', 'ыаыв', NULL, NULL, '', '', 'IN_PROGRESS', 0, '2014-01-22 04:16:36');

-- --------------------------------------------------------



DROP TABLE IF EXISTS `bids`;
CREATE TABLE IF NOT EXISTS `bids` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vacancy_id` bigint(20) NOT NULL,
  `recruiter_id` bigint(20) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'ACTIVE',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `vacancy_id` (`vacancy_id`),
  KEY `recruiter_id` (`recruiter_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;



INSERT INTO `bids` (`id`, `vacancy_id`, `recruiter_id`, `message`, `status`, `updated_date`) VALUES
(1, 1, 1, 'Условия такие условия, такие интересные условия', 'APPROVED', '2014-01-22 04:14:55'),
(2, 1, 2, 'Какие то другие условия.', 'ACTIVE', '2014-01-22 04:13:37'),
(3, 1, 3, 'Условия Условия Условия Условия Условия Условия Условия Условия " +                 "Условия Условия Условия Условия Условия Условия Условия Условия Условия Условия', 'ACTIVE', '2014-01-22 04:13:37'),
(4, 2, 1, 'Кто не согласен с условиями тот не прав. Условия такие хорошие', 'ACTIVE', '2014-01-22 04:13:37'),
(5, 2, 2, 'Грех не отказаться от условий под дулом пистолета', 'APPROVED', '2014-01-23 09:22:54'),
(6, 4, 1, 'Блабла', 'ACTIVE', '2014-01-22 04:13:37'),
(8, 3, 1, 'Клёвые условия, не пожалеете', 'APPROVED', '2014-01-23 09:22:18');

-- --------------------------------------------------------



DROP TABLE IF EXISTS `deals`;
CREATE TABLE IF NOT EXISTS `deals` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vacancy_id` bigint(20) NOT NULL,
  `recruiter_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'IN_PROGRESS',
  `recruiter_archived` tinyint(1) NOT NULL DEFAULT '0',
  `employer_archived` tinyint(1) NOT NULL DEFAULT '0',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fire_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vacancy_id` (`vacancy_id`),
  KEY `recruiter_id` (`recruiter_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;



INSERT INTO `deals` (`id`, `vacancy_id`, `recruiter_id`, `status`, `recruiter_archived`, `employer_archived`, `updated_date`, `fire_reason`) VALUES
(1, 3, 1, 'IN_PROGRESS', 0, 0, '2014-01-22 04:13:37', NULL),
(2, 2, 2, 'IN_PROGRESS', 0, 0, '2014-01-22 04:13:37', NULL),
(3, 1, 1, 'IN_PROGRESS', 0, 0, '2014-01-22 04:14:55', NULL);

-- --------------------------------------------------------


DROP TABLE IF EXISTS `employers`;
CREATE TABLE IF NOT EXISTS `employers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employers_1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;



INSERT INTO `employers` (`id`, `user_id`) VALUES
(1, 2),
(2, 5),
(3, 6);

-- --------------------------------------------------------



DROP TABLE IF EXISTS `recruiters`;
CREATE TABLE IF NOT EXISTS `recruiters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_recruiters_1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;



INSERT INTO `recruiters` (`id`, `user_id`) VALUES
(1, 1),
(2, 3),
(3, 4);

-- --------------------------------------------------------



DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;


INSERT INTO `users` (`id`, `username`, `password`, `firstname`, `lastname`, `description`) VALUES
(1, 'recruiter', '123123', 'Павел', 'Потапов', 'Лучший рекрутер на правом берегу Иртыша '),
(2, 'employer', '123123', 'Артём', 'Иванов', 'ОАО "Шанс"'),
(3, 'test1', 'test1', 'Алексей', 'Пивоваров', 'Нахожу новых кандидатов быстрее света'),
(4, 'test2', 'test2', 'Владимир', 'Афанасьев', 'Рекрутерское агенство "Новая работа"'),
(5, 'test3', 'test3', 'Михаил', 'Никонов', 'ИП Никонов М.В.'),
(6, 'test4', 'test4', 'Игорь', 'Голованов', 'ООО "Верхняя Пышма"');

-- --------------------------------------------------------



DROP TABLE IF EXISTS `vacancies`;
CREATE TABLE IF NOT EXISTS `vacancies` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employer_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `salary_from` bigint(20) NOT NULL,
  `salary_to` bigint(20) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  `expiration_date` timestamp NOT NULL DEFAULT '2038-01-19 00:00:00',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `test_file` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  KEY `employer_id` (`employer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;



INSERT INTO `vacancies` (`id`, `employer_id`, `title`, `description`, `salary_from`, `salary_to`, `creation_date`, `expiration_date`, `updated_date`, `test_file`, `status`) VALUES
(1, 1, 'Лесоруб', 'Должен уметь рубить лес', 10000, 15000, '2014-01-20 04:36:42', '2014-02-08 17:00:00', '2014-01-22 04:13:37', '#', 'ACTIVE'),
(2, 1, 'Сантехник', 'Не должен пить!', 20000, 20000, '2014-01-20 04:36:40', '2014-02-16 17:00:00', '2014-01-22 04:13:37', '#', 'ACTIVE'),
(3, 1, 'Программист', 'Уметь программировать на С++', 100000, 100000, '2014-01-21 04:36:38', '2014-02-14 17:00:00', '2014-01-22 04:13:37', '#', 'ACTIVE'),
(4, 2, 'Банщик', 'Парит и шпарит', 20000, 25000, '2014-01-14 11:43:41', '2014-02-22 17:00:00', '2014-01-22 04:13:37', '#', 'ACTIVE'),
(5, 2, 'Водитель', 'Водитель маршрутного такси на полный рабочий день.', 30000, 40000, '2014-01-22 11:44:43', '2014-02-16 17:00:00', '2014-01-22 04:13:37', '#', 'ACTIVE'),
(6, 3, 'Токарь', 'Токарь 3-го разряда', 15000, 20000, '2014-01-22 11:45:38', '2014-02-16 17:00:00', '2014-01-22 04:13:37', '#', 'ACTIVE'),
(7, 1, 'Техник', 'Техник технический 2го разряда', 22000, 27000, '2014-01-23 00:00:00', '2014-02-13 00:00:00', '2014-01-22 00:00:00', '#', 'ACTIVE');


ALTER TABLE `applicants`
  ADD CONSTRAINT `applicants_ibfk_1` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`);


ALTER TABLE `bids`
  ADD CONSTRAINT `bids_ibfk_1` FOREIGN KEY (`vacancy_id`) REFERENCES `vacancies` (`id`),
  ADD CONSTRAINT `bids_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiters` (`id`);


ALTER TABLE `deals`
  ADD CONSTRAINT `deals_ibfk_1` FOREIGN KEY (`vacancy_id`) REFERENCES `vacancies` (`id`),
  ADD CONSTRAINT `deals_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiters` (`id`);


ALTER TABLE `employers`
  ADD CONSTRAINT `fk_employers_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);


ALTER TABLE `recruiters`
  ADD CONSTRAINT `fk_recruiters_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);


ALTER TABLE `vacancies`
  ADD CONSTRAINT `vacancies_ibfk_1` FOREIGN KEY (`employer_id`) REFERENCES `employers` (`id`);
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;