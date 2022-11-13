create sequence s_work_item_id;
create sequence s_project_id;
create sequence s_customer_id;
create sequence s_invoice_id;

create table customer
(
    id               bigint DEFAULT nextval('s_customer_id') PRIMARY KEY,
    name             varchar(255),
    short_name       char(3),
    street           varchar(255),
    street_number    varchar(255),
    city             varchar(255),
    zip_code         varchar(255)
);

create type ratemode as ENUM ('hourly', 'daily');

create table project
(
    id                  bigint DEFAULT nextval('s_project_id') PRIMARY KEY,
    description         varchar(255),
    customer_id         bigint references customer (id),
    rate                decimal,
    rate_mode           ratemode,
    start_date          date,
    end_date            date
);

create table work_item
(
    id          bigint DEFAULT nextval('s_work_item_id') PRIMARY KEY,
    project_id  bigint references project (id),
    start_time  timestamp,
    end_time    timestamp,
    break_time  int,
    description varchar(255)
);

create table app_user
(
    name     varchar(255) not null,
    email    varchar(255) not null primary key,
    password varchar(255) not null
);

create type invoicestatus as ENUM ('created', 'sent', 'paid');

create table invoice
(
    id             bigint DEFAULT nextval('s_invoice_id') PRIMARY KEY,
    invoice_number varchar(50),
    project_id     bigint references project (id),
    invoice_date   date,
    total_hours    decimal,
    vat_rate       decimal,
    invoice_status invoicestatus
);

create table invoice_seq 
(
    customer_id bigint references customer (id) not null,
    seq_number int not null
);

create table settings 
(
    vat_rate decimal
)



