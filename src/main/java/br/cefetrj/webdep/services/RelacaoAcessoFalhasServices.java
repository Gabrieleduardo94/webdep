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
		Long sistemaId = versao.getSistema().getId();
		Query q;
		String query = "select r from registrologacesso r"
				     + "where r.codigo = :httpErro and " 
                     + ":sistemaId = r.sistema_id";
		
		q = PersistenceManager.getInstance().createQuery(query);
		dados[0] = q.getMaxResults();
		
		query = "select r from registrologacesso r"
		      + "where r.codigo = :httpOk and " 
              + ":sistemaId = r.sistema_id";
		
		q = PersistenceManager.getInstance().createQuery(query);
		dados[1] = q.getMaxResults();
		
		q.setParameter("htppErro", httpErro);
		q.setParameter("sistemaId", sistemaId);
		q.setParameter("httpOk", httpOk);
	}
}
