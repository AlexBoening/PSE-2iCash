package server;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private static final Class<?>[] CLASSES = new Class[] { /*
															 * Klassen des
															 * Datenmodells
															 */};
	private final JAXBContext context;

	public JAXBContextResolver() throws Exception {
		this.context = new JSONJAXBContext(JSONConfiguration.natural().humanReadableFormatting(true).build(), CLASSES);
	}

	@Override
	public JAXBContext getContext(Class<?> objectType) {
		return context;
	}

}
