package net.thumbtack.school.auction;

public class ServerResponce {
    private int responseCode;
    private String responseData;

    public ServerResponce(int reCode, String reData) throws IllegalArgumentException {
        if(reCode == 200 || reCode == 400 || (reData != null && !reData.equals("")))
        {
            responseCode = reCode;
            responseData = reData;
        }
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }
}
