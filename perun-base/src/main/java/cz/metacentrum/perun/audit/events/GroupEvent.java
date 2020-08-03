package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Group;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Group object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface GroupEvent {

	Group getGroup();

}
