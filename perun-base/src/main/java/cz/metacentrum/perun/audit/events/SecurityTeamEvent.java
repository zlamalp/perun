package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.SecurityTeam;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides SecurityTeam object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface SecurityTeamEvent {

	SecurityTeam getSecurityTeam();

}
