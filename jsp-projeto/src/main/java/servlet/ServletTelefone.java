package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import model.modelTelefone;

import java.io.IOException;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;

@WebServlet(urlPatterns = { "/ServletTelefone" })
public class ServletTelefone extends ServletGenericUtil {

	private static final long serialVersionUID = 1L;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();

	public ServletTelefone() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			if(acao!=null && !acao.isEmpty()) {
				String idFone = request.getParameter("id");
				String userPai = request.getParameter("userpai");
				
				daoTelefoneRepository.deletarFone(Long.parseLong(idFone));
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(userPai));
				List<modelTelefone> modelTelefones = daoTelefoneRepository.listaFones(modelLogin.getId());
			    request.setAttribute("modelTelefones", modelTelefones);
				request.setAttribute("msg", "Excluido com sucesso");
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
				return;
			}
			
			
			
			String idUser = request.getParameter("iduser");

			if (idUser != null && !idUser.isEmpty()) {

				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(idUser));
				List<modelTelefone> modelTelefones = daoTelefoneRepository.listaFones(modelLogin.getId());
			    request.setAttribute("modelTelefones", modelTelefones);
				
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);

			} else {
				List<ModelLogin> user = daoUsuarioRepository.consultaUsuarioCompleta(super.getUserLogado(request));
				request.setAttribute("modelLogins", user);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String usuarioPai = request.getParameter("id");
			String numero = request.getParameter("numero");
			modelTelefone modelTelefone = new modelTelefone();
			
			modelTelefone.setNumero(numero);
			modelTelefone.setUsuarioPai(daoUsuarioRepository.consultarUsuarioID(Long.parseLong(usuarioPai)));
			modelTelefone.setUsuarioCadastro(super.getUserLogadoObj(request));
			daoTelefoneRepository.gravaTelefone(modelTelefone);
			
			List<modelTelefone> modelTelefones = daoTelefoneRepository.listaFones(Long.parseLong(usuarioPai));
			ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioID(Long.parseLong(usuarioPai));
			request.setAttribute("modelLogin", modelLogin);
			request.setAttribute("modelTelefones", modelTelefones);
			request.setAttribute("msg", "Salvo com Sucesso");
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
