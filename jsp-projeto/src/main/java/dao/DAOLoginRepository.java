package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;
import model.ModelLogin;

public class DAOLoginRepository {
	private Connection connection;

	public DAOLoginRepository() {
		connection = SingleConnection.getConn();
	}

	/* metodo para validar o login */
	public boolean validarAutenticacao(ModelLogin modelLogin) throws Exception {
		String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, modelLogin.getLogin());
		statement.setString(2, modelLogin.getSenha());
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return true;
		}

		return false;
	}

}
