/**
 * 
 */
package beans;

/**
 * @author david
 *
 */
public class BeanTipoUsuario {

	private Long codTipo;
	private String descricao;
	private String setor;

	public Long getCodTipo() {
		return codTipo;
	}

	public void setCodTipo(Long codTipo) {
		this.codTipo = codTipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	@Override
	public String toString() {
		return " " + descricao + "";
	}
	
	

}
