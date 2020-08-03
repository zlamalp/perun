package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Service;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Service object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface ServiceEvent {

	Service getService();

}
