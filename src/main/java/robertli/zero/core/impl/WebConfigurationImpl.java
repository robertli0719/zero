/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core.impl;

import robertli.zero.core.WebConfiguration;

public class WebConfigurationImpl implements WebConfiguration {

    private String domain;
    private String googleSigninClientId;

    @Override
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String getGoogleSigninClientId() {
        return googleSigninClientId;
    }

    public void setGoogleSigninClientId(String googleSigninClientId) {
        this.googleSigninClientId = googleSigninClientId;
    }

}
