CREATE DATABASE `quizapi` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `question_options` (
  `questionId` int(11) NOT NULL AUTO_INCREMENT,
  `optionA` varchar(45) NOT NULL,
  `optionB` varchar(45) NOT NULL,
  `optionC` varchar(45) DEFAULT NULL,
  `optionD` varchar(45) DEFAULT NULL,
  `optionCorrect` varchar(45) NOT NULL,
  PRIMARY KEY (`questionId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


CREATE TABLE `quiz_exams` (
  `examId` int(11) NOT NULL AUTO_INCREMENT,
  `examName` varchar(45) NOT NULL,
  `examDuration` int(11) NOT NULL DEFAULT '30',
  `negativeMarks` int(11) NOT NULL DEFAULT '0',
  `numberOfQuestions` int(11) NOT NULL DEFAULT '30',
  `examStatus` varchar(45) NOT NULL DEFAULT 'open',
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`examId`,`userId`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `quiz_users` (`userId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;


CREATE TABLE `quiz_questions` (
  `questionId` int(11) NOT NULL AUTO_INCREMENT,
  `questionType` varchar(45) NOT NULL DEFAULT 'MutlipleChoice',
  `questionName` text NOT NULL,
  `examId` int(11) NOT NULL,
  PRIMARY KEY (`questionId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


CREATE TABLE `quiz_users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `emailId` varchar(45) NOT NULL,
  `phoneNumber` varchar(11) NOT NULL,
  `instituteName` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'user',
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `emailId_UNIQUE` (`emailId`),
  UNIQUE KEY `phoneNumber_UNIQUE` (`phoneNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


INSERT INTO `quizapi`.`quiz_users`
(`userName`,`firstName`,`lastName`,`emailId`,`phoneNumber`,`instituteName`,`password`)
VALUES
('KrishnaRG','Krishna',
'Girennavar','krg@gmail.com','8050805083','Manipal University','ODA1MDgwNTA4Mw==');

INSERT INTO `quizapi`.`quiz_users`
(`userName`,`firstName`,`lastName`,`emailId`,`phoneNumber`,`instituteName`,`password`)
VALUES
('VinayakRG','Vinayak',
'Girennavar','vrg@gmail.com','8050805086','Manipal University','ODA1MDgwNTA4Mw==');






