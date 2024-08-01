package learn.solar.domain;

import learn.solar.models.Panel;

import java.util.ArrayList;
import java.util.List;

public class PanelResult {
    private List<String> errorMessages = new ArrayList<>();
    private Panel payload;    //payload is the data that comes back

    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }

    public void addErrorMessage( String errorMessage ){
        errorMessages.add( errorMessage );
    }

    public boolean isSuccess(){
        return errorMessages.size() == 0;
    }

    public Panel getPayload() {
        return payload;
    }

    public void setPayload(Panel payload) {
        this.payload = payload;
    }


}
