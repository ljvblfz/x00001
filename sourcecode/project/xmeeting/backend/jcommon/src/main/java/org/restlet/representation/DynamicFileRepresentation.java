package org.restlet.representation;
import java.io.IOException;
import java.io.OutputStream;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;

public class DynamicFileRepresentation extends OutputRepresentation {
	private byte[] fileData;

	public DynamicFileRepresentation(MediaType mediaType, long expectedSize,
			byte[] fileData) {
		super(mediaType, expectedSize);
		this.fileData = fileData;
	}

	@Override
	public void write(OutputStream outputStream) throws IOException {
		outputStream.write(fileData);
	}
}