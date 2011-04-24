package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.bson.types.ObjectId;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Kirill Korgov (korgovk)
 * Date: 4/24/11
 */
public class LoginYalet extends AbstractAgregatorYalet {

    public void process(InternalRequest req, InternalResponse res) {

        Map<String, String> responseMap = new HashMap<String, String>();
        Map<String, String> forCookie = new HashMap<String, String>();
        Map<String, String> cookies = req.getCookies();
        final String emailCookie = cookies.get("email");
        final String pwdCookie = cookies.get("pwd");
        final String email = req.getParameter("email");
        final String pwd = req.getParameter("pwd");
        final String logout = req.getParameter("logout");

        System.out.println("logout:" + logout);
        System.out.println("emailCookie:" + emailCookie);
        System.out.println("pwdCookie:" + pwdCookie);
        System.out.println("email:" + email);
        System.out.println("pwd:" + pwd);
        System.out.println("toreg: " + req.getParameter("toRegister"));

        if ("YES".equals(logout)) {
            System.out.println("LOGOUT");
            res.setCookies(new HashMap<String, String>());
            responseMap.put("AUTH", "-1");
            res.add(responseMap);
            return;
        }

        if (emailCookie != null && pwdCookie != null && !emailCookie.isEmpty() && !pwdCookie.isEmpty()) {
            System.out.println("COOKIE");

            final User user = Database.getUser(emailCookie, pwdCookie);
            if (user != null) {
                //Already in the DB, everything OK
                forCookie.put("email", emailCookie);
                forCookie.put("pwd", pwdCookie);
                forCookie.put("uid", String.valueOf(user.getId()));
                res.setCookies(forCookie);
                responseMap.put("uid", String.valueOf(user.getId()));
                responseMap.put("AUTH", "2");
                //res.redirectTo("index.xml");
            }
        } else if (email != null && pwd != null && !pwd.isEmpty() && !email.isEmpty()) {
            System.out.println("LOGIN");

            final User user = Database.getUser(email, pwd);
            System.out.println("user: " + user);

            if (user != null) {
                //Already in the DB, everything OK
                forCookie.put("email", email);
                forCookie.put("pwd", pwd);
                forCookie.put("uid", String.valueOf(user.getId()));
                res.setCookies(forCookie);
                responseMap.put("AUTH", "2");
                responseMap.put("uid", String.valueOf(user.getId()));
                //res.redirectTo("index.xml");
            } else if ("YES".equals(req.getParameter("toRegister"))) {
                System.out.println("REGISTER");

                //No such user in the DB, User wanted to register?
                final User newUser = new User();
                newUser.setKeyValue(User.FIELD_LOGIN, email);
                newUser.setKeyValue(User.FIELD_PASSWORD, pwd);
                final ObjectId userId = Database.addUser(newUser);
                System.out.println("!!!!!!!!!!!!!!" + userId.toString());
                forCookie.put("email", email);
                forCookie.put("pwd", pwd);
                forCookie.put("uid", String.valueOf(userId));
                res.setCookies(forCookie);
                responseMap.put("AUTH", "1");
                responseMap.put("uid", String.valueOf(userId));
            } else {
                System.out.println("INVALID PWD");
                //No such user in the DB, and don't want to register (bad pass possibly)
                responseMap.put("AUTH", "0");
            }
        } else {
            responseMap.put("AUTH", "-1");
        }

        System.out.println(responseMap);
        System.out.println(forCookie);

        //Let's try to login
        //Let's see if we have such user

        res.add(responseMap);
    }
}
