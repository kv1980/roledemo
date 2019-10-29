INSERT INTO countries (id, name)
VALUES ('3f5ba304-7022-4a1a-a6e8-b28afca882d1', 'Belgium'),
       ('7ba9d3a4-0ede-4e86-a91d-0f42594d77c2', 'France');

INSERT INTO employees (id, name)
VALUES ('ae7db118-c66f-453a-b47c-53d12d56d34d','Alain Vandam'),
       ('2e8d9fad-3fd7-4054-9e9c-d5c61e0fdf82','Guido Palemans'),
       ('ffcf3efb-8132-4922-bceb-49f274fb364b','Frankie Loosveld');

INSERT INTO nodes (id, name)
VALUES ('2234cf74-9a26-4772-94a8-adb730b5b4cd','Europe');

INSERT INTO node_countries (node_id, country_id)
VALUES ('2234cf74-9a26-4772-94a8-adb730b5b4cd','3f5ba304-7022-4a1a-a6e8-b28afca882d1');

INSERT INTO roles (id, name, employee_id)
VALUES ('5ca7c3a5-4fec-4bd3-b89f-9e9516156032','sales representative','ae7db118-c66f-453a-b47c-53d12d56d34d'),
       ('71dba9a1-2352-4170-9bc0-5ad08b2d1bfe','region manager','2e8d9fad-3fd7-4054-9e9c-d5c61e0fdf82');

INSERT INTO node_roles (node_id, role_id)
VALUES ('2234cf74-9a26-4772-94a8-adb730b5b4cd','71dba9a1-2352-4170-9bc0-5ad08b2d1bfe');

INSERT INTO node_country_roles (node_id, country_id, role_id)
VALUES ('2234cf74-9a26-4772-94a8-adb730b5b4cd','3f5ba304-7022-4a1a-a6e8-b28afca882d1','5ca7c3a5-4fec-4bd3-b89f-9e9516156032');

