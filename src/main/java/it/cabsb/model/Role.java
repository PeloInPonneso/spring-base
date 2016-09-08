package it.cabsb.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Indexed;
import org.springframework.security.core.GrantedAuthority;

@Indexed
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="ROLE")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 3289972237554527160L;

	@Id  
    @GeneratedValue  
    @Column(name="ID")
    private Long id;
	
	@Column(name="AUTHORITY")
	private String authority;
	
	@XmlTransient
	@JsonIgnore
	@ManyToMany(mappedBy="authorities")
	@Fetch(FetchMode.SELECT)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<User> users = new LinkedHashSet<User>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

    @Override
    public boolean equals(final Object obj) {
    	if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role castOther = (Role) obj;
		return new EqualsBuilder()
			.append(id, castOther.id)
			.append(authority, castOther.authority)
			.isEquals();
    }
    
    @Override
    public int hashCode() {
    	return new HashCodeBuilder()
    		.append(id)
    		.append(authority)
    		.toHashCode();
    }
    
    @Override
    public String toString() {
    	return new ToStringBuilder(this)
    	.append(id)
		.append(authority)
		.toString();
    }
}
