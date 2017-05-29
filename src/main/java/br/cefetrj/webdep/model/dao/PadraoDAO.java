package br.cefetrj.webdep.model.dao;

import java.util.List;

import javax.persistence.Query;

import br.cefetrj.webdep.model.entity.PadraoURL;


public class PadraoDAO {
	public static boolean existePadrao(String padrao){
		Query query = PersistenceManager.getInstance().createQuery("SELECT p from PADRAOURL as p WHERE " + padrao + " = p.nome");
		List<PadraoURL> q = query.getResultList();
		return q.isEmpty();
	}
}
