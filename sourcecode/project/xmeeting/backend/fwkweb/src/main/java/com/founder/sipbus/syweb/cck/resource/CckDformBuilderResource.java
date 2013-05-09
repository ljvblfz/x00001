package com.founder.sipbus.syweb.cck.resource;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.po.SyCckType;
import com.founder.sipbus.syweb.cck.po.SyCckTypeField;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.cck.util.SyCckUtil;
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;

/**
 * 生成Dfrom 用的json数据
 * 
 * urls = "/cck/dform/buildform/{action}/{ccktypeguid}/sccGuid/{sccGuid}
 * @author zjl
 *
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cck/dform/buildform/{action}/{ccktypeguid}/sccGuid/{sccGuid}")
public class CckDformBuilderResource extends SyBaseResource {

	private String ccktypeguid = "";
	private String action = "";
	private SyCckService syCckService;
	private String sccGuid = "";
	private String DOMAIN="1";
	private SyWidgetReferenceService syWidgetReferenceService;
	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}

	private String ATTRIBUTE_CLASS = "clazz";
	private String ACTION_ADD = "add";
	private String ACTION_EDIT = "edit";
	private String ACTION_VIEW = "view";
	private String MAX_LENGTH = "maxlength";
	private String DEFAULT_MAX_LENGTH = "65";
	private JSONObject buttonContainer = buildButtonContainer();
	private JSONObject jsonObject ;
	private static String DEFAULT_ROWS="6";
	private static String DEFAULT_COLS="40";
	private static String DEFAULT_RICH_ROWS="25";
	 
	
	@PostConstruct
	public void init() {
		jsonObject = new JSONObject();
		jsonObject.put("action", "index.html");
		jsonObject.put("onSubmit",
				"return validateCallback(this, dialogAjaxDone);");
		jsonObject.put("method", "post");
		jsonObject.put(ATTRIBUTE_CLASS, "required-validate");

		// 按钮
		JSONArray fieldsArray = new JSONArray();

		fieldsArray.add(buildPageFormContentJSON());
		fieldsArray.add(buttonContainer);
		fieldsArray.addAll(buildPageFormContentJSONArray());
		jsonObject.put("html", fieldsArray);
	}

	@Override
	protected void doInit() throws ResourceException {
		ccktypeguid = getAttribute("ccktypeguid");
		sccGuid = getAttribute("sccGuid");
		action = getAttribute("action");
	}

	private static JSONArray buildPageFormContentJSONArray() {
		JSONArray fieldsArray = new JSONArray();
		JSONObject input = new JSONObject();
		input.put("type", "hidden");
		input.put("name", "callBackMethod");
		input.put("value", "cckcontentSearch");
		fieldsArray.add(input);
		input = new JSONObject();
		input.put("type", "hidden");
		input.put("name", "callbackType");
		input.put("value", "closeCurrent");
		fieldsArray.add(input);
		input = new JSONObject();
		input.put("type", "hidden");
		input.put("name", "navTabId");
		input.put("value", "cckcontentnavTab");
		fieldsArray.add(input);

		return fieldsArray;

	}

	/**
	 *action： add|edit|view 
	 *ccktypeguid 
	 *sccGuid
	 *  @param entity
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-27 下午6:08:24 
	 */
	@Get
	public Representation get(Representation entity) {
		JSONObject json = (JSONObject) StdMemCacheUtil.getMemCachedClient()
				.get(SyCckService.KEY_GEN_FORMS + ccktypeguid);
		if (null == json) {
			JSONObject j = new JSONObject();
			j.accumulateAll(jsonObject);

			JSONArray s = (JSONArray) j.get("html");
			JSONObject t = (JSONObject) s.get(0);
			List<SyCckTypeField> list = syCckService.getFields(ccktypeguid);
			
			if (list == null) {
				setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
			SyCckType type = syCckService.getSyCckTypeDao().findById(ccktypeguid);
			if (type == null) {
				setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
 
			JSONArray fa = buildFieldsJSON(list,type);
			fa.add(createSctGuid(ccktypeguid));
			// if (StringUtils.isNotBlank(sccGuid)) {
			fa.add(createSccGuid(sccGuid));
			fa.add(createMasterId(sccGuid));
			fa.add(createParentId(sccGuid));
			// }
			t.put("layouth", "48");
			t.put("html", fa);

			StdMemCacheUtil.getMemCachedClient().set(
					SyCckService.KEY_GEN_FORMS + ccktypeguid, j);
			if (ACTION_ADD.equals(action)) {
				j.put("action",
						"javascript:CPATH.domain_1 +'/rs/cck/dform/formaction/add'");
			} else if (ACTION_EDIT.equals(action)) {
				j.put("action",
						"javascript:CPATH.domain_1 +'/rs/cck/dform/formaction/edit'");
			}
			 else if (ACTION_VIEW.equals(action)) {
					j.put("action",
							"javascript:return false;'");
				}
			return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(j));
		} else {
			if (ACTION_ADD.equals(action)) {
				json.put("action",
						"javascript:CPATH.domain_1 +'/rs/cck/dform/formaction/add'");
			} else if (ACTION_EDIT.equals(action)) {
				json.put("action",
						"javascript:CPATH.domain_1 +'/rs/cck/dform/formaction/edit'");
			}
			 else if (ACTION_VIEW.equals(action)) {
					json.put("action",
							"javascript:return false;'");
					JSONArray subjsons = (JSONArray) json.get("html");
					JSONObject formbar = subjsons.getJSONObject(1);
				JSONObject buttons = (JSONObject) formbar.get("html");
				JSONObject buttonClose=((JSONArray) buttons.get("html")).getJSONObject(1);
				buttons.put("html", buttonClose);
				json.remove(ATTRIBUTE_CLASS);
				}
			
			return getJsonGzipRepresentation(JsonUtils
					.genSuccessReturnJson(json));
		}

	}

	private JSONObject createSctGuid(String ccktypeguid) {
		JSONObject sctGuid = new JSONObject();
		sctGuid.put("type", "hidden");
		sctGuid.put("name", "SCT_GUID");
		// sctGuid.put("value", ccktypeguid);
		return sctGuid;
	}
	private JSONObject createMasterId(String ccktypeguid) {
		JSONObject sctGuid = new JSONObject();
		sctGuid.put("type", "hidden");
		sctGuid.put("name", "masterId");
		// sctGuid.put("value", ccktypeguid);
		return sctGuid;
	}
	private JSONObject createParentId(String ccktypeguid) {
		JSONObject sctGuid = new JSONObject();
		sctGuid.put("type", "hidden");
		sctGuid.put("name", "parentId");
		// sctGuid.put("value", ccktypeguid);
		return sctGuid;
	}
	private JSONObject createSccGuid(String sccGuid) {
		JSONObject sctGuid = new JSONObject();
		sctGuid.put("type", "hidden");
		sctGuid.put("name", "SCC_GUID");
		// sctGuid.put("value", sccGuid);
		return sctGuid;
	}

	private  JSONObject buildPageFormContentJSON() {
		JSONObject pageFormContent = new JSONObject();
		pageFormContent.put("type", "div");
		pageFormContent.put(ATTRIBUTE_CLASS, "pageFormContent");
		pageFormContent.put("layouth", "48");

		return pageFormContent;
	}

	private JSONArray buildFieldsJSON(List<SyCckTypeField> list, SyCckType type) {
		JSONArray fieldsArray = new JSONArray();
		//
		// JSONObject formTitle=new JSONObject();
		// formTitle.put("type", "p");
		// formTitle.put("style", "font-size:25px;font-weight:bold;");
		// formTitle.put("html", "Form Title/时刻表类别");
		// fieldsArray.add(formTitle);
		//

		int i = 0;
		for (SyCckTypeField f : list) {
			if ("1".equals(f.getFieldIsformdisplay())) {
				if (SyCckUtil.TYPE_REFERENCE.equals(f.getFieldType())) {//获取Referencetype
					String code=f.getFieldTypeReference();
					SyWidgetReference bean = syWidgetReferenceService.getBean(code);
					if (bean.getWidgetType()!=null) {
						f.setFieldTypeReferenceType( bean.getWidgetType().intValue() );  
					}
					if (bean.getAutocompleteConfig()!=null) {
						f.setFieldAutocompleteType(bean.getAutocompleteConfig().intValue());
					}
					
				}
				JSONObject fieldContainer = buildFieldContainer(++i, f,type);
				fieldsArray.add(fieldContainer);
			} else {
				// JSONObject field= buildField(++i,f);
				// fieldsArray.add(field);
			}

		}

		return fieldsArray;
	}

	private  JSONObject buildButtonContainer() {
		JSONObject buttonContainer = new JSONObject();
		buttonContainer.put("type", "div");
		buttonContainer.put(ATTRIBUTE_CLASS, "formBar");
		JSONObject ulContainer = new JSONObject();
		ulContainer.put("type", "ul");
		//
		JSONObject saveButton = buildSaveButton();
		JSONObject closeButton = buildCloseButton();
		//
		JSONArray buttonArray = new JSONArray();
		buttonArray.add(saveButton);
		buttonArray.add(closeButton);
		//
		ulContainer.put("html", buttonArray);
		buttonContainer.put("html", ulContainer);
		return buttonContainer;
	}

	private  void addClass(JSONObject jsonObject, String classString) {
		if (StringUtils.isNotBlank((String) jsonObject.get(ATTRIBUTE_CLASS))) {
			jsonObject.put(ATTRIBUTE_CLASS, jsonObject.get(ATTRIBUTE_CLASS)
					+ " " + classString);
		} else {
			jsonObject.put(ATTRIBUTE_CLASS, classString);
		}

	}

	private  JSONObject buildCloseButton() {

		JSONObject liContainer = new JSONObject();
		liContainer.put("type", "li");
		JSONObject lidivContainer = new JSONObject();
		lidivContainer.put("type", "div");
		lidivContainer.put(ATTRIBUTE_CLASS, "button");
		//

		JSONObject lidivdivContainer = new JSONObject();
		lidivdivContainer.put("type", "div");
		lidivdivContainer.put(ATTRIBUTE_CLASS, "buttonContent");

		JSONObject lidivdivcloseContainer = new JSONObject();
		lidivdivcloseContainer.put("type", "button");
		lidivdivcloseContainer.put(ATTRIBUTE_CLASS, "close");
		lidivdivcloseContainer.put("html", "关闭");
		//

		lidivdivContainer.put("html", lidivdivcloseContainer);
		//
		lidivContainer.put("html", lidivdivContainer);
		//
		liContainer.put("html", lidivContainer);
		return liContainer;
	}

	private  JSONObject buildSaveButton() {
		JSONObject liContainer = new JSONObject();
		liContainer.put("type", "li");
		JSONObject lidivContainer = new JSONObject();
		lidivContainer.put("type", "div");
		lidivContainer.put(ATTRIBUTE_CLASS, "buttonActive");
		//

		JSONObject lidivdivContainer = new JSONObject();
		lidivdivContainer.put("type", "div");
		lidivdivContainer.put(ATTRIBUTE_CLASS, "buttonContent");

		JSONObject lidivdivsubmitContainer = new JSONObject();
		lidivdivsubmitContainer.put("type", "button");
		lidivdivsubmitContainer.put("html", "保存");

		//

		lidivdivContainer.put("html", lidivdivsubmitContainer);
		//
		lidivContainer.put("html", lidivdivContainer);
		liContainer.put("html", lidivContainer);
		return liContainer;
	}

	// private JSONObject buildButton() {
	// JSONObject buttonSubmit=new JSONObject();
	// buttonSubmit.put("type", "submit");
	// buttonSubmit.put("value", "保存");
	// return buttonSubmit;
	// }

	private JSONObject buildFieldContainer(int index, SyCckTypeField f, SyCckType type) {
		JSONObject fieldDataContainer = new JSONObject();
		fieldDataContainer.put("type", "p");
		fieldDataContainer.accumulate("html", buildField(index, f ,type));
	 
		if (SyCckUtil.TYPE_TEXTAREA.equals(f.getFieldType())) {
			addClass(fieldDataContainer, "area-p") ;
		}
		if ((f.getFieldColumn().equals(type.getNameColumn())|| "1".equals(f.getFieldIsrequired()))&&(SyCckUtil.TYPE_CODE.equals(f.getFieldType())||SyCckUtil.TYPE_REFERENCE.equals(f.getFieldType()))) {
			if (f.getFieldTypeReferenceType()!=1) {
				fieldDataContainer.accumulate("html", buildRequiredFont(index));
			}else {
				JSONObject fieldDataHidden = new JSONObject();
			 
			 
				fieldDataHidden.put("name", f.getFieldColumn().toUpperCase());
				fieldDataHidden.put("type", "hidden");
				fieldDataContainer.accumulate("html", fieldDataHidden);
			}
			
		}
		if ( SyCckUtil.TYPE_REFERENCE.equals(f.getFieldType())) {
			if (f.getFieldTypeReferenceType()==1) {
				JSONObject fieldDataHidden = new JSONObject();
				 
				 
				fieldDataHidden.put("name", f.getFieldColumn().toUpperCase());
				fieldDataHidden.put("type", "hidden");
				fieldDataContainer.accumulate("html", fieldDataHidden);
			} 
			
		}
		return fieldDataContainer;
	}

	/**
	 * 加必须的那个星号
	 **/
	private JSONObject buildRequiredFont(int index) {

		JSONObject font = new JSONObject();
		font.put("type", "font");
		font.put("html", "*");
		font.put("ATTRIBUTE_CLASS", "requiredfont");
		font.put("color", "red");
		return font;
	}

	/**
	 *	生成Field 
	 *  @param index
	 *  @param f
	 *  @param type
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:42:36 
	 */
	private JSONObject buildField(int index, SyCckTypeField f, SyCckType type ) {
		JSONObject fieldData = new JSONObject();
		int typeint = Integer.parseInt(f.getFieldType());
		if ("1".equals(f.getFieldIsformdisplay())) {
			fieldData.put("name", f.getFieldColumn().toUpperCase());
			// fieldData.put("id",
			// "txt-"+f.getFieldColumn().toUpperCase()+index);
			fieldData.put("caption", f.getFieldLabel());
			// input 类型
			if (SyCckUtil.TYPE_INT_CODE == typeint) {// 码表
				fieldData.put("type", "select");
				addClass(fieldData, "combox");
				addClass(fieldData, "cckcombox");
				// if (ACTION_EDIT.equals(action)) {
				addClass(fieldData, "sync"); // 全部combox 加上sync ，新增和修改是同一个页面
				// }
				fieldData.put("valueCode", "valueCode");
				fieldData.put("valueDesc", "valueDesc");
				fieldData.put("codeType", f.getFieldTypeReference());
				// fieldData.put("value", "");
				fieldData.put("html", buildOption("", "请选择"));
			} else if (SyCckUtil.TYPE_INT_REFERENCE == typeint) {
					if (f.getFieldTypeReferenceType()==0) {
						fieldData.put("type", "select");
						addClass(fieldData, "comboxtree");
						addClass(fieldData, "cckcombox");
						addClass(fieldData, "sync"); // 全部combox 加上sync ，新增和修改是同一个页面
						fieldData.put("valueCode", "ID");
						fieldData.put("valueDesc", "NAME");
						fieldData.put("valuePid", "PID");
						fieldData.put("domain", DOMAIN);
						fieldData.put("refcode", f.getFieldTypeReference());
						fieldData.put("html", buildOption("", "请选择"));
					} else if (f.getFieldTypeReferenceType()==1) {  //autocomplete
						long time=new Date().getTime();
						fieldData.put("name", f.getFieldColumn().toUpperCase()+"_SHOW");
						fieldData.put("id", f.getFieldColumn().toUpperCase()+"_SHOW_"+time);
						fieldData.put("type", "text");
						addClass(fieldData, "autocompleteForCCK");
						fieldData.put("datavalue", "ID");
						fieldData.put("itemvalue", "NAME");
						fieldData.put("autocompleteType", f.getFieldAutocompleteType());
						fieldData.put("domain", DOMAIN);
						fieldData.put("refcode", f.getFieldTypeReference());
					} else if (f.getFieldTypeReferenceType()==2) {  // comboxcascade
						fieldData.put("name", f.getFieldColumn().toUpperCase() );
						fieldData.put("type", "hidden");
						addClass(fieldData, "comboxcascade");
						addClass(fieldData, "cckcomboxcascade");
						addClass(fieldData, "syncComboxCascade");
						fieldData.put("valueCode", "ID");
						fieldData.put("valueDesc", "NAME");
						fieldData.put("domain", DOMAIN);
						fieldData.put("refcode", f.getFieldTypeReference());
					} else{
						fieldData.put("type", "text");// 其他为input
						fieldData.put("size", "30");
						fieldData.put("name", f.getFieldColumn().toUpperCase());
					}
			} else {
//				if (SyCckUtil.TYPE_INT_RICHTEXT==typeint&&index!=-1) {
//					fieldData.put("type", "div");// 其他为textarea
//					addClass(fieldData, "unit");
//					fieldData.put("html", buildField(-1, f, type));
//					
//				}else
	 
				if (SyCckUtil.TYPE_INT_TEXTAREA==typeint||SyCckUtil.TYPE_INT_RICHTEXT==typeint) {
					fieldData.put("type", "textarea");// 其他为textarea
					String rows=DEFAULT_ROWS;
					if (SyCckUtil.TYPE_INT_RICHTEXT==typeint) {
						rows=DEFAULT_RICH_ROWS;
					}
					String cols=DEFAULT_COLS;
					if (StringUtils.isNotBlank(f.getAreaRows())) {
						rows=f.getAreaRows();
					}
					if (StringUtils.isNotBlank(f.getAreaCols())) {
						cols=f.getAreaCols();
					}
					fieldData.put("rows", rows);
					fieldData.put("cols", cols);
					if (SyCckUtil.TYPE_INT_RICHTEXT==typeint) {
						addClass(fieldData, "editor");
					}
				}else {
					fieldData.put("type", "text");// 其他为input
					fieldData.put("size", "30");
					// 根据类型 加class
					if (SyCckUtil.TYPE_INT_DATE == typeint) {// date
						addClass(fieldData, "date");
					} else if (SyCckUtil.TYPE_INT_NUMBER == typeint) {// number
						addClass(fieldData, "number");
					} else if (SyCckUtil.TYPE_INT_DIGITS == typeint) {// digits
						addClass(fieldData, "digits");
					}

				}
				
			}
		 
			if (SyCckUtil.TYPE_INT_PK == typeint
					|| SyCckUtil.TYPE_INT_SCT_GUID == typeint
					|| SyCckUtil.TYPE_INT_GUID == typeint
					||SyCckUtil.TYPE_INT_MASTERID == typeint
					||SyCckUtil.TYPE_INT_UPDATE_BY == typeint
					||SyCckUtil.TYPE_INT_UPDATE_DT == typeint
					||SyCckUtil.TYPE_INT_CREATE_BY == typeint
					||SyCckUtil.TYPE_INT_CREATE_DT == typeint
					||SyCckUtil.TYPE_INT_DEL_FLAG == typeint
					||SyCckUtil.TYPE_INT_PARENT_ID == typeint) {// readonly &&
				fieldData.put("readOnly", "readOnly");
				if (SyCckUtil.TYPE_INT_MASTERID == typeint) {// readonly &&
					addClass(fieldData, "MasterId");
				}else if (SyCckUtil.TYPE_INT_PARENT_ID == typeint) {
					addClass(fieldData, "ParentId");
				}
			} else {
				// 加maxlength  
				//主键和sctGUid,guid不加maxlength
				if (StringUtils.isBlank(f.getFieldMaxlength())) {
					fieldData.put(MAX_LENGTH, DEFAULT_MAX_LENGTH);
				} else {
					if (SyCckUtil.TYPE_INT_DATE == typeint) {// date
						fieldData.put(MAX_LENGTH, DEFAULT_MAX_LENGTH);
					} else {
						fieldData.put(MAX_LENGTH, f.getFieldMaxlength());
					}

				}
				if (f.getFieldColumn().equals(type.getNameColumn())||"1".equals(f.getFieldIsrequired())) {// 必须 ！！主键
															// 和sctGUid,guid不加必须
					addClass(fieldData, "required");

				} 
				
			if ("1".equals(f.getFieldIsunique())) {
				fieldData.put("validateRemote", "/rs/cckUniqueValidate/");
				fieldData.put("domain", DOMAIN);
				fieldData.put("formData", "formData");
			}
				
			}

		} else {
			fieldData.put("name", f.getFieldColumn().toUpperCase());

			fieldData.put("type", "hidden");
		}

		return fieldData;
	}

	/**
	 *	生成option 的json
	 *  @param value
	 *  @param text
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:43:32 
	 */
	private JSONObject buildOption(String value, String text) {
		JSONObject option = new JSONObject();
		option.put("type", "option");
		option.put("value", value);
		option.put("html", text);
		return option;
	}

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}

}
