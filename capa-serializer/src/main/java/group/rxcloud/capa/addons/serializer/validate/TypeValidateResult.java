package group.rxcloud.capa.addons.serializer.validate;

import com.google.common.collect.Lists;

import java.util.List;

public class TypeValidateResult{
    private boolean isPass = true;
    private List<String> errorMessages = Lists.newArrayList();

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean pass) {
        isPass = pass;
    }

    public void addErrorMessage(String errorMessage){
        errorMessages.add(errorMessage);
    }

    public List<String> getErrorMessages(){
        return errorMessages;
    }
}
