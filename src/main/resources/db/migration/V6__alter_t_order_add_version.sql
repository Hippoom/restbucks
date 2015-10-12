ALTER TABLE t_order ADD version NUMERIC(8);
-- update existing rows
UPDATE t_order set version = 1 where version = NULL;
ALTER TABLE t_order MODIFY version NUMERIC(8) NOT NULL;