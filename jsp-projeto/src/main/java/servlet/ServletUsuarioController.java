package servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.ModelLogin;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
@MultipartConfig
@WebServlet(urlPatterns = {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
	String msg;
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

	public ServletUsuarioController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				String UserId = request.getParameter("id");
				daoUsuarioRepository.deletarUser(UserId);
				List<ModelLogin>user = daoUsuarioRepository.consultaUsuarioCompleta(super.getUserLogado(request));
				request.setAttribute("modelLogins", user);
				
				
				request.setAttribute("msg", "Excluido com Sucesso");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				String UserId = request.getParameter("id");
				daoUsuarioRepository.deletarUser(UserId);

				response.getWriter().write("Excluido com Sucesso");
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {

				String nomeBusca = request.getParameter("nomeBusca");
				System.out.println(nomeBusca);

				List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca,super.getUserLogado(request));

				ObjectMapper mapper = new ObjectMapper();

				String json = mapper.writeValueAsString(dadosJsonUser);

				response.getWriter().write(json);

			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				String id = request.getParameter("id");
				ModelLogin modelLogin = daoUsuarioRepository.consultarUsuarioId(id,super.getUserLogado(request));
				List<ModelLogin>user = daoUsuarioRepository.consultaUsuarioCompleta(super.getUserLogado(request));
				request.setAttribute("modelLogins", user);
				
				request.setAttribute("msg", "usuario em edição");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
				request.setAttribute("modelLogin", modelLogin);
				redireciona.forward(request, response);

			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				List<ModelLogin>user = daoUsuarioRepository.consultaUsuarioCompleta(super.getUserLogado(request));
				request.setAttribute("msg", "Usuarios carregados com sucesso");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
				request.setAttribute("modelLogins", user);
				redireciona.forward(request, response);
				
				
				
			}
		 else if (acao != null && !acao.isEmpty()&& acao.equals("downloadFoto")) {
    		String id = request.getParameter("id");
    		var modelLogin = daoUsuarioRepository.consultarUsuarioId(id,super.getUserLogado(request));
    		if (modelLogin!= null && !acao.isEmpty()) {
    			response.setHeader("Content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaoFotoUser());
    			response.getOutputStream().write(Base64.decodeBase64(modelLogin.getFotoUser().split("\\,")[1]));
    		}
		 }
		 else if (acao != null && !acao.isEmpty()&& acao.equals("paginar")) {
			 Integer offset =Integer.parseInt(request.getParameter("pagina"));
			 List<ModelLogin>user = daoUsuarioRepository.consultaUsuarioCompletaPaginada(this.getUserLogado(request), offset);
			 request.setAttribute("modelLogins", user);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 
		 }
			else {
				List<ModelLogin>user = daoUsuarioRepository.consultaUsuarioCompleta(super.getUserLogado(request));
				request.setAttribute("modelLogins", user);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		 } catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			msg = "operaçao realizada";
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
     		String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);
			modelLogin.setPerfil(perfil);
			modelLogin.setSexo(sexo);
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf);
			modelLogin.setNumero(numero);
			
			var part= request.getPart("filefoto") ;
			if(ServletFileUpload.isMultipartContent(request) && part.getSize() > 0) {
				var extensao = part.getContentType().split("\\/")[1];
				byte[ ] foto = IOUtils.toByteArray(part.getInputStream() );/*converte imagem para byte*/
				var base64 = "data:image/" + extensao + ";base64," + Base64.encodeBase64String(foto);
				modelLogin.setFotoUser(base64);
				modelLogin.setExtensaoFotoUser(extensao);
			}
			/*
			 * a parte do id ==null é para testar se estamos tentando inserir um novo
			 * usuario
			 */
			if (daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "ja existe usuario com este login ,  insira outro valor de login";
			} else {

				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin,super.getUserLogado(request));
			}
			List<ModelLogin>user = daoUsuarioRepository.consultaUsuarioCompleta(super.getUserLogado(request));
			request.setAttribute("modelLogins", user);

			request.setAttribute("msg", msg);
			request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			RequestDispatcher redireciona = request.getRequestDispatcher("principal/usuario.jsp");
			request.setAttribute("modelLogin", modelLogin);
			redireciona.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
