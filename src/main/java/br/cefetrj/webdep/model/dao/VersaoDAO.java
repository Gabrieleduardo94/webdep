package br.cefetrj.webdep.model.dao;

import java.util.List;

import javax.persistence.Query;

import br.cefetrj.webdep.model.entity.Versao;

public class VersaoDAO {
	public static boolean existeVersao(String versao){
		Query query = PersistenceManager.getInstance().createQuery("SELECT v from VERSAO as v WHERE " + versao + " = v.nome");
		List<Versao> q = query.getResultList();
		return q.isEmpty();
	}
}
