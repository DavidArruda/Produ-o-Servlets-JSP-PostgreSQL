/**
 * 
 */
package beans;

import java.io.Serializable;
import java.sql.Time;

/**
 * @author david
 *
 */
public class BeanOperacao implements Serializable {
	
	public BeanOperacao() {
		
	}

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String descricao;
	private String maquina;
	private Time tempo_estimado;
	private Integer n_operacao;
	private BeanProduto produto_id = new BeanProduto();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public Time getTempo_estimado() {
		return tempo_estimado;
	}

	public void setTempo_estimado(Time tempo_estimado) {
		this.tempo_estimado = tempo_estimado;
	}

	public Integer getN_operacao() {
		return n_operacao;
	}

	public void setN_operacao(Integer n_operacao) {
		this.n_operacao = n_operacao;
	}

	public BeanProduto getProduto_id() {
		return produto_id;
	}

	public void setProduto_id(BeanProduto produto_id) {
		this.produto_id = produto_id;
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
		BeanOperacao other = (BeanOperacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
