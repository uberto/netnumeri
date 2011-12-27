package com.netnumeri.shared.pojoc;

import com.netnumeri.shared.enumer.CountryEnum;
import com.netnumeri.shared.pojoc.field.BlobField;
import com.netnumeri.shared.pojoc.field.EnumField;
import com.netnumeri.shared.pojoc.field.StringField;

public class BusinessAccount extends BAPojo {
    private static final int DEFAULT_CURRENCIES_LIST_SIZE = 10;

    private static final long serialVersionUID = -1558288185522119402L;

    // company info
    public StringField fVat = stringField("vat", 20);
    public BlobField fCompanyLogo = blobField("companyLogo");
    public BlobField fCompanyLogoThumb = blobField("companyLogoThumb");

    // contact info
    public StringField fEmail = stringField("email", 120);
    public StringField fWeb = stringField("web", 120);
    public StringField fPhone = stringField("phone", 15);
    public StringField fFax = stringField("fax", 15);

    // address info
    public StringField fAddress = stringField("address", 30);
    public StringField fCity = stringField("city", 30);
    public StringField fRegion = stringField("region", 30);
    public StringField fZipCode = stringField("zipCode", 30);
    public EnumField<CountryEnum> fCountry = enumField("country", CountryEnum.GB);

}
