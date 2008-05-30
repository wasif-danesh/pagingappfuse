package com.pobox.paging.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
import org.compass.annotations.SearchableConstant;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;

@Entity
@Searchable(alias = "category")
@SearchableAllMetaData(enable = EnableAll.TRUE)
@SearchableConstant(name = "type", values = { "category" })
public class Category extends BaseObject {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -2872901296541482809L;
    private Long id;
    private String name;
    private String description;
    private Date version;

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SearchableId
    public Long getId() {
        return id;
    }

    public void setId(Long categoryId) {
        this.id = categoryId;
    }

    // Boost - see http://www.kimchy.org/improved-boosting-with-all-property/
    @SearchableProperty(name = "name",boost = 3.0f)
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String categoryName) {
        this.name = categoryName;
    }

    @SearchableProperty(name = "description",boost = 3.0f)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Category)) {
            return false;
        }
        Category rhs = (Category) object;
        return new EqualsBuilder().append(this.description, rhs.description)
                .append(this.name, rhs.name).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(565232597, 1589224137).append(
                this.description).append(this.name).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("categoryId", this.id)
                .append("name", this.name).append("description",
                        this.description).toString();
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
