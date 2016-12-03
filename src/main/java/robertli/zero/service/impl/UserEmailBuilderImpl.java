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
                content += "<a href=\"" + domain + "/#/auth/register/verifiy/" + verifiedCode + "\">";
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
                String content = "<p>Hello " + name + "</p>";
                content += "<a href=\"" + domain + "/UserPasswordReset!input?token=" + token + "\">";
                content += "click me for reset your password</a>";
                return content;
            }
        };
    }

}
