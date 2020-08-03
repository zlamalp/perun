package cz.metacentrum.perun.audit.events;

import cz.metacentrum.perun.core.api.Service;

import java.util.List;

/**
 * Marker interface for AuditEvent classes.
 *
 * Classes with this interface provides Service object(s) from the message.
 *
 * @author Pavel Zlámal <zlamal@cesnet.cz>
 */
public interface ServicesEvent {

	List<Service> getServices();

}
