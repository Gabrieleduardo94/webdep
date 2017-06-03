package br.cefetrj.webdep.view.command;

import java.io.IOException;
import java.util.StringJoiner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.cefetrj.webdep.model.entity.PadraoURL;
import br.cefetrj.webdep.model.entity.Versao;
import br.cefetrj.webdep.services.PadraoURLServices;
import br.cefetrj.webdep.services.RelacaoAcessoFalhasServices;
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
		PadraoURL p = PadraoURLServices.retornaPeloNome(padrao);
		Versao v = VersionServices.retornaPeloNome(versao);
		if(p == null){
			msgKeys.add("Padrão inválido");
		}
		
		if(v == null){
			msgKeys.add("Padrão inválido");
		}
		
		if(msgKeys.toString() == ""){
			RelacaoAcessoFalhasServices.buscarGrafico(p, v, httpErro, httpOK);
		}
		else{
			request.setAttribute("msg", msgKeys.toString());
			request.getRequestDispatcher("RelatorioAcessoFalhas.jsp").forward(request, response);
		}
	}
}