create table movie
(
    title varchar2(255) not null,
    release int not null,
    uri varchar2(255) not null,
    director varchar2(255) not null,
    primary key(title)
);

create table reviews(
   review_id int GENERATED ALWAYS AS IDENTITY,
   title varchar2(255),
   reviewer varchar2(255) NOT NULL,
   rating integer,
   comment varchar2(255) NOT NULL,
   review_date timestamp not null,

   PRIMARY KEY(review_id),
   CONSTRAINT fk_movie
      FOREIGN KEY(title)
	  REFERENCES movie(title)
	  ON DELETE CASCADE
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