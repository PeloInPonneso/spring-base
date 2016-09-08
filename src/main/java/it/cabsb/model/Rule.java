package it.cabsb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="RULE")
public class Rule implements Serializable {

	private static final long serialVersionUID = -1808403270057620770L;
	
	@DocumentId
	@Id  
    @GeneratedValue
    @Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	@NotNull @NotEmpty @Size(max=255)
	private String name;
	
	@Lob
	@Column(name="CONTENT")
	private String content;
	
	@Transient
	private MultipartFile file;
	
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.TIME)
	private Date creationDate;
	
	@Column(name="LAST_MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifyDate;
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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
		Rule castOther = (Rule) obj;
		return new EqualsBuilder()
			.append(id, castOther.id)
			.append(name, castOther.name)
			.append(content, castOther.content)
			.append(creationDate, castOther.creationDate)
			.append(lastModifyDate, castOther.lastModifyDate)
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(id)
			.append(name)
			.append(content)
			.append(creationDate)
			.append(lastModifyDate)
			.toHashCode();
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append(id)
			.append(name)
			.append(content)
			.append(creationDate)
			.append(lastModifyDate)
			.toString();
	}
}
