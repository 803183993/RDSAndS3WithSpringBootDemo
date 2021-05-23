create table movie
(
    title varchar2(255) not null,
    release int not null,
    uri varchar2(255) not null,
    primary key(title)
);

create table audit
(
    row_id varchar2(255) not null,
    entity_type varchar2(20) not null,
    entity_id varchar2(255) not null,
    event varchar2(20) not null,
    host_name varchar2(255) not null,
    create_date timestamp not null,
    primary key(row_id)
);