package cz.metacentrum.perun.audit.events.MembersManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.MemberEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.Member;
import cz.metacentrum.perun.core.api.User;

public class SponsorshipEstablished extends AuditEvent implements MemberEvent, UserEvent {

	private Member sponsoredMember;
	private User sponsor;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public SponsorshipEstablished() {
	}

	public SponsorshipEstablished(Member sponsoredMember, User sponsor) {
		this.sponsoredMember = sponsoredMember;
		this.sponsor = sponsor;
		this.message = formatMessage("Sponsorship of %s by %s established.", sponsoredMember, sponsor);
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
	public User getUser() {
		return sponsor;
	}

	@Override
	public String toString() {
		return message;
	}
}
