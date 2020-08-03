package cz.metacentrum.perun.audit.events.GroupManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.GroupEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.core.api.Group;
import cz.metacentrum.perun.core.api.Member;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
public class MemberExpiredInGroup extends AuditEvent implements MemberEvent, GroupEvent {

	private Member member;
	private Group group;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public MemberExpiredInGroup() {
	}

	public MemberExpiredInGroup(Member member, Group group) {
		this.member = member;
		this.group = group;
		this.message = formatMessage("%s in %s expired.", member, group);
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
}
