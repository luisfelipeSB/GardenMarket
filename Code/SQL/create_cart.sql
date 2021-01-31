DELIMITER //
CREATE PROCEDURE `create_cart` (IN bid INT)
BEGIN
	START TRANSACTION;
    
    -- creating the (GardenMarket) transaction
	INSERT INTO transactions(buyer_id) values (bid);
    
    -- getting that GM transaction
    SELECT @tid := t.transct_id, buyer_id FROM transactions t
	LEFT JOIN transactionstate ts ON t.transct_id = ts.transct_id
	WHERE ts.transct_id IS NULL AND buyer_id = bid LIMIT 1;
    
    -- inserting a cart state entry for the assigned GM transaction in transactionstate
    INSERT INTO transactionstate(transct_id, state_id, ts_date)
	VALUES (@tid, 1, sysdate());
	COMMIT;
END //
DELIMITER ;