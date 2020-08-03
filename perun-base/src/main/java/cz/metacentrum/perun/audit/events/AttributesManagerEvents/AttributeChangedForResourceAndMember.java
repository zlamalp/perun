package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.audit.events.ResourceEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.Member;
import cz.metacentrum.perun.core.api.Resource;

public class AttributeChangedForResourceAndMember extends AuditEvent implements ResourceEvent, MemberEvent, AttributeEvent {

	private Attribute attribute;
	private Resource resource;
	private Member member;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeChangedForResourceAndMember() {
	}

	public AttributeChangedForResourceAndMember(Attribute attribute, Resource resource, Member member) {
		this.attribute = attribute;
		this.resource = resource;
		this.member = member;
		this.message = formatMessage("%s changed for %s and %s.", attribute, resource, member);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	@Override
	public Member getMember() {
		return member;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
}
