package com.brand.sniffy.android.model;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Producer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5536747258299170489L;

	private static final String ID_FIELD = "id";

	private static final String NAME_FIELD = "name";

	private static final String PHONE_FIELD = "phone";

	private static final String FAX_FIELD = "fax";

	private static final String EMAIL_FIELD = "email";

	private static final String COUNTRY_ID_FIELD = "country_id";

	private static final String CODE_FIELD = "code";

	private static final String CONTACT_PERSON_FIELD = "contactPerson";

	public Producer(){
		
	}
	
	public Producer(JSONObject json) throws JSONException {
		id = json.getInt(ID_FIELD);
		name = json.getString(NAME_FIELD);
		phone = json.getString(PHONE_FIELD);
		fax = json.getString(FAX_FIELD);
		email = json.getString(EMAIL_FIELD);
		country = new Country(json.getInt(COUNTRY_ID_FIELD));
		code = json.getString(CODE_FIELD);
		contactPerson = json.getString(CONTACT_PERSON_FIELD);
	}

	@DatabaseField(id = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField
	private String phone;

	@DatabaseField
	private String fax;

	@DatabaseField
	private String email;

	@DatabaseField(foreign = true, foreignAutoCreate = false, foreignAutoRefresh = true)
	private Country country;

	@DatabaseField
	private String code;

	@DatabaseField
	private String contactPerson;
}
