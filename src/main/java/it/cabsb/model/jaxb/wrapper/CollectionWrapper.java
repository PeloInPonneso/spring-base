package it.cabsb.model.jaxb.wrapper;

import it.cabsb.model.Role;
import it.cabsb.model.User;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({User.class, Role.class})
@XmlRootElement(name="list")
public class CollectionWrapper<T> {
	
	private Collection<T> items;
	
    public CollectionWrapper() {
		super();
	}

	public CollectionWrapper(Collection<T> items) {
        this.items = items;
    }
 
    @XmlAnyElement(lax=true)
    public Collection<T> getItems() {
        return items;
    }

}
