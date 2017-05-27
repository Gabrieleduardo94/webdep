package br.cefetrj.webdep.view.command;

import java.io.IOException;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.webdep.services.RelatorioAcessoFalhasService;

public class RelatorioAcessoFalhasCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String padrao;
	String versao;
	String httpErro;
	String httpOK;
	StringJoiner msgKeys = new StringJoiner(",");
	msgKeys.setEmptyValue("");
	boolean valido = true;
	
	padrao = request.getParameter("padrao");
	versao = request.getParameter("versao");
	httpErro= request.getParameter("httpErro");
	httpOK = request.getParameter("httpOK");
	
	//Fazer a validação dos campos
	
	if(valido){
		RelatorioAcessoFalhasService.buscarGrafico(padrao, versao, httpErro, httpOK);
	}
	else{
		request.setAttribute("msg", msgKeys.toString());
		request.getRequestDispatcher("RelatorioAcessoFalhas.jsp").forward(request, response);
	}
}