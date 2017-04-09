/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import robertli.zero.core.WebConfiguration;

public class WebConfigurationImpl implements WebConfiguration {

    private String domain;
    private String imageActionUrl;
    private String videoActionUrl;
    private String googleSigninClientId;
    private boolean sslEnabled;

    @Override
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String getImageActionUrl() {
        return imageActionUrl;
    }

    public void setImageActionUrl(String imageActionUrl) {
        this.imageActionUrl = imageActionUrl;
    }

    @Override
    public String getVideoActionUrl() {
        return videoActionUrl;
    }

    public void setVideoActionUrl(String videoActionUrl) {
        this.videoActionUrl = videoActionUrl;
    }

    @Override
    public String getGoogleSigninClientId() {
        return googleSigninClientId;
    }

    public void setGoogleSigninClientId(String googleSigninClientId) {
        this.googleSigninClientId = googleSigninClientId;
    }

    @Override
    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

}
