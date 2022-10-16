package net.thumbtack.school.auction;

public class ServerResponse
{
    private int responseCode;
    private String responseData;

    public ServerResponse(int reCode, String reData) throws IllegalArgumentException {
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
