/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import java.util.ArrayList;
import java.util.List;
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
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                content += "<a href=\"" + domain + "\\UserRegister?verifiedCode=" + verifiedCode + "\">";
                content += "verifiedCode:" + verifiedCode;
                content += "</a>";
                return content;
            }
        };
    }

    @Override
    public EmailMessage buildPasswordResetTokenEmail(String email, String name, String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
