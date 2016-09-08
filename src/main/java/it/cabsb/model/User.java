package it.cabsb.model;

import it.cabsb.constraint.FieldMatchConstraint;
import it.cabsb.drools.constraint.BusinessRulesConstraint;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.userdetails.UserDetails;

@Indexed
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="USER")
@FieldMatchConstraint.List({
    @FieldMatchConstraint(first="formPasswordOne", second="formPasswordTwo", message="{it.cabsb.constraint.PasswordsEqual.message}")
})
@BusinessRulesConstraint
public class User implements UserDetails {
	
	private static final long serialVersionUID = -8454035919996401296L;

	@DocumentId
	@Id
    @GeneratedValue
    @Column(name="ID")
	private Long id;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name="USERNAME")
	@NotNull @NotEmpty @Size(min=5, max=10)
	private String username;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Transient
	@Size(min=6, max=10)
	private String formPasswordOne;
	
	@Transient
	@Size(min=6, max=10)
	private String formPasswordTwo;
	
	@Column(name="COUNTRY")
	private String country;
	
	@Column(name="EMAIL")
	@Email
	private String email;
	
	@Column(name="BIRTH_DAY")
	@Temporal(TemporalType.DATE)
	private Date birthDay;
	
	@ManyToMany 
	@Fetch(FetchMode.SELECT)
	@LazyCollection(LazyCollectionOption.TRUE)
	@Cascade(value={CascadeType.SAVE_UPDATE})
    @JoinTable(name="USER_ROLE",  
        joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName="ID")},  
        inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName="ID")}
    )
	private Set<Role> authorities = new LinkedHashSet<Role>(0);
	
	@Column(name="ACCOUNT_NON_EXPIRED")
	private boolean accountNonExpired;
	
	@Column(name="ACCOUNT_NON_LOCKED")
	private boolean accountNonLocked;
	
	@Column(name="CREDENTIALS_NON_EXPIRED")
	private boolean credentialsNonExpired;
	
	@Column(name="ENABLED")
	private boolean enabled;
	
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name="LAST_MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifyDate;
	
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFormPasswordOne() {
		return formPasswordOne;
	}

	public void setFormPasswordOne(String formPasswordOne) {
		this.formPasswordOne = formPasswordOne;
	}

	public String getFormPasswordTwo() {
		return formPasswordTwo;
	}

	public void setFormPasswordTwo(String formPasswordTwo) {
		this.formPasswordTwo = formPasswordTwo;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Set<Role> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User castOther = (User) obj;
		return new EqualsBuilder()
			.append(id, castOther.id)
			.append(username, castOther.username)
			.append(password, castOther.password)
			.append(country, castOther.country)
			.append(email, castOther.email)
			.append(birthDay, castOther.birthDay)
			.append(accountNonExpired, castOther.accountNonExpired)
			.append(accountNonLocked, castOther.accountNonLocked)
			.append(credentialsNonExpired, castOther.credentialsNonExpired)
			.append(enabled, castOther.enabled)
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(id)
			.append(username)
			.append(password)
			.append(country)
			.append(email)
			.append(birthDay)
			.append(accountNonExpired)
			.append(accountNonLocked)
			.append(credentialsNonExpired)
			.append(enabled)
			.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append(id)
			.append(username)
			.append(password)
			.append(country)
			.append(email)
			.append(birthDay)
			.append(accountNonExpired)
			.append(accountNonLocked)
			.append(credentialsNonExpired)
			.append(enabled)
			.toString();
	}
}
