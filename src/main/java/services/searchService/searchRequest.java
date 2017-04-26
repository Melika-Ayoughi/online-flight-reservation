package services.searchService;

/**
 * Created by melikaayoughi on 4/26/17.
 */
public class searchRequest {
    private String srcCode = "";
    private String destCode = "";
    private String date = "";
    private Integer adultCount = 0;
    private Integer childCount = 0;
    private Integer infantCount = 0;

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }

    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public void setInfantCount(Integer infantCount) {
        this.infantCount = infantCount;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public String getDate() {
        return date;
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public Integer getInfantCount() {
        return infantCount;
    }


}
