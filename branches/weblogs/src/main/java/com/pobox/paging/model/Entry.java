package com.pobox.paging.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.compass.annotations.EnableAll;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableAllMetaData;
import org.compass.annotations.SearchableBoostProperty;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableConstant;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableParent;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

@Entity
@Searchable( alias = "entry")
@SearchableAllMetaData(enable = EnableAll.TRUE)
@SearchableConstant(name = "type", values = { "entry" })
public class Entry extends BaseObject {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 5615735304641846996L;
    private Long id;
    private String text;
    private Timestamp timeCreated;
    private Date version;

    private Weblog weblog;
    private Category category;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @SearchableReference
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "weblog_id")
    @SearchableReference
     public Weblog getWeblog() {
        return weblog;
    }

    public void setWeblog(Weblog weblog) {
        this.weblog = weblog;
    }

    @Id
    @Column(name = "entry_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    public Long getId() {
        return id;
    }

    public void setId(Long entryId) {
        this.id = entryId;
    }

    @SearchableProperty(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @SearchableProperty(name = "timeCreated")
    @SearchableMetaData(name = "timeCreated", format = "HH:MM dd-MM-yyyy")
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry rhs = (Entry) object;
        return new EqualsBuilder().append(this.text, rhs.text).append(
                this.timeCreated, rhs.timeCreated).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-894939119, 821739003).append(this.text)
                .append(this.timeCreated).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("entryId", this.id).append("text", this.text)
                .append("timeCreated", this.timeCreated).toString();
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
}