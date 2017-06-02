package br.cefetrj.webdep.services;

import javax.persistence.Query;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import br.cefetrj.webdep.model.dao.PersistenceManager;


public class RelacaoAcessoFalhasService {
	public static void buscarGrafico(String padrao, String versao, String httpErro, String httpOk){
		PersistenceManager pm = PersistenceManager.getInstance();
		Query query;
		
		try {
			RConnection c = new RConnection();
			//query = fazer a query 
			c.assign("dados", dados);
			c.eval("png(tf1 <- tempfile(fileext = '.png'))");
			c.eval("plot(dados)"); 
			c.eval("dev.off()");c.eval("library(RCurl)");c.eval("txt<- base64Encode(readBin(tf1, 'raw', file.info(tf1)[1, 'size']), 'txt')");
			String img = c.eval("sprintf('<img src=\"data:image/png;base64,%s\">', txt)").asString();
			//Passar o grafico para a jsp
			c.close();

		} catch (RserveException e) {
			e.printStackTrace();
		}
	}
}
