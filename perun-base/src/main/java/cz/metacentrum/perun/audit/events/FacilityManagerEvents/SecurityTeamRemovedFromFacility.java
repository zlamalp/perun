package cz.metacentrum.perun.audit.events.FacilityManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.FacilityEvent;
import cz.metacentrum.perun.audit.events.SecurityTeamEvent;
import cz.metacentrum.perun.core.api.Facility;
import cz.metacentrum.perun.core.api.SecurityTeam;

public class SecurityTeamRemovedFromFacility extends AuditEvent implements SecurityTeamEvent, FacilityEvent {

	private SecurityTeam securityTeam;
	private Facility facility;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public SecurityTeamRemovedFromFacility() {
	}

	public SecurityTeamRemovedFromFacility(SecurityTeam securityTeam, Facility facility) {
		this.securityTeam = securityTeam;
		this.facility = facility;
		this.message = formatMessage("%s was removed from %s.", securityTeam, facility);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public SecurityTeam getSecurityTeam() {
		return securityTeam;
	}

	@Override
	public Facility getFacility() {
		return facility;
	}

	@Override
	public String toString() {
		return message;
	}
}
