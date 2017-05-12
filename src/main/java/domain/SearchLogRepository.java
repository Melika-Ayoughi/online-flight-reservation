package domain;

/**
 * Created by Ali_Iman on 5/12/17.
 */
public interface SearchLogRepository {
    void storeLogRecord(String srcCode, String destCode, String date);
    SearchLog getLogRecord(String srcCode, String destCode, String date);
    void updateLogRecord(SearchLog searchLog);
}
