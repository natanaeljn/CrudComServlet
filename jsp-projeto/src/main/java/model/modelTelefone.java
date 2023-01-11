package model;

import java.io.Serializable;
import java.util.Objects;

public class modelTelefone implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id ; 
	private String numero ; 
	private ModelLogin usuarioPai;
	private ModelLogin usuarioCadastro;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public ModelLogin getUsuarioPai() {
		return usuarioPai;
	}
	public void setUsuarioPai(ModelLogin usuarioPai) {
		this.usuarioPai = usuarioPai;
	}
	public ModelLogin getUsuarioCadastro() {
		return usuarioCadastro;
	}
	public void setUsuarioCadastro(ModelLogin usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		modelTelefone other = (modelTelefone) obj;
		return Objects.equals(id, other.id);
	}
}
	
	