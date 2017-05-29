package br.cefetrj.webdep.view.command;

import java.io.IOException;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.webdep.model.dao.PadraoDAO;
import br.cefetrj.webdep.model.dao.VersaoDAO;
import br.cefetrj.webdep.services.RelacaoAcessoFalhasService;

public class RelacaoAcessoFalhasCommand implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String padrao;
		String versao;
		String httpErro;
		String httpOK;
		StringJoiner msgKeys = new StringJoiner(",");
		msgKeys.setEmptyValue("");
		
		padrao = request.getParameter("padrao");
		versao = request.getParameter("versao");
		httpErro= request.getParameter("httpErro");
		httpOK = request.getParameter("httpOK");
		
		//Valida��o dos campos
		if(!PadraoDAO.existePadrao(padrao)){
			msgKeys.add("Padr�o inv�lido");
		}
		if(!VersaoDAO.existeVersao(versao)){
			msgKeys.add("Vers�o inv�lida");
		}
		
		
		if(msgKeys.toString() == ""){
			RelacaoAcessoFalhasService.buscarGrafico(padrao, versao, httpErro, httpOK);
		}
		else{
			request.setAttribute("msg", msgKeys.toString());
			request.getRequestDispatcher("RelatorioAcessoFalhas.jsp").forward(request, response);
		}
	}
}