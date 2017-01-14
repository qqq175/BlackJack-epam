package org.qqq175.blackjack.persistence.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.qqq175.blackjack.exception.DAOException;
import org.qqq175.blackjack.persistence.connection.ConnectionWrapper;
import org.qqq175.blackjack.persistence.dao.UserDAO;
import org.qqq175.blackjack.persistence.entity.User;
import org.qqq175.blackjack.persistence.entity.User.Type;
import org.qqq175.blackjack.persistence.entity.id.UserId;

public class UserDAOImpl extends EntityDAOImpl<User, UserId> implements UserDAO {
	private static final String TABLE_NAME = "user";
	private static final int COLUMN_COUNT = 12;

	private static final String EXCEPTION_MESSAGE_PREPARE = "Exception in " + UserDAOImpl.class.getName() + " at preparing query.";
	private static final String EXCEPTION_MESSAGE_FILL = "Exception in " + UserDAOImpl.class.getName() + " at reading query result.";

	public UserDAOImpl() {
		super(TABLE_NAME, COLUMN_COUNT);
	}

	@Override
	public boolean disableUser(UserId userId) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.disable"))) {
				prepStatment.setLong(1, userId.getValue());
				return prepStatment.executeUpdate() == 1;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean enableUser(UserId userId) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.enable"))) {
				prepStatment.setLong(1, userId.getValue());
				return prepStatment.executeUpdate() == 1;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User findUser(String login, String password) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			String query = prepareQueryString(getSqlQuery().getQuery("sql.user.findBy.login.password"));
			try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
				prepStatment.setString(1, login);
				prepStatment.setString(2, password);
				try (ResultSet resultSet = prepStatment.executeQuery()) {
					return toDTO(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User findUserWithHash(String login, String passwordHash) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			String query = prepareQueryString(getSqlQuery().getQuery("sql.user.findBy.login.passwordHash"));
			try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
				prepStatment.setString(1, login);
				prepStatment.setString(2, passwordHash);
				try (ResultSet resultSet = prepStatment.executeQuery()) {
					return toDTO(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public User findUser(String email) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			String query = prepareQueryString(getSqlQuery().getQuery("sql.user.findBy.email"));
			System.out.println(query);
			try (PreparedStatement prepStatment = connection.prepareStatement(query)) {
				prepStatment.setString(1, email);
				try (ResultSet resultSet = prepStatment.executeQuery()) {
					return toDTO(resultSet);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean update(User entity) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("For update userstat use increment methods of UserDAO");
	}

	@Override
	protected void prepareWithEntity(PreparedStatement prepStatment, User entity) throws UnsupportedOperationException, DAOException {
		try {
			prepStatment.setString(1, entity.getEmail());
			prepStatment.setString(2, entity.getPassword());
			prepStatment.setString(3, entity.getFirstName());
			prepStatment.setString(4, entity.getLastName());
			prepStatment.setString(5, entity.getDisplayName());
			prepStatment.setString(6, entity.getType().toString());
			prepStatment.setBoolean(7, entity.isActive());
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_MESSAGE_PREPARE, e);
		}
	}

	@Override
	protected User fillEntity(ResultSet resultSet) throws SQLException, DAOException {
		User user = new User();
		try {
			user.setId(new UserId(resultSet.getLong(1)));
			user.setEmail(resultSet.getString(2));
			user.setPassword(resultSet.getString(3));
			user.setFirstName(resultSet.getString(4));
			user.setLastName(resultSet.getString(5));
			user.setDisplayName(resultSet.getString(6));
			user.setRating(resultSet.getDouble(7));
			user.setAccountBalance(resultSet.getBigDecimal(8));
			user.setLockedBalance(resultSet.getBigDecimal(9));
			user.setType(User.Type.valueOf(resultSet.getString(10).toUpperCase()));
			user.setRegistred(this.stringToDate(resultSet.getString(11)));
			user.setActive(resultSet.getBoolean(12));
		} catch (SQLException e) {
			throw new DAOException(EXCEPTION_MESSAGE_FILL, e);
		}
		return user;
	}

	@Override
	public boolean updateBalance(UserId userId, BigDecimal change, ConnectionWrapper connection) throws DAOException {
		try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.update.balance"))) {
			prepStatment.setBigDecimal(1, change);
			prepStatment.setLong(2, userId.getValue());
			return prepStatment.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean updateType(UserId userId, Type type) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.update.type"))) {
				prepStatment.setString(1, type.toString());
				prepStatment.setLong(2, userId.getValue());
				return prepStatment.executeUpdate() == 1;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean updatePassword(UserId userId, String oldPass, String newPass) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.password"))) {
				prepStatment.setString(1, newPass);
				prepStatment.setLong(2, userId.getValue());
				prepStatment.setString(3, oldPass);
				return prepStatment.executeUpdate() == 1;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean updatePersonal(UserId userId, String firstName, String lastName, String displayName) throws DAOException {
		try (ConnectionWrapper connection = connPool.retrieveConnection()) {
			try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.update.personal"))) {
				prepStatment.setString(1, firstName);
				prepStatment.setString(2, lastName);
				prepStatment.setString(3, displayName);
				prepStatment.setLong(4, userId.getValue());
				return prepStatment.executeUpdate() == 1;
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	protected UserId makeId(long id) {
		return new UserId(id);
	}

	@Override
	public boolean lockBalance(UserId userId, BigDecimal change, ConnectionWrapper connection) throws DAOException {
		try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.update.balance.lock"))) {
			prepStatment.setBigDecimal(1, change);
			prepStatment.setBigDecimal(2, change);
			prepStatment.setLong(3, userId.getValue());
			return prepStatment.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean unlockBalance(UserId userId, BigDecimal change, ConnectionWrapper connection) throws DAOException {
		try (PreparedStatement prepStatment = connection.prepareStatement(getSqlQuery().getQuery("sql.user.update.balance.unlock"))) {
			prepStatment.setBigDecimal(1, change);
			prepStatment.setBigDecimal(2, change);
			prepStatment.setLong(3, userId.getValue());
			return prepStatment.executeUpdate() == 1;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
