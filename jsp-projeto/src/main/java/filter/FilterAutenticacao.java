package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnection;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/principal/*" }) /* intercepta todas as requisiçoes que vierem do projeto ou mapeamento */
public class FilterAutenticacao extends HttpFilter {
	private static Connection connection;

	public FilterAutenticacao() {

	}

	/* Encerra os processos quando o servidor e parado */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* Intercepta as conexoes e da as respostas no sistema */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			String usuarioLogado = (String) session.getAttribute("usuario");
			String urlParaAutenticar = req.getServletPath();/* url que esta sendo acessada */
			/* validaçao para ver se esta logado , senao redireciona para a tela de login */
			if (usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("/principal/LoginServlet")) {
				/*
				 * vai ser passado a url para a tela de login e depois que ele realizar o login,
				 * vai continuar para levar o login para a url q estava sendo acessada antes
				 */
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o Login");
				/* pegamos o objeto redireciona e lançamos o comando */
				redireciona.forward(request, response);
				return; /* este return e necessario para parar a execuçao e redirecionar para o login */
			} else {
				chain.doFilter(request, response);
			}
			connection.commit();/* Comitando as operaçoes ja que tudo ocorreu certo */
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	/* E executado quando inicia o sistema , inicia os processos */
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnection.getConn();
	}

}
