package com.axiomatics.spring.boot.pdp.request;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "Attribute" })
public class AccessSubject {

	@JsonProperty("Attribute")
	private List<Attribute> attribute = new ArrayList<Attribute>();

	@JsonProperty("Attribute")
	public List<Attribute> getAttribute() {
		return attribute;
	}

	@JsonProperty("Attribute")
	public void setAttribute(List<Attribute> attribute) {
		this.attribute = attribute;
	}

	public AccessSubject withAttributes(List<Attribute> attribute) {
		this.attribute = attribute;
		return this;
	}

	public AccessSubject withAttributes(Attribute attribute) {
		this.attribute.add(attribute);
		return this;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(attribute).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof AccessSubject) == false) {
			return false;
		}
		AccessSubject rhs = ((AccessSubject) other);
		return new EqualsBuilder().append(attribute, rhs.attribute).isEquals();
	}

}