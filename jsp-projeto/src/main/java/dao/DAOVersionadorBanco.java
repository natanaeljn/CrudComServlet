package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class DAOVersionadorBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	private Connection connection;

	public DAOVersionadorBanco() {
		connection = SingleConnection.getConn();
	}

	public void gravarSqlRodado(String nome_file) throws Exception {
		String sql = "insert into versionadorbanco(arquivo_sql) VALUES (?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, nome_file);
		preparedStatement.execute();
	}

	public boolean arquivoSqlRodado(String nome_arquivo) throws Exception {
		String sql = "select count(1) > 0 as rodado from versionadorbanco where arquivo_sql = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, nome_arquivo);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		return resultSet.getBoolean("rodado");

	}
}