-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 06, 2014 at 04:53 PM
-- Server version: 5.5.34
-- PHP Version: 5.4.6-1ubuntu1.5

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `recruiter`
--

-- --------------------------------------------------------

--
-- Table structure for table `applicant`
--

DROP TABLE IF EXISTS `applicant`;
CREATE TABLE IF NOT EXISTS `applicant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `sex` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `resume_file` bigint(20) DEFAULT NULL,
  `test_answer_file` bigint(20) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'IN_PROGRESS',
  `viewed` tinyint(1) NOT NULL DEFAULT '0',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `deal` (`deal_id`),
  KEY `resume_file` (`resume_file`),
  KEY `test_answer_file` (`test_answer_file`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- RELATIONS FOR TABLE `applicant`:
--   `deal_id`
--       `deal` -> `id`
--   `resume_file`
--       `attachment` -> `id`
--   `test_answer_file`
--       `attachment` -> `id`
--

--
-- Dumping data for table `applicant`
--

INSERT INTO `applicant` (`id`, `deal_id`, `first_name`, `last_name`, `description`, `sex`, `age`, `resume_file`, `test_answer_file`, `status`, `viewed`, `updated_date`) VALUES
(1, 1, 'Александр', 'Иванов', 'Программирую даже во сне', 'Мужской', 34, NULL, NULL, 'IN_PROGRESS', 1, '2014-01-22 04:17:39'),
(2, 1, 'Павел', 'Никифоров', 'Круто программирует на всём', 'Мужской', 45, NULL, NULL, 'IN_PROGRESS', 1, '2014-01-22 04:17:40'),
(3, 2, 'Михаил', 'Луценко', 'Чиню любые трубы: пластик', 'Мужской', 42, NULL, NULL, 'IN_PROGRESS', 0, '2014-01-22 04:13:37'),
(4, 3, 'Афанасий', 'Афанасьев', 'фывавы', NULL, NULL, NULL, NULL, 'IN_PROGRESS', 0, '2014-01-22 04:15:41'),
(5, 3, 'Михаил', 'Платонов', 'Аккак', NULL, NULL, NULL, NULL, 'IN_PROGRESS', 0, '2014-01-22 04:15:53'),
(6, 1, 'Игорь', 'Вавилов', 'Папаапап', NULL, NULL, NULL, NULL, 'IN_PROGRESS', 0, '2014-01-22 04:16:14'),
(7, 1, 'Олег', 'Кошевой', 'ываыва', NULL, NULL, NULL, NULL, 'REJECTED', 1, '2014-01-22 04:18:08'),
(8, 1, 'Константин', 'Никольский', 'ыаыв', NULL, NULL, NULL, NULL, 'IN_PROGRESS', 0, '2014-01-22 04:16:36');

-- --------------------------------------------------------

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
CREATE TABLE IF NOT EXISTS `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system_filename` varchar(255) NOT NULL,
  `public_filename` varchar(255) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `employer_id` bigint(20) DEFAULT NULL,
  `recruiter_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_filename` (`system_filename`),
  KEY `employer_id` (`employer_id`),
  KEY `recruiter_id` (`recruiter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- RELATIONS FOR TABLE `attachment`:
--   `recruiter_id`
--       `recruiter` -> `id`
--   `employer_id`
--       `employer` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
CREATE TABLE IF NOT EXISTS `bid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vacancy_id` bigint(20) NOT NULL,
  `recruiter_id` bigint(20) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'ACTIVE',
  `recruiter_archived` tinyint(1) NOT NULL DEFAULT '0',
  `employer_archived` tinyint(1) NOT NULL DEFAULT '0',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `viewed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `vacancy_id` (`vacancy_id`),
  KEY `recruiter_id` (`recruiter_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- RELATIONS FOR TABLE `bid`:
--   `vacancy_id`
--       `vacancy` -> `id`
--   `recruiter_id`
--       `recruiter` -> `id`
--

--
-- Dumping data for table `bid`
--

