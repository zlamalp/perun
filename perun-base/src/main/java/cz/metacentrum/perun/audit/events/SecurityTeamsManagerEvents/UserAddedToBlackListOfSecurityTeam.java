package cz.metacentrum.perun.audit.events.SecurityTeamsManagerEvents;

import cz.metacentrum.perun.audit.events.AuditEvent;
import cz.metacentrum.perun.audit.events.EngineForceEvent;
import cz.metacentrum.perun.audit.events.SecurityTeamEvent;
import cz.metacentrum.perun.audit.events.UserEvent;
import cz.metacentrum.perun.core.api.SecurityTeam;
import cz.metacentrum.perun.core.api.User;

public class UserAddedToBlackListOfSecurityTeam extends AuditEvent implements EngineForceEvent, UserEvent, SecurityTeamEvent {

	private User user;
	private SecurityTeam securityTeam;
	private String description;
	private String message;

	@SuppressWarnings("unused") // used by jackson mapper
	public UserAddedToBlackListOfSecurityTeam() {
	}

	public UserAddedToBlackListOfSecurityTeam(User user, SecurityTeam securityTeam, String description) {
		this.user = user;
		this.securityTeam = securityTeam;
		this.description = description;
		this.message = formatMessage("%s add to blacklist of %s with description '%s'.", user, securityTeam, description);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public SecurityTeam getSecurityTeam() {
		return securityTeam;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return message;
	}
}
