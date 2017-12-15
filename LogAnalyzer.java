/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCount;
    private int[] monthCount;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String file)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(file);
    }
    /**
     * Return the number of accesses recorded in the log file.
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for (int hour = 0; hour < hourCounts.length; hour++)
        {
            total = total + hourCounts[hour];
        }
        return total;
    }
       /**
     * Return the number of accesses recorded in the log file over the course of a month
     */
    public int numberOfAccessesAMonth()
    {
        int total = 0;
        for (int day = 0; day < dayCount.length; day++)
        {
            total = total + dayCount[day];
        }
        return total;
    }
    
    /**
     * Tells us the busiest hour in a log
     */
    public int busiestHour()
    {
        int busiest = 0;
        int busiestHour = 0;
        
        for (int hour = 0; hour < hourCounts.length; hour++)
        {
            if (busiest < hourCounts[hour])
            {
                busiest = hourCounts[hour];
                busiestHour = hour;
            }
        }
        return busiestHour;
    }
    
    /**
     * Find the quietest hour in a log
     */
    public int quietestHour()
    {
        int quietest = hourCounts[0];
        int quietestHour = 0;
        
        for (int hour = 0; hour < hourCounts.length; hour++)
        {
            if (quietest > hourCounts[hour])
            {
                quietest = hourCounts[hour];
                quietestHour = hour;
            }
        }
        return quietestHour;
    }
    /**
     * Finds the quietest day in a log
     */
     int quietestDay(int [] dayCount)
    {
        int quietestDay = dayCount[0];
        for(int i=0; i<dayCount.length; i++)
        {
            if(dayCount[i]>0 && dayCount[i]>quietestDay)
            {
                quietestDay = dayCount[i];
            }
        }
        return quietestDay; // the most quitest element return here
    }
    
    /** 
     * Tells us the busiest two hour period in a log
     */
    public int busiestTwoHourPeriod()
    {
        int busiestPeriod = 0;
        int busiestPeriodHour = 0;

        for (int hour = 0; hour < hourCounts.length - 1; hour++) 
        {
            int periodCount = hourCounts[hour] + hourCounts[hour+1];
            if (periodCount > busiestPeriodHour)
            {
                busiestPeriod = hour;
                busiestPeriodHour = periodCount;
            }
        }
        return busiestPeriodHour;
    }
    
    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            int day = 0;
            int month = 0;
            hourCounts[hour]++;
                if ( hour == 23)
                {
                    dayCount[day]++;
                    }
                 
                    if (day == 30)
                    {
                        monthCount[month]++;
                    }
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
