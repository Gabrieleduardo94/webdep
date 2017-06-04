package br.cefetrj.webdep.view.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class RelacaoAcessoFalhasTag extends SimpleTagSupport{
	private int largura;
	private int altura;
	private String tipoGrafico;
	private Object dados;
	
	public int getLargura() {
		return largura;
	}
	public void setLargura(int largura) {
		this.largura = largura;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public String getTipoGrafico() {
		return tipoGrafico;
	}
	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}
	
	public Object getDados() {
		return dados;
	}
	public void setDados(Object dados) {
		this.dados = dados;
	}
	
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		//Escrever a html que vai exibir o gráfico
		//Usar o método buscarGráfico da classe RelacaoAcessoFalhasChartCommand
	}
}
