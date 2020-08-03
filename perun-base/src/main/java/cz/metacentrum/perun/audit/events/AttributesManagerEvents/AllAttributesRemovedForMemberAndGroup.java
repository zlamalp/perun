package cz.metacentrum.perun.audit.events.AttributesManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineIgnoreEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Member;

public class AllAttributesRemovedForMemberAndGroup extends AuditEvent implements EngineIgnoreEvent, MemberEvent, GroupEvent {

	private Member member;
	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public AllAttributesRemovedForMemberAndGroup() {
	}

	public AllAttributesRemovedForMemberAndGroup(Member member, Group group) {
		this.member = member;
		this.group = group;
		this.message = formatMessage("All attributes removed for %s and %s.", member, group);
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
