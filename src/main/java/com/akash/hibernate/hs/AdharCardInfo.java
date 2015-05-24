package com.akash.hibernate.hs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.PhoneticFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@AnalyzerDefs({
	@AnalyzerDef(name = AnalyserName.MY_CUSTOM_ANALYSER, 
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), 
		filters = {
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
			@TokenFilterDef(factory = SnowballPorterFilterFactory.class),
			@TokenFilterDef(factory = PhoneticFilterFactory.class, params = {
			      @Parameter(name="encoder", value= "DOUBLEMETAPHONE")}
			)
		}),
	@AnalyzerDef(name = AnalyserName.DEFAULT_ANALYSER, 
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), 
		filters = {
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
			@TokenFilterDef(factory = SnowballPorterFilterFactory.class),
			@TokenFilterDef(factory = PhoneticFilterFactory.class, params = {
			      @Parameter(name="encoder", value= "DOUBLEMETAPHONE")}
			)
		})
})

@Analyzer(definition=AnalyserName.MY_CUSTOM_ANALYSER)
@Entity
@Indexed
public class AdharCardInfo {

	@Id
	@GeneratedValue
	private long id;

	@Field(store = Store.YES, index = Index.YES)
	private String adharNumber;

	@OneToOne
	@IndexedEmbedded
	private IndianCitizen person;

	private String companyName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}

	public IndianCitizen getPerson() {
		return person;
	}

	public void setPerson(IndianCitizen person) {
		this.person = person;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}