ALTER TABLE t_order ADD customer VARCHAR(50);
-- update existing rows
UPDATE t_order set customer = '';
ALTER TABLE t_order MODIFY customer VARCHAR(50) NOT NULL;