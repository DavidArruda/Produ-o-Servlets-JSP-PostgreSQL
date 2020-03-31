package beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class BeanApontamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Time inicio;
	private Time termino;
	private Date dataAp;
	private Integer quantidade;
	private Boolean finalizado;
	private BeanUsuario usuario = new BeanUsuario();
	private BeanOperacao operacao = new BeanOperacao();
	private Boolean opAtual;
	private BeanOrdemServico ordemServico = new BeanOrdemServico();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getInicio() {
		return inicio;
	}

	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}

	public Time getTermino() {
		return termino;
	}

	public void setTermino(Time termino) {
		this.termino = termino;
	}

	public Date getDataAp() {
		return dataAp;
	}

	public void setDataAp(Date dataAp) {
		this.dataAp = dataAp;
	}

	public BeanUsuario getUsuario() {
		return usuario;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Boolean getOpAtual() {
		return opAtual;
	}

	public void setOpAtual(Boolean opAtual) {
		this.opAtual = opAtual;
	}

	public void setUsuario(BeanUsuario usuario) {
		this.usuario = usuario;
	}

	public BeanOperacao getOperacao() {
		return operacao;
	}

	public void setOperacao(BeanOperacao operacao) {
		this.operacao = operacao;
	}

	public BeanOrdemServico getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(BeanOrdemServico ordemServico) {
		this.ordemServico = ordemServico;
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
		BeanApontamento other = (BeanApontamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
