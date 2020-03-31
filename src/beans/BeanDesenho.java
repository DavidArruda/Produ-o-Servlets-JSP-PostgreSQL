package beans;

import java.io.Serializable;

public class BeanDesenho implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String dBase64;
	private String fContentType;
	private String dBase64Miniatura;
	private BeanOperacao operacao_id = new BeanOperacao();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getdBase64() {
		return dBase64;
	}

	public void setdBase64(String dBase64) {
		this.dBase64 = dBase64;
	}

	public String getfContentType() {
		return fContentType;
	}

	public void setfContentType(String fContentType) {
		this.fContentType = fContentType;
	}

	public String getdBase64Miniatura() {
		return dBase64Miniatura;
	}

	public void setdBase64Miniatura(String dBase64Miniatura) {
		this.dBase64Miniatura = dBase64Miniatura;
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
		BeanDesenho other = (BeanDesenho) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
