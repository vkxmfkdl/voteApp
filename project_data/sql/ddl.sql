CREATE TABLE `vote`.`votecode` (
  `num` INT NOT NULL AUTO_INCREMENT,
  `sgId` VARCHAR(45) NULL,
  `sgName` VARCHAR(45) NULL,
  `sgTypeCode` VARCHAR(45) NULL,
  `sgVotedate` VARCHAR(45) NULL,
  PRIMARY KEY (`num`))
COMMENT = '선거코드';

CREATE TABLE `vote`.`jobcode` (
  `jobId` VARCHAR(45) NOT NULL,
  `sgId` VARCHAR(45) NOT NULL,
  `jobName` VARCHAR(45) NULL,
  PRIMARY KEY (`jobId`, `sgId`))
COMMENT = '직업코드';

CREATE TABLE `vote`.`candidateinfo` (
  `num` INT NOT NULL AUTO_INCREMENT,
  `sgId` VARCHAR(45) NULL,
  `sgTypeCode` VARCHAR(45) NULL,
  `huboid` VARCHAR(45) NULL,
  `sggName` VARCHAR(45) NULL,
  `sdName` VARCHAR(45) NULL,
  `wiwName` VARCHAR(45) NULL,
  `giho` VARCHAR(45) NULL,
  `jdName` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `hanjaName` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL,
  `birthday` VARCHAR(45) NULL,
  `age` VARCHAR(45) NULL,
  `addr` VARCHAR(45) NULL,
  `jobId` VARCHAR(45) NULL,
  `job` VARCHAR(45) NULL,
  `eduId` VARCHAR(45) NULL,
  `edu` VARCHAR(200) NULL,
  `career1` VARCHAR(100) NULL,
  `career2` VARCHAR(100) NULL,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`num`))
COMMENT = '후보자정보';