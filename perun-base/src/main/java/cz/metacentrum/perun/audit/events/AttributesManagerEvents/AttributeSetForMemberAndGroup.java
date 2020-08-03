package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AttributeEvent;
import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.core.api.Attribute;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Member;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class AttributeSetForMemberAndGroup extends AuditEvent implements AttributeEvent, MemberEvent, GroupEvent {

	private Attribute attribute;
	private Member member;
	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AttributeSetForMemberAndGroup() {
	}

	public AttributeSetForMemberAndGroup(Attribute attribute, Member member, Group group) {
		this.attribute = attribute;
		this.member = member;
		this.group = group;
		this.message = formatMessage("%s set for %s and %s.", attribute, member, group);
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Member getMember() {
		return member;
	}

	@Override
	public Group getGroup() {
		return group;
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
