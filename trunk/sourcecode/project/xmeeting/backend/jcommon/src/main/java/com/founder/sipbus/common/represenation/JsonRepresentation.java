package com.founder.sipbus.common.represenation;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import net.sf.json.util.JSONTokener;

import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.WriterRepresentation;

public class JsonRepresentation extends WriterRepresentation
{

    public JsonRepresentation(JSONArray jsonArray)
    {
        super(MediaType.APPLICATION_JSON);
        init(jsonArray);
    }

    public JsonRepresentation(JSONObject jsonObject)
    {
        super(MediaType.APPLICATION_JSON);
        init(jsonObject);
    }

    public JsonRepresentation(JSONStringer jsonStringer)
    {
        super(MediaType.APPLICATION_JSON);
        init(jsonStringer);
    }

    public JsonRepresentation(JSONTokener jsonTokener)
    {
        super(MediaType.APPLICATION_JSON);
        init(jsonTokener);
    }

    public JsonRepresentation(Map map)
    {
        this(JSONObject.fromObject(map));//new JSONObject(map));
    }

    public JsonRepresentation(Object bean)
    {
        this(JSONObject.fromObject(bean));// new JSONObject(bean));
    }

    public JsonRepresentation(Representation jsonRepresentation)
        throws IOException
    {
        super(jsonRepresentation != null ? jsonRepresentation.getMediaType() : null);
        this.jsonRepresentation = jsonRepresentation;
    }

    public JsonRepresentation(String jsonString)
    {
        super(MediaType.APPLICATION_JSON);
        setCharacterSet(CharacterSet.UTF_8);
        jsonRepresentation = new StringRepresentation(jsonString);
    }

    public int getIndentingSize()
    {
        return indentingSize;
    }

    /**
     * @deprecated Method getIndentSize is deprecated
     */

    public int getIndentSize()
    {
        return getIndentingSize();
    }

    public JSONArray getJsonArray()
        throws JSONException
    {
        if(jsonValue != null)
            return (JSONArray)jsonValue;
        else
            return toJsonArray();
    }

    public JSONObject getJsonObject()
        throws JSONException
    {
        if(jsonValue != null)
            return (JSONObject)jsonValue;
        else
            return toJsonObject();
    }

    private String getJsonText()
        throws JSONException
    {
        String result = null;
        if(jsonValue != null)
        {
            if(jsonValue instanceof JSONArray)
            {
                JSONArray jsonArray = (JSONArray)jsonValue;
                if(isIndenting())
                    result = jsonArray.toString(getIndentingSize());
                else
                    result = jsonArray.toString();
            } else
            if(jsonValue instanceof JSONObject)
            {
                JSONObject jsonObject = (JSONObject)jsonValue;
                if(isIndenting())
                    result = jsonObject.toString(getIndentingSize());
                else
                    result = jsonObject.toString();
            } else
            if(jsonValue instanceof JSONStringer)
            {
                JSONStringer jsonStringer = (JSONStringer)jsonValue;
                result = jsonStringer.toString();
            } else
            if(jsonValue instanceof JSONTokener)
            {
                JSONTokener jsonTokener = (JSONTokener)jsonValue;
                result = jsonTokener.toString();
            }
        } else
        if(jsonRepresentation != null)
            try
            {
                result = jsonRepresentation.getText();
            }
            catch(IOException e)
            {
                throw new JSONException(e);
            }
        return result;
    }

    public JSONTokener getJsonTokener()
        throws JSONException
    {
        if(jsonValue != null)
            return (JSONTokener)jsonValue;
        else
            return toJsonTokener();
    }

    public long getSize()
    {
        if(jsonRepresentation != null)
            return jsonRepresentation.getSize();
        else
            return super.getSize();
    }

    private void init(Object jsonObject)
    {
        setCharacterSet(CharacterSet.UTF_8);
        jsonValue = jsonObject;
        indenting = false;
        indentingSize = 3;
    }

    /**
     * @deprecated Method isIndent is deprecated
     */

    public boolean isIndent()
    {
        return indenting;
    }

    public boolean isIndenting()
    {
        return isIndent();
    }

    /**
     * @deprecated Method setIndent is deprecated
     */

    public void setIndent(boolean indenting)
    {
        this.indenting = indenting;
    }

    public void setIndenting(boolean indenting)
    {
        setIndent(indenting);
    }

    public void setIndentingSize(int indentFactor)
    {
        indentingSize = indentFactor;
    }

    /**
     * @deprecated Method setIndentSize is deprecated
     */

    public void setIndentSize(int indentFactor)
    {
        setIndentingSize(indentFactor);
    }

    /**
     * @deprecated Method toJsonArray is deprecated
     */

    public JSONArray toJsonArray()
        throws JSONException
    {
        return JSONArray.fromObject(getJsonText());//new JSONArray(getJsonText());
    }

    /**
     * @deprecated Method toJsonObject is deprecated
     */

    public JSONObject toJsonObject()
        throws JSONException
    {
        return JSONObject.fromObject(getJsonText());//new JSONObject(getJsonText());
    }

    /**
     * @deprecated Method toJsonTokener is deprecated
     */

    public JSONTokener toJsonTokener()
        throws JSONException
    {
        return new JSONTokener(getJsonText());
    }

    public void write(Writer writer)
        throws IOException
    {
        try
        {
            writer.write(getJsonText());
        }
        catch(JSONException e)
        {
            IOException ioe = new IOException(e.getLocalizedMessage());
            ioe.initCause(e.getCause());
            throw ioe;
        }
    }

    private boolean indenting;
    private int indentingSize;
    private Object jsonValue;
    private Representation jsonRepresentation;
}
 