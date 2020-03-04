package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.validation.constraints.Pattern;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class PostTagCredentialsValidator implements Validator {
    public boolean supports(Class<?> clazz) {
        return PostCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostCredentials enterForm = (PostCredentials) target;
            StringTokenizer st = new StringTokenizer(enterForm.getAllTags());
            while (st.hasMoreTokens()) {
                String nextToCheck = st.nextToken();
                if (!nextToCheck.matches("[a-z]+")) {
                    errors.rejectValue("allTags", "allTags.invalid-name", "invalid name");
                }
            }
        }
    }
}
