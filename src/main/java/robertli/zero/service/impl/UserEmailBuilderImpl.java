/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.EmailMessage;
import robertli.zero.core.WebConfiguration;
import robertli.zero.service.UserEmailBuilder;

@Component("userEmailBuilder")
public class UserEmailBuilderImpl implements UserEmailBuilder {

    @Resource
    private WebConfiguration webConfiguration;

    @Override
    public EmailMessage buildUserRegisterVerificationEmail(String email, String name, String verifiedCode) {
        String domain = webConfiguration.getDomain();
        return new EmailMessage() {
            @Override
            public String getUUID() {
                return UUID.randomUUID().toString();
            }

            @Override
            public List<String> getEmailList() {
                List<String> emailList = new ArrayList<>(1);
                emailList.add(email);
                return emailList;
            }

            @Override
            public String getSubject() {
                return "User Register Verification";
            }

            @Override
            public String getContent() {
                String content = "<p>welcome to register " + domain + "</p>";
                content += "<a href=\"" + domain + "/#/auth/register/verify/" + verifiedCode + "\">";
                content += "verifiedCode:" + verifiedCode;
                content += "</a>";
                return content;
            }
        };
    }

    @Override
    public EmailMessage buildPasswordResetTokenEmail(String email, String name, String token) {
        String domain = webConfiguration.getDomain();
        return new EmailMessage() {
            @Override
            public String getUUID() {
                return UUID.randomUUID().toString();
            }

            @Override
            public List<String> getEmailList() {
                List<String> emailList = new ArrayList<>(1);
                emailList.add(email);
                return emailList;
            }

            @Override
            public String getSubject() {
                return "Password reset request";
            }

            @Override
            public String getContent() {
                final String linkUrl = domain + "/#/auth/reset-password/" + token;
                String content = "<p>Hello " + name + "</p>";
                content += "<p>We received a request to reset the password associated with this e-mail address. If you made this request, please follow the instructions below.</p>";
                content += "<p>Click the link below to reset your password using our secure server:</p>";
                content += "<a href=\"" + linkUrl + "\">" + linkUrl + "</a>";
                content += "<p>If you did not request to have your password reset you can safely ignore this email. Rest assured your customer account is safe.</p>";
                return content;
            }
        };
    }

}
