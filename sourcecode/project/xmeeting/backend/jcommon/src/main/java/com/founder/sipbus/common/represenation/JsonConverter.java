
package com.founder.sipbus.common.represenation;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

import org.restlet.data.MediaType;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.Resource;
import org.restlet.resource.UniformResource;

public class JsonConverter extends ConverterHelper
{

    public JsonConverter()
    {
    }

    public List getObjectClasses(Variant source)
    {
        List result = null;
        if(VARIANT_JSON.isCompatible(source))
        {
            result = addObjectClass(result, JSONArray.class);
            result = addObjectClass(result, JSONObject.class);
            result = addObjectClass(result, JSONTokener.class);
        }
        return result;
    }

//	public List getVariants(Class source)
//    {
//        List result = null;
//        if(JSONArray.class.isAssignableFrom(source))
//            result = addVariant(result, VARIANT_JSON);
//        else
//        if(JSONObject.class.isAssignableFrom(source))
//            result = addVariant(result, VARIANT_JSON);
//        else
//        if(JSONTokener.class.isAssignableFrom(source))
//            result = addVariant(result, VARIANT_JSON);
//        return result;
//    }

    public float score(Object source, Variant target, UniformResource resource)
    {
        float result = -1F;
        if((source instanceof JSONArray) || (source instanceof JSONObject) || (source instanceof JSONTokener))
            if(target == null)
                result = 0.5F;
            else
            if(MediaType.APPLICATION_JSON.isCompatible(target.getMediaType()))
                result = 1.0F;
            else
                result = 0.5F;
        return result;
    }

    public float score(Representation source, Class target, UniformResource resource)
    {
        float result = -1F;
        if(target != null)
            if(JsonRepresentation.class.isAssignableFrom(target))
                result = 1.0F;
            else
            if(JSONArray.class.isAssignableFrom(target))
            {
                if(MediaType.APPLICATION_JSON.isCompatible(source.getMediaType()))
                    result = 1.0F;
                else
                    result = 0.5F;
            } else
            if(JSONObject.class.isAssignableFrom(target))
            {
                if(MediaType.APPLICATION_JSON.isCompatible(source.getMediaType()))
                    result = 1.0F;
                else
                    result = 0.5F;
            } else
            if(JSONTokener.class.isAssignableFrom(target))
                if(MediaType.APPLICATION_JSON.isCompatible(source.getMediaType()))
                    result = 1.0F;
                else
                    result = 0.5F;
        return result;
    }

    public Object toObject(Representation source, Class target, UniformResource resource)
        throws IOException
    {
        Object result = null;
        if(JSONArray.class.isAssignableFrom(target))
            try
            {
                result = JSONArray.fromObject(source.getText());// new JSONArray(source.getText());
            }
            catch(JSONException e)
            {
                IOException ioe = new IOException("Unable to convert to JSON array");
                ioe.initCause(e);
            }
        else
        if(JSONObject.class.isAssignableFrom(target))
            try
            {
                result = JSONArray.fromObject(source.getText());//new JSONObject(source.getText());
            }
            catch(JSONException e)
            {
                IOException ioe = new IOException("Unable to convert to JSON object");
                ioe.initCause(e);
            }
        else
        if(JSONTokener.class.isAssignableFrom(target))
            result = new JSONTokener(source.getText());
        else
        if(JsonRepresentation.class.isAssignableFrom(target))
            result = new JsonRepresentation(source);
        return result;
    }

    public Representation toRepresentation(Object source, Variant target, UniformResource resource)
    {
        Representation result = null;
        if(source instanceof JSONArray)
            result = new StringRepresentation(((JSONArray)source).toString());
        else
        if(source instanceof JSONObject)
            result = new StringRepresentation(((JSONObject)source).toString());
        else
        if(source instanceof JSONTokener)
            result = new StringRepresentation(((JSONTokener)source).toString());
        return result;
    }

    public void updatePreferences(List preferences, Class entity)
    {
        if(JSONArray.class.isAssignableFrom(entity) || JSONObject.class.isAssignableFrom(entity) || JSONTokener.class.isAssignableFrom(entity))
            updatePreferences(preferences, MediaType.APPLICATION_JSON, 1.0F);
    }

    private static final VariantInfo VARIANT_JSON;

    static 
    {
        VARIANT_JSON = new VariantInfo(MediaType.APPLICATION_JSON);
    }

	@Override
	public List<VariantInfo> getVariants(Class<?> source) {
        List result = null;
        if(JSONArray.class.isAssignableFrom(source))
            result = addVariant(result, VARIANT_JSON);
        else
        if(JSONObject.class.isAssignableFrom(source))
            result = addVariant(result, VARIANT_JSON);
        else
        if(JSONTokener.class.isAssignableFrom(source))
            result = addVariant(result, VARIANT_JSON);
        return result;
	}

	@Override
	public float score(Object source, Variant target, Resource resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> float score(Representation source, Class<T> target,
			Resource resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T toObject(Representation source, Class<T> target,
			Resource resource) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Representation toRepresentation(Object source, Variant target,
			Resource resource) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}

