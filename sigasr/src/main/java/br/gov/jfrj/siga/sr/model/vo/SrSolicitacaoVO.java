package br.gov.jfrj.siga.sr.model.vo;

import br.gov.jfrj.siga.dp.DpLotacao;
import br.gov.jfrj.siga.dp.DpPessoa;
import br.gov.jfrj.siga.sr.model.SrLista;
import br.gov.jfrj.siga.sr.model.SrMarca;
import br.gov.jfrj.siga.sr.model.SrMovimentacao;
import br.gov.jfrj.siga.sr.model.SrPrioridade;
import br.gov.jfrj.siga.sr.model.SrPrioridadeSolicitacao;
import br.gov.jfrj.siga.sr.model.SrSolicitacao;
import br.gov.jfrj.siga.sr.util.SrViewUtil;

public class SrSolicitacaoVO {
	
	private String cssClass = "";

	private String botaoRemover = "";
	private String botaoPriorizar = "";
	private String botaoExpandir = "+";
	private String botaoRemoverPriorizar = "";
	private Long idSolicitacao;
	private Long idPrioridadeSolicitacao;

	// Edson: colunas ordenáveis:
	private String dtReg = "";
	private String codigo = "";
	private String descrSolicitacao = "";
	private String solicitante = "";
	private String lotaSolicitante = "";
	private String cadastrante = "";
	private String lotaTitular = "";
	private String prioridade = "";
	private SrPrioridade prioridadeNaLista;
	private String prioridadeTecnica = "";
	private String posicaoNaLista = "";
	private boolean naoReposicionarAutomaticoNaLista = false;
	private String situacao = "";
	private String atendente = "";
	private String lotaAtendente = "";
	private String dtUltimaMovimentacao = "";
	private String ultimaMovimentacao = "";

	public SrSolicitacaoVO(SrSolicitacao sol, SrMarca m, SrMovimentacao ultMov, SrLista lista,
			SrPrioridadeSolicitacao prioridadeSolicitacao,
			DpLotacao lotaTitular, DpPessoa titular, String propriedade, boolean isPopup,
			boolean podeRemover, boolean podePriorizar)
			throws Exception {
		this.setIdSolicitacao(sol.getId());
		this.setIdPrioridadeSolicitacao(prioridadeSolicitacao != null ? prioridadeSolicitacao.getIdPrioridadeSolicitacao() : null);

		this.setDtReg(sol.getSolicitacaoInicial().getDtRegString());
		if (isPopup)
			setCodigo("<a href=\"javascript:opener.retorna_" + propriedade
					+ "(&#039;" + getIdSolicitacao() + "&#039;,&#039;" + codigo
					+ "&#039;,&#039;" + codigo + "&#039;);window.close()\">"
					+ sol.getCodigo() + "</a>");
		else
			setCodigo("<a href=\"/sigasr/app/solicitacao/exibir/" + sol.getId()
					+ "\">" + sol.getCodigo() + "</a>");

		this.setDescrSolicitacao("<b>"
				+ (sol.getDnmItemConfiguracao() != null ? sol.getDnmItemConfiguracao().getTituloItemConfiguracao() : "Item não informado")
				+ ":</b>&nbsp;" + SrViewUtil.selecionado(sol.getDescricao(), sol.getDescricao()));

		String nomeCadastranteAbreviado = sol.getCadastrante() != null ? sol
				.getCadastrante().getNomeAbreviado() : "";
		String nomeCadastrante = sol.getCadastrante() != null ? sol
				.getCadastrante().getNomePessoa() : "";
		this.setCadastrante(SrViewUtil.selecionado(nomeCadastranteAbreviado,nomeCadastrante));
		
		this.setLotaTitular(SrViewUtil.selecionado(sol.getLotaTitular()
				.getSiglaCompleta(), sol.getLotaTitular().getNomeLotacao()));

		String nomeSolicitanteAbreviado = sol.getSolicitante() != null ? sol.getSolicitante().getNomeAbreviado() : "";
		String nomeSolicitante = sol.getSolicitante() != null ? sol.getSolicitante().getNomePessoa() : "";
		this.setSolicitante(SrViewUtil.selecionado(nomeSolicitanteAbreviado,nomeSolicitante));
		
		String siglaLotaSolicitante = sol.getLotaSolicitante() != null ? sol.getLotaSolicitante().getSiglaCompleta() : "";
		String nomeLotaSolicitante = sol.getLotaSolicitante() != null ? sol.getLotaSolicitante().getNomeLotacao() : "";
		this.setLotaSolicitante(SrViewUtil.selecionado(siglaLotaSolicitante,nomeLotaSolicitante));

		this.setPrioridade(sol.getPrioridadeString());
		this.setPosicaoNaLista(prioridadeSolicitacao != null ? prioridadeSolicitacao
				.getNumPosicao().toString() : "");
		this.setPrioridadeNaLista(prioridadeSolicitacao != null ? prioridadeSolicitacao
				.getPrioridade() : null);
		this.setNaoReposicionarAutomaticoNaLista(prioridadeSolicitacao != null ? prioridadeSolicitacao
				.getNaoReposicionarAutomatico() : false);
		this.setPrioridadeTecnica(sol.getDnmPrioridadeTecnicaString());
		
		if (prioridadeSolicitacao != null)
			this.setCssClass("PRIORIDADE-" + prioridadeSolicitacao.getPrioridade());
		else this.setCssClass("PRIORIDADE-" + sol.getDnmPrioridadeTecnica());

		this.setSituacao(m.getCpMarcador().getDescrMarcador());

		String nomeAtendenteAbreviado = m.getDpPessoaIni() != null ? m
				.getDpPessoaIni().getNomeAbreviado() : "";
		String nomeAtendente = m.getDpPessoaIni() != null ? m.getDpPessoaIni()
				.getNomePessoa() : "";
		this.setAtendente(SrViewUtil.selecionado(nomeAtendenteAbreviado,
				nomeAtendente));
		
		this.setLotaAtendente(SrViewUtil.selecionado(m.getDpLotacaoIni().getLotacaoAtual()
						.getSiglaCompleta(), m.getDpLotacaoIni()
						.getLotacaoAtual().getNomeLotacao()));

		this.setUltimaMovimentacao(ultMov != null ? ultMov.getDescrMovimentacao()!= null ? 
				SrViewUtil.selecionado(ultMov.getDescrMovimentacao(), ultMov.getDescrMovimentacao()) : "" : "");
		this.setDtUltimaMovimentacao(ultMov != null ? ultMov.getDtIniString() : "");
		
		if (podeRemover)
			this.botaoRemover = SrViewUtil.botaoRemoverSolicitacao(
					this.getIdSolicitacao(), lista.getIdLista());

		if (podePriorizar)
			this.botaoPriorizar = SrViewUtil.botaoPriorizarSolicitacao();

		if (podeRemover || podePriorizar)
			this.setBotaoRemoverPriorizar(this.botaoRemover
					+ this.botaoPriorizar);
	}

