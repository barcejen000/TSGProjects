drop database if exists guessingGameDBtest;
create database guessingGameDBtest;
use guessingGameDBtest;

create table game(
id int primary key auto_increment,
answer char(4) not null,
finished boolean default false);

create table `round`(
id int primary key auto_increment,
gameId int not null,
guess char(4) not null,
`time` timestamp default current_timestamp,
result char(7) not null,
foreign key (gameId) references game(id));

 
