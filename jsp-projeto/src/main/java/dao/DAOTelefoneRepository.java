package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.modelTelefone;

public class DAOTelefoneRepository {
	private Connection connection;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public DAOTelefoneRepository() {
		connection = SingleConnection.getConn();
	}

	public List<modelTelefone> listaFones(Long idUser) throws Exception {
		List<modelTelefone> retorno = new ArrayList<modelTelefone>();
		String sql = " select * from telefone where usuario_pai_id = ? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUser);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			modelTelefone modelTelefone = new modelTelefone();
			modelTelefone.setId(rs.getLong("id"));
			modelTelefone.setNumero(rs.getString("numero"));
			modelTelefone.setUsuarioCadastro(daoUsuarioRepository.consultarUsuarioID(rs.getLong("usuario_cad_id")));
			modelTelefone.setUsuarioPai(daoUsuarioRepository.consultarUsuarioID(rs.getLong("usuario_pai_id")));
			retorno.add(modelTelefone);
		}

		return retorno;
	}

	public void gravaTelefone(modelTelefone modelTelefone) throws Exception {
		String sql = " insert into telefone (numero, usuario_pai_id, usuario_cad_id) values ( ? , ? , ? ) ";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.setString(1, modelTelefone.getNumero());
		preparedStatement.setLong(2, modelTelefone.getUsuarioPai().getId());
		preparedStatement.setLong(3, modelTelefone.getUsuarioCadastro().getId());

		preparedStatement.execute();

		connection.commit();
	}

	public void deletarFone(Long id) throws Exception {
		String sql = "delete from telefone where id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, id);
		preparedStatement.executeUpdate();
		connection.commit();
	}

	public boolean existeFone(String fone, long idUser) throws Exception {
		String sql = "select count(1) > 0 as existe from telefone where usuario_pai_id =? and numero=? ";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1, idUser);
		preparedStatement.setString(2, fone);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		return resultSet.getBoolean("existe");

	}

}
