package com.akash.hibernate.hs;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.akash.hibernate.hibernatetut.singledomain.HibernateUtil;

public class HSSearch {

	public void createQuery() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		Transaction tx = fullTextSession.beginTransaction();

		// create native Lucene query unsing the query DSL
		// alternatively you can write the Lucene query using the Lucene query
		// parser
		// or the Lucene programmatic API. The Hibernate Search DSL is
		// recommended though
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(AdharCardInfo.class).get();
		org.apache.lucene.search.Query query = qb.keyword()
				.onFields("adharNumber", "person.username", "person.age")
				.matching("amitabh").createQuery();

		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, AdharCardInfo.class);

		// execute search
		List result = hibQuery.list();
		if (result != null) {
			System.out.println("list size : " + result.size());
		}

		tx.commit();
		session.close();
	}
}