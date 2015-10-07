-- see https://github.com/Hippoom/restbucks/issues/7
UPDATE t_order set status = 'payment-expected' where status = 'pending';