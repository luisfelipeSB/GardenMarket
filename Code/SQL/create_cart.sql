DELIMITER //
CREATE PROCEDURE `create_cart` (IN bid INT)
BEGIN
	START TRANSACTION;
    
    -- Creating the (GardenMarket) transaction
	INSERT INTO transactions(buyer_id) values (bid);
    
    -- Getting that GM transaction
    SET @tid = (
		SELECT t.transct_id FROM transactions t
		LEFT JOIN transactionstate ts ON t.transct_id = ts.transct_id
		WHERE ts.transct_id IS NULL AND buyer_id = bid LIMIT 1
    );
    
    -- Inserting a cart state entry for the assigned GM transaction in transactionstate
    INSERT INTO transactionstate(transct_id, state_id, ts_date)
	VALUES (@tid, 1, sysdate());
    
    -- Finally, returning that same cart
    SELECT t.transct_id, t.buyer_id, max(ts.state_id) as statelvl 
    FROM transactions t
    INNER JOIN users u on usr_id = t.buyer_id
    INNER JOIN transactionstate ts on ts.transct_id = t.transct_id
	WHERE t.transct_id = @tid;

	COMMIT;
END //
DELIMITER ;