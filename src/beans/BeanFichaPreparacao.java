/**
 * 
 */
package beans;

import java.io.Serializable;

/**
 * @author david
 *
 */
public class BeanFichaPreparacao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String fBase64;
	private String fContentType;
	private BeanOperacao operacao_id = new BeanOperacao();
	private boolean atualizar = true;
	
	public boolean isAtualizar() {
		return atualizar;
	}
	
	public void setAtualizar(boolean atualizar) {
		this.atualizar = atualizar;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long codFicha) {
		this.id = codFicha;
	}

	public String getfBase64() {
		return fBase64;
	}

	public void setfBase64(String fBase64) {
		this.fBase64 = fBase64;
	}

	public String getfContentType() {
		return fContentType;
	}

	public void setfContentType(String fContentType) {
		this.fContentType = fContentType;
	}
	
	public BeanOperacao getOperacao_id() {
		return operacao_id;
	}
	
	public void setOperacao_id(BeanOperacao operacao_id) {
		this.operacao_id = operacao_id;
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
		BeanFichaPreparacao other = (BeanFichaPreparacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
