package Utils;
import com.google.gson.Gson;
import config.ConfigurationSingleton;

import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class Producers {

    @Produces
    public ConfigurationSingleton createConfiguration() {
        return ConfigurationSingleton.getInstance();
    }

    @Produces
    public Validator createValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }

    @Produces
    public Gson gson(){
        Gson gson = new Gson();
        return gson;
    }
}