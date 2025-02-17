/**
 * 
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author david
 *
 */
public class BeanProduto implements Serializable {
	
	public BeanProduto() {

	}

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String pn;
	private String cliente;
	private String descricao;
	private List<BeanOperacao> operacoes = new ArrayList<>();

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<BeanOperacao> getOperacoes() {
		return operacoes;
	}
	
	public void setOperacoes(List<BeanOperacao> operacoes) {
		this.operacoes = operacoes;
	}

	@Override
	public String toString() {
		return "" + pn + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanProduto other = (BeanProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
