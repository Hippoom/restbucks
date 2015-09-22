CREATE DATABASE IF NOT EXISTS restbucks_ordering;
GRANT ALL PRIVILEGES ON restbucks_ordering.* To 'ordering'@localhost IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON restbucks_ordering.* To 'ordering'@'%' IDENTIFIED BY '123456';