INSERT INTO `bid` (`id`, `vacancy_id`, `recruiter_id`, `message`, `status`, `updated_date`, `viewed`) VALUES
(1, 1, 1, 'Условия такие условия, такие интересные условия', 'APPROVED', '2014-01-22 04:14:55', 1),
(2, 1, 2, 'Какие то другие условия.', 'ACTIVE', '2014-01-22 04:13:37', 0),
(3, 1, 3, 'Условия Условия Условия Условия Условия Условия Условия Условия " +                 "Условия Условия Условия Условия Условия Условия Условия Условия Условия Условия', 'ACTIVE', '2014-01-22 04:13:37', 0),
(4, 2, 1, 'Кто не согласен с условиями тот не прав. Условия такие хорошие', 'ACTIVE', '2014-01-22 04:13:37', 0),
(5, 2, 2, 'Грех не отказаться от условий под дулом пистолета', 'APPROVED', '2014-01-23 09:22:54', 1),
(6, 4, 1, 'Блабла', 'ACTIVE', '2014-01-22 04:13:37', 1),
(8, 3, 1, 'Клёвые условия, не пожалеете', 'APPROVED', '2014-01-23 09:22:18', 1),
(9, 10, 1, 'У меня есть толковый коммерческий директор', 'APPROVED', '2014-02-06 09:26:10', 1),
(10, 11, 1, 'Не подведу', 'ACTIVE', '2014-02-06 09:24:58', 0),
(11, 13, 1, 'Подаю заявку', 'ACTIVE', '2014-02-06 09:25:11', 0),
(12, 18, 1, 'Блаблабла', 'REJECTED', '2014-02-06 09:25:26', 1),
(13, 8, 1, 'Условия', 'APPROVED', '2014-02-06 09:26:04', 1);

-- --------------------------------------------------------

--
-- Table structure for table `deal`
--

DROP TABLE IF EXISTS `deal`;
CREATE TABLE IF NOT EXISTS `deal` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `vacancy_id` bigint(20) NOT NULL,
  `recruiter_id` bigint(20) NOT NULL,
  `bid_id` bigint(20) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'IN_PROGRESS',
  `recruiter_archived` tinyint(1) NOT NULL DEFAULT '0',
  `employer_archived` tinyint(1) NOT NULL DEFAULT '0',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `fire_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vacancy_id` (`vacancy_id`),
  KEY `recruiter_id` (`recruiter_id`),
  KEY `bid_id` (`bid_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- RELATIONS FOR TABLE `deal`:
--   `vacancy_id`
--       `vacancy` -> `id`
--   `recruiter_id`
--       `recruiter` -> `id`
--   `bid_id`
--       `bid` -> `id`
--

--
-- Dumping data for table `deal`
--

INSERT INTO `deal` (`id`, `vacancy_id`, `recruiter_id`, `bid_id`, `status`, `recruiter_archived`, `employer_archived`, `updated_date`, `fire_reason`) VALUES
(1, 3, 1, 8, 'IN_PROGRESS', 0, 0, '2014-01-22 04:13:37', NULL),
(2, 2, 2, 5, 'IN_PROGRESS', 0, 0, '2014-01-22 04:13:37', NULL),
(3, 1, 1, 1, 'IN_PROGRESS', 0, 0, '2014-01-22 04:14:55', NULL),
(4, 8, 1, 13,'IN_PROGRESS', 0, 0, '2014-02-06 09:26:04', NULL),
(5, 10, 1, 9, 'IN_PROGRESS', 0, 0, '2014-02-06 09:26:10', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `employer`
--

DROP TABLE IF EXISTS `employer`;
CREATE TABLE IF NOT EXISTS `employer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_employer_1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- RELATIONS FOR TABLE `employer`:
--   `user_id`
--       `user` -> `id`
--

--
-- Dumping data for table `employer`
--

INSERT INTO `employer` (`id`, `user_id`) VALUES
(1, 2),
(2, 5),
(3, 6);

-- --------------------------------------------------------

--
-- Table structure for table `recruiter`
--

DROP TABLE IF EXISTS `recruiter`;
CREATE TABLE IF NOT EXISTS `recruiter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_recruiter_1` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- RELATIONS FOR TABLE `recruiter`:
--   `user_id`
--       `user` -> `id`
--

--
-- Dumping data for table `recruiter`
--

INSERT INTO `recruiter` (`id`, `user_id`) VALUES
(1, 1),
(2, 3),
(3, 4);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `firstname`, `lastname`, `description`) VALUES
(1, 'recruiter', '123123', 'Павел', 'Потапов', 'Лучший рекрутер на правом берегу Иртыша '),
(2, 'employer', '123123', 'Артём', 'Иванов', 'ОАО "Шанс"'),
(3, 'test1', 'test1', 'Алексей', 'Пивоваров', 'Нахожу новых кандидатов быстрее света'),
(4, 'test2', 'test2', 'Владимир', 'Афанасьев', 'Рекрутерское агенство "Новая работа"'),
(5, 'test3', 'test3', 'Михаил', 'Никонов', 'ИП Никонов М.В.'),
(6, 'test4', 'test4', 'Игорь', 'Голованов', 'ООО "Верхняя Пышма"');

