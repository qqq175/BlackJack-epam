package org.qqq175.blackjack.persistence.dao;

import java.math.BigDecimal;

import org.qqq175.blackjack.exception.DAOException;
import org.qqq175.blackjack.persistence.connection.ConnectionWrapper;
import org.qqq175.blackjack.persistence.entity.AccountOperation;
import org.qqq175.blackjack.persistence.entity.id.AccountOperationId;
import org.qqq175.blackjack.persistence.entity.id.UserId;

public interface AccountOperationDAO extends EntityDAO<AccountOperation, AccountOperationId> {
	AccountOperationId create(AccountOperation entity, ConnectionWrapper conn) throws DAOException;

	BigDecimal calcTotal(AccountOperation.Type type) throws DAOException;

	BigDecimal calcTotal(AccountOperation.Type type, UserId userId) throws DAOException;
}
