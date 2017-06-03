package br.cefetrj.webdep.services;

import javax.persistence.Query;

import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import br.cefetrj.webdep.model.dao.PersistenceManager;
import br.cefetrj.webdep.model.entity.PadraoURL;
import br.cefetrj.webdep.model.entity.Versao;


public class RelacaoAcessoFalhasServices {
	/**
	 * Realiza a busca no banco de dados e cria o gráfico com os dados encontrados
	 * @param padrao
	 * @param versao
	 * @param httpErro
	 * @param httpOk
	 */
	public static void buscarGrafico(PadraoURL padrao, Versao versao, String httpErro, String httpOk){
		int[] dados = new int[2];
		Query q;
		String query = "select r from registrologacesso"
				     + "where r.codigo = " + httpErro + " and " 
             + versao.getSistema().getId() + " = r.sistema_id";
		
		q = PersistenceManager.getInstance().createQuery(query);
		dados[0] = q.getMaxResults();
		
		query = "select r from registrologacesso"
				     + "where r.codigo = " + httpOk + " and " 
             + versao.getSistema().getId() + " = r.sistema_id";
		
		q = PersistenceManager.getInstance().createQuery(query);
		dados[1] = q.getMaxResults();
		
		/*try {
			RConnection c = new RConnection();
			c.assign("dados", dados);
			c.eval("png(tf1 <- tempfile(fileext = '.png'))");
			c.eval("plot(dados)"); 
			c.eval("dev.off()");c.eval("library(RCurl)");c.eval("txt<- base64Encode(readBin(tf1, 'raw', file.info(tf1)[1, 'size']), 'txt')");
			String img = c.eval("sprintf('<img src=\"data:image/png;base64,%s\">', txt)").asString();
			//Passar o grafico para a jsp
			c.close();

		} catch (RserveException e) {
			e.printStackTrace();
		} catch(REngineException e){
			e.printStackTrace();
		}*/
	}
}
