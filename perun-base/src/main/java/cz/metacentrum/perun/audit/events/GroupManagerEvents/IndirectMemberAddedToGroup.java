package cz.metacentrum.perun.audit.events.GroupManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Member;

public class IndirectMemberAddedToGroup extends AuditEvent implements MemberEvent, GroupEvent {

	private Group group;
	private Member member;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public IndirectMemberAddedToGroup() {
	}

	public IndirectMemberAddedToGroup(Member member, Group group) {
		this.group = group;
		this.member = member;
		this.message = formatMessage("%s added to %s.", member, group);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Group getGroup() {
		return group;
	}

	@Override
	public Member getMember() {
		return member;
	}

	@Override
	public String toString() {
		return message;
	}
}
