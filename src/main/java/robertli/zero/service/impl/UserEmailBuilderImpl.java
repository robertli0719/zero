/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
                content += "<a href=\"" + domain + "/UserRegisterVerify?code=" + verifiedCode + "\">";
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
