create table media (dtype varchar(31) not null, data_id bigint not null auto_increment, file_name varchar(255), file_path varchar(255), insert_time datetime, source_uid varchar(255), uid varchar(255), format varchar(255), height integer, width integer, codec varchar(255), time_len integer, source_source_id bigint, primary key (data_id)) engine=InnoDB
create table media_tags (media_data_id bigint not null, tag_id integer, tag_name varchar(255), tag_source varchar(255), tag_value varchar(255)) engine=InnoDB
create table source (source_id bigint not null auto_increment, description varchar(255), source_name varchar(255), primary key (source_id)) engine=InnoDB
alter table media add constraint UK_g4kyjv667i8ub6efrf3umiikp unique (uid)
alter table source add constraint UK_3pganbnum82xyg852kf3qonwu unique (source_name)
alter table media add constraint FKcp1f0ukc8qtqci1lwj8ojs3og foreign key (source_source_id) references source (source_id)
alter table media_tags add constraint FK3t16t557845rcohxo6iaav5lb foreign key (media_data_id) references media (data_id)
