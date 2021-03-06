package domain;

import java.util.Date;

/**
 * Created by melikaayoughi on 5/11/17.
 */
public class SearchLog {
    private String srcCode;
    private String destCode;
    private String date;
    private Date lastUpdateDate;

    public SearchLog(String srcCode, String destCode, String date, Date lastUpdateDate) {
        this.srcCode = srcCode;
        this.destCode = destCode;
        this.date = date;
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setSrcCode(String srcCode) {
        this.srcCode = srcCode;
    }
    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
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
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public boolean equals(SearchLog searchLog) {
        if (this.getSrcCode().equals(searchLog.getSrcCode()) &&
                this.getDestCode().equals(searchLog.getDestCode()) &&
                this.getDate().equals(searchLog.getDate()))
            return true;
        return false;
    }
}
