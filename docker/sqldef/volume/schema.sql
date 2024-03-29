CREATE TABLE `user` (
  `ID` CHAR(36) NOT NULL,
  `NAME` VARCHAR(200) NOT NULL,
  `EMAIL` VARCHAR(320) NOT NULL,
  `CREATED_AT` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_AT` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
