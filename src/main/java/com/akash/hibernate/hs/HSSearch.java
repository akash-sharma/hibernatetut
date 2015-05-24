package com.akash.hibernate.hs;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
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
				.onFields("person.username")
				.matching("AmiTabh").createQuery();

		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, AdharCardInfo.class);
		
		// execute search
		List result = hibQuery.list();
		if (result != null) {
			System.out.println("list size : " + result.size());
		}
		
		Analyzer analyzer = fullTextSession.getSearchFactory().getAnalyzer( AdharCardInfo.class );
		QueryParser parser = new QueryParser(Version.LUCENE_36, "person.username", analyzer);
		try {
			Query luceneQuery = parser.parse( "person.username:AMTP" );
			List list = fullTextSession.createFullTextQuery( luceneQuery, AdharCardInfo.class ).list();
			if(list.size()>0) {
				for(int counter=0; counter<list.size(); counter++) {
					AdharCardInfo aci = (AdharCardInfo)list.get(counter);
					System.out.println(counter+1 +" person: "+aci.getPerson().getUsername()+" , id:"+aci.getId());
				}
			}
			System.out.println("username list :"+list.size());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		

		tx.commit();
		session.close();
	}
}