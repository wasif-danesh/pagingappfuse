package com.pobox.paging.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.appfuse.model.BaseObject;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.*;

@SuppressWarnings("unchecked")
@Entity
@Searchable(alias = "authority")
@SearchableAllMetaData(enable = EnableAll.TRUE)
@SearchableConstant(name = "type", values = { "authority" })
public class Authority extends BaseObject implements Comparable<Authority> {
	private static final long serialVersionUID = -5633829061950556162L;
	private Long id;
	private String authorityName;
	private Date version;

	@SearchableProperty(name = "name")
	@SearchableMetaData(name = "authorityName")
	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	@Version
	@SearchableProperty(name = "versionDate")
	@SearchableMetaData(name = "version", format = "dd-MM-yyyy")
	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Authority myClass) {
		return new CompareToBuilder().append(this.id, myClass.id).append(this.version, myClass.version).append(
				this.authorityName, myClass.authorityName).toComparison();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Authority)) {
			return false;
		}
		Authority rhs = (Authority) object;
		return new EqualsBuilder().append(this.id, rhs.id).append(this.version, rhs.version).append(this.authorityName,
				rhs.authorityName).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(896218595, -572312663).append(this.id).append(this.version).append(
				this.authorityName).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("authorityName", this.authorityName).append(
				"version", this.version).toString();
	}

}
