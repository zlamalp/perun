package cz.metacentrum.perun.audit.events.MembersManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.core.api.Member;

public class SponsoredMemberUnset extends AuditEvent implements MemberEvent {

	private Member sponsoredMember;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public SponsoredMemberUnset() {
	}

	public SponsoredMemberUnset(Member sponsoredMember) {
		this.sponsoredMember = sponsoredMember;
		this.message = formatMessage("%s is not sponsored anymore.", sponsoredMember);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Member getMember() {
		return sponsoredMember;
	}

	@Override
	public String toString() {
		return message;
	}
}
