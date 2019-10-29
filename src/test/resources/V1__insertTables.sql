CREATE SCHEMA roledemo;

CREATE TABLE roledemo.countries (
    id UUID PRIMARY KEY,
    name varchar(20) UNIQUE
);

CREATE TABLE roledemo.employees (
    id UUID PRIMARY KEY,
    name varchar(20) UNIQUE
);

CREATE TABLE roledemo.nodes (
    id UUID PRIMARY KEY,
    name varchar(20) UNIQUE
);

CREATE TABLE roledemo.roles (
    id UUID PRIMARY KEY,
    name varchar(20) UNIQUE,
    employee_id UUID REFERENCES roledemo.employees(id)
);

CREATE TABLE roledemo.node_countries (
    node_id UUID REFERENCES roledemo.nodes(id),
    country_id UUID REFERENCES roledemo.countries(id),
    PRIMARY KEY (node_id,country_id)
);

CREATE TABLE roledemo.node_roles (
    role_id UUID PRIMARY KEY REFERENCES roledemo.roles(id),
    node_id UUID REFERENCES roledemo.nodes(id)
);

CREATE TABLE roledemo.node_country_roles (
    role_id UUID PRIMARY KEY REFERENCES roledemo.roles(id),
    node_id UUID REFERENCES roledemo.nodes(id),
    country_id UUID REFERENCES roledemo.countries(id)
)