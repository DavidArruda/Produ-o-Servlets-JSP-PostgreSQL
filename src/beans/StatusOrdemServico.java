package beans;

import java.io.Serializable;

public class StatusOrdemServico implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long ordemServico;
	private Long operacao;

	public Long getOperacao() {
		return operacao;
	}

	public void setOperacao(Long operacao) {
		this.operacao = operacao;
	}

	public Long getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(Long ordemServico) {
		this.ordemServico = ordemServico;
	}

}
