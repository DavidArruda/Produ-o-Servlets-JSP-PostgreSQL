/**
 * 
 */
package beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author david
 *
 */
public class BeanOrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date dataEmissao;
	private Date dataEntrega;
	private Integer quantidade;
	private String status;
	private BeanProduto produtoId = new BeanProduto();
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private List<StatusOrdemServico> statusOrdemServicos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public Long getLongProdutoId() {
		return produtoId.getId();
	}
	public void setLongProdutoId(Long produtoId) {
		this.produtoId.setId(produtoId); 
	}
	
	
	public BeanProduto getProdutoId() {
		return produtoId;
	}
	
	public void setProdutoId(BeanProduto produtoId) {
		this.produtoId = produtoId;
	}
	


	public String getDateEntrega() {
		return format.format(dataEntrega);
	}

	public String getDateEmissao() {
		return format.format(dataEmissao);
	}

	public List<StatusOrdemServico> getStatusOrdemServicos() {
		return statusOrdemServicos;
	}

	public void setStatusOrdemServicos(List<StatusOrdemServico> statusOrdemServicos) {
		this.statusOrdemServicos = statusOrdemServicos;
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
		BeanOrdemServico other = (BeanOrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BeanOrdemServico [id=" + id + ", dataEmissao=" + dataEmissao + ", dataEntrega=" + dataEntrega
				+ ", quantidade=" + quantidade + ", status=" + status + ", produtoId=" + produtoId
				+ ", statusOrdemServicos=" + statusOrdemServicos + "]";
	}

}
