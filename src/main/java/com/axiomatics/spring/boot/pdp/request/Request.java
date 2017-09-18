package com.axiomatics.spring.boot.pdp.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "AccessSubject" })
public class Request {

	@JsonProperty("AccessSubject")
	private AccessSubject accessSubject;

	@JsonProperty("AccessSubject")
	public AccessSubject getAccessSubject() {
		return accessSubject;
	}

	@JsonProperty("AccessSubject")
	public void setAccessSubject(AccessSubject accessSubject) {
		this.accessSubject = accessSubject;
	}

	public Request withAccessSubject(AccessSubject accessSubject) {
		this.accessSubject = accessSubject;
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(accessSubject).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Request) == false) {
			return false;
		}
		Request rhs = ((Request) other);
		return new EqualsBuilder().append(accessSubject, rhs.accessSubject).isEquals();
	}

}