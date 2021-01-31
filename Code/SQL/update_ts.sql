DELIMITER //
CREATE PROCEDURE `update_ts` (IN tid INT, IN tsid INT)
BEGIN
    
	-- Inserting a new state entry for the assigned GM transaction in transactionstate
	INSERT INTO transactionstate (transct_id, state_id, ts_date) 
	VALUES (tid, tsid, sysdate());
    
    -- Updating the active status of ads within transaction if the new state is purchased
    IF tsid = 2 THEN
		UPDATE advertisements SET ad_isactive = FALSE 
		WHERE ad_id IN (
			SELECT aid FROM (
				SELECT a.ad_id AS aid FROM advertisements a
				INNER JOIN transactionitems ts ON ts.ad_id = a.ad_id
				INNER JOIN transactions t ON t.transct_id = ts.transct_id
				WHERE t.transct_id = tid
			) a
		);
	END IF;
    
END//
DELIMITER ;
