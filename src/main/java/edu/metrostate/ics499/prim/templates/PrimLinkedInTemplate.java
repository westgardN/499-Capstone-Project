package edu.metrostate.ics499.prim.templates;

import edu.metrostate.ics499.prim.model.LinkedInCompaniesList;
import edu.metrostate.ics499.prim.model.LinkedInCompany;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.linkedin.api.Company;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class PrimLinkedInTemplate extends LinkedInTemplate {
    public static final String API_URL_BASE = "https://api.linkedin.com";

    /**
     * Creates a new LinkedInTemplate given the minimal amount of information needed to sign requests with OAuth 1 credentials.
     *
     * @param accessToken an access token acquired through OAuth authentication with LinkedIn
     */
    public PrimLinkedInTemplate(String accessToken) {
        super(accessToken);
    }

    public List<LinkedInCompany> getCompaniesMemberIsAdministratorOf() {
        String url = API_URL_BASE + "/v1/companies?format=json&is-company-admin=true";
        ResponseEntity<LinkedInCompaniesList> responseEntity = this.getRestTemplate().getForEntity(url, LinkedInCompaniesList.class);

        LinkedInCompany companies[] = null;

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            LinkedInCompaniesList linkedInCompaniesList = responseEntity.getBody();

            companies = linkedInCompaniesList.getValues();

            if (companies == null) {
                companies = new LinkedInCompany[0];
            }
        }

        return asList(companies);
    }
}
