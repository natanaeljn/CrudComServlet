package servlet;

import java.io.Serializable;
import java.sql.Connection;

import connection.SingleConnection;
import dao.DAOUsuarioRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.ModelLogin;

public class ServletGenericUtil extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public Long getUserLogado(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado).getId();
	}

	public ModelLogin getUserLogadoObj(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		String usuarioLogado = (String) session.getAttribute("usuario");
		return daoUsuarioRepository.consultarUsuarioLogado(usuarioLogado);
	}

}
