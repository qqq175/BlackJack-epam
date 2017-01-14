package org.qqq175.blackjack.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.blackjack.exception.DAOException;
import org.qqq175.blackjack.persistence.dao.AccountOperationDAO;
import org.qqq175.blackjack.persistence.entity.AccountOperation;
import org.qqq175.blackjack.persistence.entity.AccountOperation.Type;
import org.qqq175.blackjack.persistence.entity.id.AccountOperationId;
import org.qqq175.blackjack.persistence.entity.id.UserId;

public class AccountOperationDAOImpl extends EntityDAOImpl<AccountOperation, AccountOperationId> implements AccountOperationDAO {
	private static final String TABLE_NAME = "account_operation";
	private static final int COLUMN_COUNT = 6;

	private static final String EXCEPTION_MESSAGE_PREPARE = "Exception in " + AccountOperationDAOImpl.class.getName() + " at preparing query.";
	private static final String EXCEPTION_MESSAGE_FILL = "Exception in " + AccountOperationDAOImpl.class.getName() + " at reading query result.";

	public AccountOperationDAOImpl() {
		super(TABLE_NAME, COLUMN_COUNT);
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, AccountOperation entity) throws UnsupportedOperationException, DAOException {
		// INSERT INTO account_operation (user_id, ammount, type, time, comment)
		// VALUES (?, ?, ?, ?, ?)
		try {
			prepStatment.setLong(1, entity.getUserId().getValue());
			prepStatment.setBigDecimal(2, entity.getAmmount());
			prepStatment.setString(3, entity.getType().toString());
			prepStatment.setString(4, this.dateToString(entity.getTime()));
			prepStatment.setString(5, entity.getComment());
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_MESSAGE_PREPARE, e);
		}
	}

	@Override
	protected AccountOperation fillEntity(ResultSet resultSet) throws SQLException, DAOException {
		// account_operation_id, user_id, ammount, type, time, comment
		AccountOperation ao = new AccountOperation();
		try {
			ao.setId(new AccountOperationId(resultSet.getLong(1)));
			ao.setUserId(new UserId(resultSet.getLong(3)));
			ao.setAmmount(resultSet.getBigDecimal(3));
			ao.setType(AccountOperation.Type.valueOf(resultSet.getString(4).toUpperCase()));
			ao.setTime(this.stringToDate(resultSet.getString(5)));
			ao.setComment(resultSet.getString(6));
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_MESSAGE_FILL, e);
		}
		return ao;
	}

	@Override
	protected AccountOperationId makeId(long id) {
		return new AccountOperationId(id);
	}

	@Override
	public BigDecimal calcTotal(Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal calcTotal(Type type, UserId userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
