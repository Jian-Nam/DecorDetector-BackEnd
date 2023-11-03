SHOW DATABASES;
CREATE DATABASE `product_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE product_db
drop table if exists product CASCADE;
create table product
(
    id bigint generated by default as identity,
    external_id varchar(255),
    product_name varchar(255),
    image varchar(255),
    link varchar(255),
    primary key (id)
);

create table product
(
    id bigint NOT NULL AUTO_INCREMENT,
    external_id varchar(255),
    product_name varchar(255),
    image varchar(255),
    link varchar(255),
    primary key (id)
);

drop table if exists vector CASCADE;
create table vector
(
    product_id bigint,
    vector_value FLOAT,
    vector_order INT,
    constraint vector_pk primary key(product_id, vector_value, vector_order)
);

DROP TABLE IF EXISTS search_key CASCADE;
CREATE TABLE search_key
(
    id bigint generated by default as identity,
    primary key (id)
);

DROP TABLE IF EXISTS key_vector CASCADE;
create table key_vector
(
    searchkey_id bigint,
    vector_value FLOAT,
    vector_order INT,
    constraint keyVector_pk primary key(searchkey_id, vector_value, vector_order)
);

insert into vector_input
values
    (1, 3.14, 0),
    (1, 8.05, 1),
    (1, 5.29, 2),
    (1, 3.28, 3)



with norms as (
    select
        product_id,
        sum(vector_value * vector_value) as squared_value
    from vector
    group by product_id
), input_norms as (
    select
        product_id,
        sum(vector_value * vector_value) as squared_value
    from vector
    WHERE product_id=10
), input_vector as(
    select * from vector WHERE product_id=10
)
select
    x.product_id as product_id,
    sum(x.vector_value * y.vector_value) / sqrt(nx.squared_value * ny.squared_value) as cosine_similarity
from vector as x
join input_vector as y
    on (x.vector_order=y.vector_order)
join norms as nx
    on (nx.product_id=x.product_id)
join input_norms as ny
    on (ny.product_id=y.product_id)
group by x.product_id
order by cosine_similarity desc




with input_vector as(
    select * from key_vector WHERE searchkey_id=1
)
SELECT
    A.product_id as id,
    P.name as name,
    P.image as image,
    P.link as link,
    SUM(A.vector_value * B.vector_value) / (SQRT(SUM(A.vector_value * A.vector_value)) * SQRT(SUM(B.vector_value * B.vector_value))) AS cosine_similarity
FROM vector as A
JOIN input_vector as B
    on (A.vector_order = B.vector_order)
JOIN product as P
    on (A.product_id = P.id)
group by A.product_id
order by cosine_similarity desc
