package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ali_Iman on 5/12/17.
 */
public class SearchLogRepo implements SearchLogRepository {
    private static SearchLogRepo searchLogRepo;
    private ArrayList<SearchLog> searchLogsList;

    private SearchLogRepo() { }
    public static SearchLogRepo getSearchLogRepo() {
        if(searchLogRepo == null) {
            searchLogRepo = new SearchLogRepo();
            searchLogRepo.searchLogsList = new ArrayList<SearchLog>();
        }
        return searchLogRepo;
    }


    public void storeLogRecord(String srcCode, String destCode, String date) {
        searchLogsList.add(new SearchLog(srcCode, destCode, date, new Date()));
    }
    public SearchLog getLogRecord(String srcCode, String destCode, String date) {
        for(SearchLog searchLogEntry : searchLogsList) {
            if(searchLogEntry.getSrcCode().equals(srcCode) && searchLogEntry.getDestCode().equals(destCode) &&
                    searchLogEntry.getDate().equals(date))
                return searchLogEntry;
        }
        return null;
    }
    public void updateLogRecord(SearchLog searchLog) {
        for(SearchLog searchLogEntry : searchLogsList)
            if(searchLogEntry.equals(searchLog)) {
                searchLogEntry.setLastUpdateDate(searchLog.getLastUpdateDate());
                return;
            }
        return;
    }


    public ArrayList<SearchLog> getSearchLogsList() {
        return searchLogsList;
    }
    public void setSearchLogsList(ArrayList<SearchLog> searchLogsList) {
        this.searchLogsList = searchLogsList;
    }
}
