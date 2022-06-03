
package com.example.finalyearproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseGrammar {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("response")
    @Expose
    private Response response;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
