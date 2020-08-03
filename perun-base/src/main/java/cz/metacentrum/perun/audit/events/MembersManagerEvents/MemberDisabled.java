package cz.metacentrum.perun.audit.events.MembersManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.core.api.Member;

public class MemberDisabled extends AuditEvent implements MemberEvent {

	private Member member;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public MemberDisabled() {
	}

	public MemberDisabled(Member member) {
		this.member = member;
		this.message = formatMessage("%s disabled.", member);
	}

	@Override
	public String getMessage() {
		return message;
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
