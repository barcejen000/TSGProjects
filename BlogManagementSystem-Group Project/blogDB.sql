drop database if exists blogmanagementsystemdb;

create database blogmanagementsystemdb;

use blogmanagementsystemdb;

create table `user`(
	username varchar(45) primary key,
    `password` varchar(150) not null ,
    `enabled` boolean not null
	);
    
create table `role`(
	id int primary key auto_increment,
    `role` varchar(30) not null
    );
    
create table user_role(
	username varchar(45) not null,
    roleId int not null,
    primary key(username, roleId)
    );
    
create table article(
	id int primary key auto_increment,
    postDate datetime not null,
    title varchar(50) not null,
    notes varchar(200),
    content text not null,
    approved int not null default 0,
    username varchar(45) not null,
    static boolean not null default 0
    );
    
create table hashtag(
	name varchar(45) primary key
    );
    
create table article_hashtag(
	articleId int not null,
    hashtagId varchar(45) not null,
    primary key (articleId, hashtagId)
    );
    
create table `comment`(
	id int primary key auto_increment,
    title varchar(45) not null,
    body varchar(200),
    articleId int,
    commentId int,
    username varchar(45)
    );
    
create table comment_hashtag(
	commentId int not null,
    hashtagId varchar(45) not null,
    primary key (commentId, hashtagId)
    );
    
alter table user_role
	add constraint foreign key (username) references `user`(username),
	add constraint foreign key (roleId) references `role`(id);

alter table article
	add constraint foreign key (username) references `user`(username);

alter table article_hashtag
	add constraint foreign key (articleId) references article(id),
    add constraint foreign key (hashtagId) references hashtag(`name`);
    
alter table `comment`
	add constraint foreign key (articleId) references article(id),
    add constraint foreign key (commentId) references `comment`(id),
    add constraint foreign key (username) references `user`(username);
    
alter table comment_hashtag
	add constraint foreign key (commentId) references `comment`(id),
    add constraint foreign key (hashtagid) references hashtag(`name`);