	public SrPrioridade getPrioridadeNaLista() {
		return prioridadeNaLista;
	}

	public void setPrioridadeNaLista(SrPrioridade prioridadeNaLista) {
		this.prioridadeNaLista = prioridadeNaLista;
	}

	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigoFormatado) {
		this.codigo = codigoFormatado;
	}

	public String getDescrSolicitacao() {
		return descrSolicitacao;
	}

	public void setDescrSolicitacao(String teorFormatado) {
		this.descrSolicitacao = teorFormatado;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitanteFormatado) {
		this.solicitante = solicitanteFormatado;
	}

	public String getBotaoExpandir() {
		return botaoExpandir;
	}

	public void setBotaoExpandir(String botaoExpandir) {
		this.botaoExpandir = botaoExpandir;
	}

	public String getAtendente() {
		return atendente;
	}

	public void setAtendente(String atendente) {
		this.atendente = atendente;
	}

	public String getCadastrante() {
		return cadastrante;
	}

	public void setCadastrante(String cadastrante) {
		this.cadastrante = cadastrante;
	}

	public String getBotaoRemoverPriorizar() {
		return botaoRemoverPriorizar;
	}

	public void setBotaoRemoverPriorizar(String botaoRemoverPriorizar) {
		this.botaoRemoverPriorizar = botaoRemoverPriorizar;
	}

	public String getDtReg() {
		return dtReg;
	}

	public void setDtReg(String dtRegString) {
		this.dtReg = dtRegString;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getPosicaoNaLista() {
		return posicaoNaLista;
	}

	public void setPosicaoNaLista(String posicaoListaPrioridade) {
		this.posicaoNaLista = posicaoListaPrioridade;
	}

	public String getBotaoRemover() {
		return botaoRemover;
	}

	public void setBotaoRemover(String botaoRemover) {
		this.botaoRemover = botaoRemover;
	}

	public String getBotaoPriorizar() {
		return botaoPriorizar;
	}

	public void setBotaoPriorizar(String botaoPriorizar) {
		this.botaoPriorizar = botaoPriorizar;
	}

	public String getLotaSolicitante() {
		return lotaSolicitante;
	}

	public void setLotaSolicitante(String lotaSolicitante) {
		this.lotaSolicitante = lotaSolicitante;
	}

	public String getLotaTitular() {
		return lotaTitular;
	}

	public void setLotaTitular(String lotaCadastrante) {
		this.lotaTitular = lotaCadastrante;
	}

	public String getLotaAtendente() {
		return lotaAtendente;
	}

	public void setLotaAtendente(String lotaAtendente) {
		this.lotaAtendente = lotaAtendente;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getPrioridadeTecnica() {
		return prioridadeTecnica;
	}

	public void setPrioridadeTecnica(String prioridadeTecnica) {
		this.prioridadeTecnica = prioridadeTecnica;
	}

	public String getDtUltimaMovimentacao() {
		return dtUltimaMovimentacao;
	}

	public void setDtUltimaMovimentacao(String dtUltimaMovimentacao) {
		this.dtUltimaMovimentacao = dtUltimaMovimentacao;
	}

	public String getUltimaMovimentacao() {
		return ultimaMovimentacao;
	}

	public void setUltimaMovimentacao(String ultimaMovimentacao) {
		this.ultimaMovimentacao = ultimaMovimentacao;
	}
	
	public boolean isNaoReposicionarAutomaticoNaLista() {
		return naoReposicionarAutomaticoNaLista;
	}

	public void setNaoReposicionarAutomaticoNaLista(
			boolean naoReposicionarAutomaticoNaLista) {
		this.naoReposicionarAutomaticoNaLista = naoReposicionarAutomaticoNaLista;
	}

	public Long getIdPrioridadeSolicitacao() {
		return idPrioridadeSolicitacao;
	}

	public void setIdPrioridadeSolicitacao(Long idPrioridadeSolicitacao) {
		this.idPrioridadeSolicitacao = idPrioridadeSolicitacao;
	}

}