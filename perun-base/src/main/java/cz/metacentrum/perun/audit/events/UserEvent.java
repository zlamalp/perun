package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.User;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides User object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface UserEvent {

	User getUser();

}
