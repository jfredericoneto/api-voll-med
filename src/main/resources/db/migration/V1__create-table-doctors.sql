create table doctors(

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null unique,
    crm varchar(6) not null unique,
    phone varchar(20) not null,
    specialty varchar(100) not null,
    public_place varchar(100) not null,
    neighborhood varchar(100) not null,
    zip_code varchar(9) not null,
    city varchar(100) not null,
    uf char(2) not null,
    complement varchar(100),
    address_number varchar(20),

    primary key(id)

);