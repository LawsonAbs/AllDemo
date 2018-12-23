drop table if exists monthStat;
create table monthStat(
teleNumber varchar(12) not null primary key,
yearMonth varchar(6) not null,
callDuration int not null DEFAULT  0
);