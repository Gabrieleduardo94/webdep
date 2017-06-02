package br.cefetrj.webdep.view.command;

import java.io.IOException;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.webdep.services.PadraoURLServices;
import br.cefetrj.webdep.services.RelacaoAcessoFalhasService;
import br.cefetrj.webdep.services.VersionServices;

/**
 * Command para validar os dados passados pelo HTTPreport.jsp
 * @author vinicius
 *
 */
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
		
		//Validação dos campos
		if(padrao.length() != 0 && padrao != null){
			if(!PadraoURLServices.verificaSeExiste(padrao)){
				msgKeys.add("Padrão inválido");
			}
		}
		
		if(padrao.length() != 0 && versao != null){
			if(!VersionServices.verificaSeExiste(versao)){
				msgKeys.add("Versão inválida");
			}
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