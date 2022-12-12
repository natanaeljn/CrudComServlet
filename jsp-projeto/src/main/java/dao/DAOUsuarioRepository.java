package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnection;
import model.ModelLogin;

public class DAOUsuarioRepository {
	private Connection connection;

	public DAOUsuarioRepository() {
		connection = SingleConnection.getConn();
	}

	public ModelLogin gravarUsuario(ModelLogin obj ,Long getUserLogado) throws Exception {

		if (obj.isNovo()) {
			String sql = " INSERT INTO  model_login(login , senha, nome , email , usuarioid , perfil , sexo) VALUES ( ? , ? , ? , ? , ? , ? , ?) ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, obj.getLogin());
			preparedStatement.setString(2, obj.getSenha());
			preparedStatement.setString(3, obj.getNome());
			preparedStatement.setString(4, obj.getEmail());
			preparedStatement.setLong(5, getUserLogado);
			preparedStatement.setString(6, obj.getPerfil());
			preparedStatement.setString(7, obj.getSexo());
			preparedStatement.execute();
			connection.commit();
			
			if(obj.getFotoUSer() != null && !obj.getFotoUSer().isEmpty()) {
				sql = "update model_login  set fotouser =?, extensaofoto =? where login =?" ;
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, obj.getFotoUSer());
				preparedStatement.setString(2, obj.getExtensaoFotoUser());
				preparedStatement.setString(3, obj.getLogin());
				preparedStatement.execute();
				connection.commit();
			}

		} else {
			String sql = "UPDATE model_login SET login=?, senha=?,  nome=?, email=? , perfil=? , sexo=? WHERE id = " + obj.getId() + "";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, obj.getLogin());
			preparedStatement.setString(2, obj.getSenha());
			preparedStatement.setString(3, obj.getNome());
			preparedStatement.setString(4, obj.getEmail());
			preparedStatement.setString(5, obj.getPerfil());
			preparedStatement.setString(6, obj.getSexo());
			preparedStatement.executeUpdate();
			connection.commit();
			
			if(obj.getFotoUSer() != null && !obj.getFotoUSer().isEmpty()) {
				sql = "update model_login  set fotouser =?, extensaofoto =? where id =?" ;
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, obj.getFotoUSer());
				preparedStatement.setString(2, obj.getExtensaoFotoUser());
				preparedStatement.setLong(3, obj.getId());
				preparedStatement.execute();
				connection.commit();
			}

		}
		return this.consultarUsuario(obj.getLogin(),getUserLogado);
	}

	public List<ModelLogin> consultaUsuarioList(String nome,Long getUserLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where upper(nome) like upper(?) and  useradmin  is false and usuarioid = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, getUserLogado);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			// modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));

			retorno.add(modelLogin);
		}

		return retorno;
	}
	public ModelLogin consultarUsuarioLogado(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where upper(login) = upper( '" + login + "' )  ";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next())/* se tiver um resultado */ {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUserAdmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUSer(resultado.getString("fotouser"));
			
		}

		return modelLogin;
	}
	public ModelLogin consultarUsuario(String login) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where upper(login) = upper( '" + login + "' ) and  useradmin  is false ";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next())/* se tiver um resultado */ {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setUserAdmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUSer(resultado.getString("fotouser"));
		}

		return modelLogin;
	}

	public ModelLogin consultarUsuario(String login,Long getUserLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where upper(login) = upper( '" + login + "' ) and  useradmin  is false and usuarioid= "+getUserLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next())/* se tiver um resultado */ {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUSer(resultado.getString("fotouser"));
		}

		return modelLogin;
	}

	public ModelLogin consultarUsuarioId(String id,Long getUserLogado) throws Exception {
		ModelLogin modelLogin = new ModelLogin();
		String sql = "select * from model_login where id = ? and  useradmin  is false and usuarioid = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, getUserLogado);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next())/* se tiver um resultado */ {
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUSer(resultado.getString("fotouser"));
		}

		return modelLogin;
	}

	/* metodo para validar e ver se ja existe o login , retornando true ou false */
	public boolean validaLogin(String login) throws Exception {
		String sql = "select count(1)> 0 as existe from model_login where upper(login)=upper('" + login + "')";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		resultado.next();
		return resultado.getBoolean("existe");

	}

	public void deletarUser(String UserId) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and  useradmin  is false; ";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(UserId));
		statement.executeUpdate();
		connection.commit();
	}

	public List<ModelLogin> consultaUsuarioCompleta(Long getUserLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		String sql = "select * from model_login where useradmin  is false and usuarioid = " + getUserLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultSet.getString("email"));
			modelLogin.setId(resultSet.getLong("id"));
			modelLogin.setLogin(resultSet.getString("login"));
			modelLogin.setNome(resultSet.getString("nome"));
			// modelLogin.setSenha(resultSet.getString("senha"));
			modelLogin.setPerfil(resultSet.getString("perfil"));
			modelLogin.setSexo(resultSet.getString("sexo"));
			retorno.add(modelLogin);
		}

		return retorno;
	}

}
