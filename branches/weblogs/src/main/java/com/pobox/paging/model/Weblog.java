package com.pobox.paging.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.appfuse.model.BaseObject;
import org.compass.annotations.Cascade;
import org.compass.annotations.EnableAll;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableAllMetaData;
import org.compass.annotations.SearchableBoostProperty;
import org.compass.annotations.SearchableCascading;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableConstant;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

@SuppressWarnings("unchecked")
@Entity
@Searchable(alias = "weblog")
@SearchableAllMetaData(enable = EnableAll.TRUE)
@SearchableConstant(name = "type", values = { "weblog" })
public class Weblog extends BaseObject {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -2988716755100043321L;
    private Long id;
    private String blogTitle;
    private Date dateCreated;
    private Date version;
    private List<Entry> entries;

    @OneToMany(mappedBy = "weblog")
    @Column(name = "entry_id")
    @SearchableReference
    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Id
    @Column(name = "weblog_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    public Long getId() {
        return id;
    }

    public void setId(Long weblogId) {
        this.id = weblogId;
    }

    // Boost - see http://www.kimchy.org/improved-boosting-with-all-property/
    @SearchableProperty(name = "blogTitle",boost = 5.0f)
    @SearchableMetaData(name = "title")
    @Column(name = "blog_title", nullable = false)
    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    @Version
    @SearchableProperty(name = "dateCreated")
    @SearchableMetaData(name = "dateCreated", format = "dd-MM-yyyy")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Weblog)) {
            return false;
        }
        Weblog rhs = (Weblog) object;
        return new EqualsBuilder().append(this.blogTitle, rhs.blogTitle)
                .append(this.dateCreated, rhs.dateCreated).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-1769938895, -2120812053).append(
                this.blogTitle).append(this.dateCreated).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("weblogId", this.id).append("blogTitle",
                        this.blogTitle).append("dateCreated", this.dateCreated)
                .toString();
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