-- --------------------------------------------------------

--
-- Table structure for table `vacancy`
--

DROP TABLE IF EXISTS `vacancy`;
CREATE TABLE IF NOT EXISTS `vacancy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `employer_id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `salary_from` bigint(20) NOT NULL,
  `salary_to` bigint(20) NOT NULL,
  `creation_date` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  `expiration_date` timestamp NOT NULL DEFAULT '2038-01-19 00:00:00',
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `test_file` bigint(20) DEFAULT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`id`),
  KEY `employer_id` (`employer_id`),
  KEY `test_file` (`test_file`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=20 ;

--
-- RELATIONS FOR TABLE `vacancy`:
--   `employer_id`
--       `employer` -> `id`
--   `test_file`
--       `attachment` -> `id`
--

--
-- Dumping data for table `vacancy`
--

INSERT INTO `vacancy` (`id`, `employer_id`, `title`, `description`, `salary_from`, `salary_to`, `creation_date`, `expiration_date`, `updated_date`, `test_file`, `status`) VALUES
(1, 1, 'Лесоруб', 'Должен уметь рубить лес', 10000, 15000, '2014-01-20 04:36:42', '2014-02-08 17:00:00', '2014-01-22 04:13:37', NULL, 'ACTIVE'),
(2, 1, 'Сантехник', 'Не должен пить!', 20000, 20000, '2014-01-20 04:36:40', '2014-02-16 17:00:00', '2014-01-22 04:13:37', NULL, 'ACTIVE'),
(3, 1, 'Программист', 'Уметь программировать на С++', 100000, 100000, '2014-01-21 04:36:38', '2014-02-14 17:00:00', '2014-01-22 04:13:37', NULL, 'ACTIVE'),
(4, 2, 'Банщик', 'Парит и шпарит', 20000, 25000, '2014-01-14 11:43:41', '2014-02-22 17:00:00', '2014-01-22 04:13:37', NULL, 'ACTIVE'),
(5, 2, 'Водитель', 'Водитель маршрутного такси на полный рабочий день.', 30000, 40000, '2014-01-22 11:44:43', '2014-02-16 17:00:00', '2014-01-22 04:13:37', NULL, 'ACTIVE'),
(6, 3, 'Токарь', 'Токарь 3-го разряда', 15000, 20000, '2014-01-22 11:45:38', '2014-02-16 17:00:00', '2014-01-22 04:13:37', NULL, 'ACTIVE'),
(7, 1, 'Техник', 'Техник технический 2го разряда', 22000, 27000, '2014-01-23 00:00:00', '2014-02-13 00:00:00', '2014-01-22 00:00:00', NULL, 'ACTIVE'),
(8, 1, 'Менеджер региональных продаж', 'Обязанности: \r\n\r\n    Прямые продажи\r\n    Продвижение расходных материалов для реанимации, анестезиологии и хирургии в ЛПУ\r\n\r\nТребования:\r\n\r\n    Медицинское образование\r\n    Коммуникабельность\r\n    Навыки ведения переговоров, взаимодействия с клиентами\r\n    Активная жизненная позиция\r\n    Желание работать и зарабатывать\r\n\r\nУсловия:\r\n\r\n    Фиксированный оклад ( по результатам собеседования) + %  от каждой сделки + стабильные премии + компенсация бензина\r\n    Служебный автомобиль, компенсация бензина\r\n    Оплачиваемая безлимитная мобильная связь\r\n    Карьерный рост\r\n    Корпоративное обучение\r\n    Оформление в соответствии с трудовым законодательством\r\n', 20000, 200000, '2014-02-06 09:17:41', '2014-04-01 17:00:00', '2014-02-06 09:17:41', NULL, 'ACTIVE'),
(9, 1, 'Менеджер по работе с клиентами', 'Компания ООО "Новые технологии" приглашает менеджера по работе с клиентами.\r\n\r\n\r\nОбязанности\r\n\r\n    осуществлять поиск и привлечение клиентов на омском и федеральном уровне;\r\n    консультировать клиентов в области маркетинга;\r\n    поддерживать существующую клиентскую базу.\r\n\r\n\r\nТребования\r\n\r\n    наличие высшего образования по специальностям «Менеджмент», «Маркетинг», «Реклама»;\r\n    умение проводить деловые переговоры, встречи с руководителями компаний на уровне первых лиц;\r\n    опыт работы в сфере прямых продаж: не менее 2 лет, приветствуется опыт работы в рекламе;\r\n    знание этапов техники продаж, владение техникой «холодный звонок», наличие теоретических и практических навыков в области маркетинга, стимулирования сбыта;\r\n    знание телефонного и делового этикета, а также правил ведения деловых переговоров.\r\n\r\n\r\nУсловия\r\n\r\n    полный рабочий день, с 13.00 до 14.00 – обеденный перерыв, сб-вс и праздничные дни – выходной;\r\n    официальное трудоустройство согласно ТК РФ;\r\n    полный соцпакет;\r\n    регулярная выплата заработной платы на банковскую карту;\r\n    заработная плата 30.000 рублей и выше;\r\n    карьерный рост.\r\n\r\n\r\nРезюме принимаются на рассмотрение по электронной почте. Заголовок: "Соискатель на должность «Менеджер по работе с клиентами».\r\nТип занятости\r\nПолная занятость, полный день', 30000, 50000, '2014-02-06 09:18:26', '2014-04-02 17:00:00', '2014-02-06 09:18:26', NULL, 'ACTIVE'),
(10, 1, 'Коммерческий директор', 'Обязанности:\r\n\r\n    руководство отделом продаж,\r\n    мониторинг цен,\r\n    взаимодействие с производителями и трейдерами,\r\n    подбор персонала,\r\n    разработка внутренних документов.\r\n\r\nТребования:\r\n\r\n    опыт работы на руководящей должности в торговой компании.\r\n\r\nУсловия:\r\n\r\n    пятидневная рабочая неделя,\r\n    полный рабочий день на территории работодателя,\r\n    испытательный срок 2 месяца.\r\n\r\nТип занятости\r\nПолная занятость, полный день', 60000, 120000, '2014-02-06 09:19:13', '2014-05-07 17:00:00', '2014-02-06 09:19:13', NULL, 'ACTIVE'),
(11, 1, 'Менеджер в отдел по работе с корпоративными клиентами', 'Обязанности:\r\n\r\n    Поставка программного и аппаратного обеспечения,\r\n    Продажа услуг (разработки, интеграция, аутсорсинг),\r\n    Ведение переговоров с различными руководителями различных подразделений, первыми лицами Заказчиков,\r\n    Анализ регионального рынка и развитие отношений с новыми заказчиками,\r\n    Поддержание отношений с существующими заказчиками, выявление и обеспечение всех потребностей заказчика,\r\n    Участие в маркетинговых мероприятиях (конференции, семинары, выставки)\r\n    Поиск новых заказчиков, холодные звонки, встречи.\r\n\r\n \r\n\r\nТребования:\r\n\r\n    Понимание профильной терминологии.\r\n    Коммуникабельность, активность, самоорганизация, мобильность(наличие личного автомобиля приветствуется), стрессоустойчивость.\r\n    Заинтересованность развития в IT отрасли.\r\n\r\n \r\n\r\nУсловия:\r\n\r\n    Полный социальный пакет.\r\n    Карьерный рост.\r\n    Офис в центральной части города.\r\n    Компенсация ГСМ.\r\n    Поощрение повышения квалификации.\r\n    Гибкая система оплаты труда: оклад 15000 + проценты + бонуcы. (систему оплаты обсуждаем на собеседовании)\r\n\r\nАдрес\r\nОмск, улица Красный Путь, 57\r\nПоказать на карте\r\nТип занятости\r\nПолная занятость, полный день', 15000, 40000, '2014-02-06 09:19:40', '2014-04-30 17:00:00', '2014-02-06 09:19:40', NULL, 'ACTIVE'),
(12, 1, 'Менеджер активных продаж', 'Обязанности:\r\n\r\n    привлечение новых клиентов\r\n    контроль и сопровождение торговых сделок\r\n    подготовка и сдача первичной документации\r\n    продажа металлочерепицы, сайдинга, элементов кровли и др. стройматериалов;\r\n    реализация продукции новым и постоянным клиентам;\r\n    контроль по отгрузки и выполнение планов;\r\n    мониторинг конкурентной среды;\r\n     \r\n\r\n\r\nТребования:\r\n\r\n    высшее образование\r\n    опыт в продажах от 1 года\r\n    личностные качества: внимательность, ответственность, коммуникабельность, аккуратность, активная жизненная позиция\r\n\r\n\r\nУсловия:\r\n\r\n    карьерный рост\r\n    корпоративное обучение\r\n    официальный доход (21 600 руб. + % премия+сот. связи+ГСМ)\r\n    социальный пакет.\r\n    личный автомобиль\r\n\r\nАдрес\r\nОмск\r\nПоказать на карте\r\nТип занятости\r\nПолная занятость, полный день', 20000, 80000, '2014-02-06 09:20:08', '2014-05-14 17:00:00', '2014-02-06 09:20:08', NULL, 'ACTIVE'),
(13, 1, 'Консультант 1С', 'Описание\r\nКонсультирование клиентов по работе с программой 1C 8\r\n\r\nОбязанности:\r\n• Участие в разработке и поддержке ПО на базе 1С;\r\n• Конфигурирование и настройка программ 1С на базе 1С Предприятие 7.7 и 8;\r\n• Обновление программных продуктов 1С;\r\n• Консультирование по работе клиентов с программой 1С.\r\n\r\nУсловия:\r\n• Работа в офисе и на территории Заказчика;\r\n• Режим работы с 9-00 до 18-00 (либо в режиме работы Заказчика);\r\n• Испытательный срок - 1 месяц;\r\n• Оформление согласно ТК РФ.\r\n• Предварительно необходимо отправить свое резюме\r\n• Соискатели приглашаются на собеседование ТОЛЬКО после ознакомления с резюме.\r\n• Возможны стажеры, с большим желанием работать и учиться.\r\n\r\nНавыки и опыт\r\n• Готовность освоить основы бухучета, расчета заработной платы.\r\n• Высшее образование или последний курс ВУЗа, аспиранты;\r\n• Опыт работы, желательно бухгалтером или программистом на любом языке высокого уровня;\r\n• Готовность пройти стажировку 1 месяц;\r\n• Готовность освоить основы бухучета, расчета заработной платы.', 15000, 40000, '2014-02-06 09:20:42', '2014-05-06 17:00:00', '2014-02-06 09:20:42', NULL, 'ACTIVE'),
(14, 1, 'Заместитель руководителя розничной сети', 'Обязанности:\r\n\r\n    Управление торговыми точками, контроль работы сотрудников торговых точек и менеджеров розничной сети.\r\n    Анализ эффективности работы розничных точек.\r\n    Внедрение и контроль фирменных стандартов.\r\n    Разработка, проведение маркетинговых мероприятий для увеличения товарооборота розничной сети.\r\n    Ведение документации отдела розницы, контроль ведения текущей документации на торговых точках.\r\n    Контроль состояния склада отдела розницы, контроль и участие в проведении инвентаризации на складе и торговых точках, заказ и покупка торгового оборудования.\r\n\r\n\r\nТребования:\r\n\r\n    Высшее образование (приветствуется профильное образование - менеджмент, управление, бизнес-администрирование).\r\n    Опыт работы на управляющей должности в сфере розничной торговли или общественного питания от 2 лет.\r\n    Знание принципов продаж, умение организовать работу, хорошие коммуникативные навыки, инициативность, целеустремленность.\r\n\r\n \r\n\r\nУсловия:\r\n\r\n    Официальное трудоустройство, мобильная корпоративная связь, бесплатное питание, компенсация ГСМ.\r\n\r\nТип занятости\r\nПолная занятость, полный день', 0, 0, '2014-02-06 09:21:04', '2014-05-14 17:00:00', '2014-02-06 09:21:04', NULL, 'ACTIVE'),
(15, 1, 'Офис-менеджер', 'Требования:\r\n• высшее образование,\r\n• грамотная речь,\r\n• опыт работы в продажах приветствуется,\r\n• желателен опыт работы в сфере FMCG,\r\n• пользователь ПК,\r\n• приветствуются активность, мотивация на достижение, ответственность.\r\n\r\nОбязанности:\r\n• прием и обработка заявок от клиентов,\r\n• развитие и поддержание своей клиентской базы ,\r\n• работа с дебиторской задолженностью,\r\n• ведение отчетности согласно срокам и формам, принятым в компании\r\n\r\nУсловия:\r\n• работа в крупной компании фармацевтического рынка\r\n• заработная плата на испытательный срок 25 т.р.; \r\n• после испытательного срока средняя заработная плата 29 т.р. (оклад+бонус);\r\n• оформление согласно Трудовому кодексу РФ,\r\n• заработная плата «белая»,\r\n• работа в офисе компании.\r\nТип занятости\r\nПолная занятость, полный день', 25000, 29000, '2014-02-06 09:21:44', '2014-04-22 17:00:00', '2014-02-06 09:21:44', NULL, 'ACTIVE'),
(16, 1, 'Автослесарь', 'На новое СТО компании "АвтоТаун" требуются автослесарь для ремонта японских и европейских автомобилей.\r\nСТО находится по адресу 1-я Заводская.\r\n \r\n\r\nТребования:\r\n\r\n    Навыки и опыт \r\n        Хорошие знания устройства и ремонта легковых японских или европейских автомобилей , узлов, агрегатов;\r\n        Опыт работы на СТО;\r\n    Ответственность;\r\n    Внимательность;\r\n    Высокая обучаемость;\r\n    Желание расти профессионально: потребность учиться и совершенствовать свою работу;\r\n\r\nУсловия:\r\n\r\nКомпания "АвтоТаун" предлагает:\r\n\r\n    Достойную и своевременную заработную плату. Если Вы обладаете достойным опытом, соответствующие условия оплаты оговариваются на собеседовании;\r\n    Дополнительное поощрение сотрудников, показывающих лучшие результаты;\r\n    Большие скидки на товар и сервисное обслуживание внутри компании.\r\n\r\nТип занятости\r\nПолная занятость, полный день', 30000, 50000, '2014-02-06 09:22:10', '2014-04-09 17:00:00', '2014-02-06 09:22:10', NULL, 'ACTIVE'),
(17, 1, 'Автоэлектрик-диагност', 'На новое СТО компании "АвтоТаун" требуется автоэлектрик-диагност для ремонта японских и европейских автомобилей.\r\n\r\nСТО находится по адресу  Заводская\r\n\r\n \r\n\r\nОсновные обязанности:\r\n\r\n\r\n• диагностика электронных систем автомобиля;\r\n• диагностика и ремонт электрооборудования легковых автомобилей (импортные);\r\n• установка автосигнализаций, авто-аудио аппаратуры.\r\n\r\n\r\nНавыки и опыт\r\n\r\n\r\n• знание устройства автомобилей импортного производства, эл. проводки автомобиля\r\n• умение выявлять и устранять неисправность электрооборудования\r\n• опыт диагностики автомобиля\r\n• навыки работы с электроизмерительными приборами\r\n• ответственность, исполнительность, пунктуальность\r\n\r\n \r\n\r\nКомпания "АвтоТаун" предлагает:\r\n\r\n\r\n• достойную и своевременную заработную плату ( % от выполненных работ). Если Вы обладаете достойным опытом, соответствующие условия оплаты оговариваются на собеседовании;\r\n• дополнительное поощрение сотрудников, показывающих лучшие результаты;\r\n• большие скидки на товар и сервисное обслуживание внутри компании.\r\nТип занятости\r\nПолная занятость, полный день', 25000, 35000, '2014-02-06 09:22:48', '2014-05-07 17:00:00', '2014-02-06 09:22:48', NULL, 'ACTIVE'),
(18, 1, 'Менеджер по продажам запчастей', 'Обязанности:\r\n\r\n    выявление потребностей покупателей (определение запчастей, которые им необходимы);\r\n    приём заказов от покупателей;\r\n    работа с электронными каталогами авто-запчастей;\r\n    поиск авто-запчастей;\r\n    продажа авто-запчастей;\r\n    контроль взаиморасчетов с поставщиками.\r\n\r\n\r\nТребования:\r\n\r\n    хорошее знание устройства автомобилей, авто-запчастей и комплектующих зарубежного производства;\r\n    знание программы 1С;\r\n    уверенное пользование интернетом, ПК;\r\n    опыт работы в продажах;\r\n    развитые коммуникативные навыки.\r\n\r\n\r\nКомпания "АвтоТаун" предлагает:\r\n\r\n    достойную и своевременную заработную плату. Если Вы обладаете достойным опытом, соответствующие условия оплаты оговариваются на собеседовании;\r\n    дополнительное поощрение сотрудников, показывающих лучшие результаты;\r\n    большие скидки на товар и сервисное обслуживание внутри компании.\r\n\r\nТип занятости\r\nПолная занятость, полный день', 30000, 40000, '2014-02-06 09:23:16', '2014-05-06 17:00:00', '2014-02-06 09:23:16', NULL, 'ACTIVE'),
(19, 1, 'Менеджер по персоналу', 'Обязанности:\r\n\r\n    Отбор кандидатов на вакансии;\r\n    Контроль адаптации новых сотрудников;\r\n    Проведение мониторинга рынка предложений работодателей и ожиданий соискателей;\r\n    Участие в оценочных процедурах;\r\n    Размещение информации о вакансиях компании в СМИ и интернет-ресурсах; \r\n    Участие в организации корпоративных мероприятий, формировании благоприятного морально-психологического климата внутри Компании; \r\n    Ведение кадрового делопроизводства по Компании (меньше 100 человек).\r\n\r\n \r\n\r\nТребования:\r\n\r\n    Образование высшее (желательно управление персоналом); \r\n    Опыт работы в сфере управления персоналом;\r\n    Уверенное владение технологиями подбора; \r\n    Уверенное знание трудового законодательства и умение применять его на практике;\r\n    Знание программы 1С 8, опытный пользователь ПК; \r\n    Развитые коммуникативные навыки , самоорганизованность, работоспособность, стрессоустойчивость; \r\n    Готовность работать с большими объемами информации.\r\n\r\n \r\n\r\nМы предлагаем:\r\n\r\n    График работы 5/2 (суббота, воскресенье выходной) с 9:00 до 18:00;\r\n    Возможность для повышения профессиональной квалификации, бесплатное корпоративное обучение;\r\n    Скидки на товары Компании;\r\n    Достойный уровень заработной платы (обсуждается с успешным кандидатом индивидуально), своевременная выплата заработной платы 2 раза в месяц;\r\n    Молодой и дружные коллектив;\r\n    Работа в активно развивающейся Компании.\r\n\r\n\r\nПодчинение Руководителю отдела персонала', 18000, 25000, '2014-02-06 09:23:43', '2014-05-13 17:00:00', '2014-02-06 09:23:43', NULL, 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
CREATE TABLE IF NOT EXISTS `chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `recruiter_id` bigint(20) DEFAULT NULL,
  `employer_id` bigint(20) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `deal_id` (`deal_id`),
  KEY `recruiter_id` (`recruiter_id`),
  KEY `employer_id` (`employer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- RELATIONS FOR TABLE `chat`:
--   `employer_id`
--       `employer` -> `id`
--   `deal_id`
--       `deal` -> `id`
--   `recruiter_id`
--       `recruiter` -> `id`
--

--
-- Dumping data for table `chat`
--

INSERT INTO `chat` (`id`, `deal_id`, `recruiter_id`, `employer_id`, `date`, `message`) VALUES
(1, 1, 1, NULL, '2014-02-10 07:43:29', 'тест'),
(2, 1, 1, NULL, '2014-02-10 07:43:38', 'ёлки, неужели работает?!');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deal_id` bigint(20) NOT NULL,
  `recruiter_id` bigint(20) NOT NULL,
  `employer_id` bigint(20) NOT NULL,
  `recruiter_feedback` varchar(255) DEFAULT NULL,
  `employer_feedback` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `deal_id` (`deal_id`),
  KEY `recruiter_id` (`recruiter_id`),
  KEY `employer_id` (`employer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;


--
-- Constraints for dumped tables
--

--
-- Constraints for table `applicant`
--
ALTER TABLE `applicant`
  ADD CONSTRAINT `applicant_ibfk_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`id`),
  ADD CONSTRAINT `applicant_ibfk_2` FOREIGN KEY (`resume_file`) REFERENCES `attachment` (`id`),
  ADD CONSTRAINT `applicant_ibfk_3` FOREIGN KEY (`test_answer_file`) REFERENCES `attachment` (`id`);

--
-- Constraints for table `attachment`
--
ALTER TABLE `attachment`
  ADD CONSTRAINT `attachment_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`id`),
  ADD CONSTRAINT `attachment_ibfk_1` FOREIGN KEY (`employer_id`) REFERENCES `employer` (`id`);

--
-- Constraints for table `bid`
--
ALTER TABLE `bid`
  ADD CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`vacancy_id`) REFERENCES `vacancy` (`id`),
  ADD CONSTRAINT `bid_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`id`);

--
-- Constraints for table `deal`
--
ALTER TABLE `deal`
  ADD CONSTRAINT `deal_ibfk_1` FOREIGN KEY (`vacancy_id`) REFERENCES `vacancy` (`id`),
  ADD CONSTRAINT `deal_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`id`),
  ADD CONSTRAINT `deal_ibfk_3` FOREIGN KEY (`bid_id`) REFERENCES `bid` (`id`);

--
-- Constraints for table `employer`
--
ALTER TABLE `employer`
  ADD CONSTRAINT `fk_employer_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `recruiter`
--
ALTER TABLE `recruiter`
  ADD CONSTRAINT `fk_recruiter_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `vacancy`
--
ALTER TABLE `vacancy`
  ADD CONSTRAINT `vacancy_ibfk_1` FOREIGN KEY (`employer_id`) REFERENCES `employer` (`id`),
  ADD CONSTRAINT `vacancy_ibfk_2` FOREIGN KEY (`test_file`) REFERENCES `attachment` (`id`);
SET FOREIGN_KEY_CHECKS=1;

--
-- Constraints for table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `chat_ibfk_3` FOREIGN KEY (`employer_id`) REFERENCES `employer` (`id`),
  ADD CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`id`),
  ADD CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `recruiter` (`id`);

  --
  -- Constraints for table `feedback`
  --
  ALTER TABLE `feedback`
    ADD CONSTRAINT `feedback_ibfk_3` FOREIGN KEY (`employer_id`) REFERENCES `recruiter` (`id`),
    ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`id`),
    ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`recruiter_id`) REFERENCES `employer` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;