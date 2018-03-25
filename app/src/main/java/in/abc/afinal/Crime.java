package in.abc.afinal;

/**
 * Created by maitr_000 on 24-03-2018.
 */

public class Crime {

    public String c_type;
    public int c_year, c_month, c_date, c_hr,c_min, c_lat,c_lon;
    public String c_desc;

    public Crime( String c_type, int c_year, int c_month, int c_date, int c_hr, int c_min, int c_lat, int c_lon, String c_desc){

        this.c_type = c_type;
        this.c_year = c_year;
        this.c_month = c_month;
        this.c_date = c_date;
        this.c_hr = c_hr;
        this.c_min =c_min;
        this.c_lat = c_lat;
        this.c_lon = c_lon;
        this.c_desc = c_desc;
    }
}
